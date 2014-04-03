package com.ariadne.nlp;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Scanner;
import java.util.Set;

import com.ariadne.units.ComplexUnit;
import com.ariadne.units.DocumentReference;
import com.ariadne.units.Doublet;
import com.ariadne.units.SentenceUnit;
import com.ariadne.units.Triplet;
import com.ariadne.util.Logger;

import edu.stanford.nlp.dcoref.CorefChain;
import edu.stanford.nlp.dcoref.CorefChain.CorefMention;
import edu.stanford.nlp.dcoref.CorefCoreAnnotations;
import edu.stanford.nlp.ling.CoreAnnotations.SentencesAnnotation;
import edu.stanford.nlp.ling.IndexedWord;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.semgraph.SemanticGraph;
import edu.stanford.nlp.semgraph.SemanticGraphCoreAnnotations.CollapsedCCProcessedDependenciesAnnotation;
import edu.stanford.nlp.semgraph.SemanticGraphEdge;
import edu.stanford.nlp.util.CoreMap;

public class TextParserHelper 
{
	private boolean isPassive;
	private CorefHandler mCorefHandler;
	/*
	 * Methods used for debugging.
	 */
	public Triplet getSubjectVerbObject(Set<SemanticGraphEdge> sem)
	{
		Triplet result=new Triplet();
		for(SemanticGraphEdge iw:sem)
        {
			if(iw.getRelation().getShortName().startsWith("nsubj"))
			{
				result.setSubject(iw.getDependent().toString());
				result.setVerb(iw.getGovernor().toString());
			}
			else if(iw.getRelation().getShortName().contains("obj"))
				result.setObject(iw.getDependent().toString());
        }
		return result;
	}
	public void traverseTree(SemanticGraph s,IndexedWord parent)
	{
        Scanner sc=new Scanner(System.in);
		IndexedWord current=parent;
		List<IndexedWord>chils=s.getChildList(current);
		Logger.log("Parent: "+current.toString()+"\n");
		for(int i=0;i<chils.size();i++)
		{
			IndexedWord chil=chils.get(i);
			Logger.log("Word: "+chil.toString());
			Logger.log("Edge: "+s.getEdge(current, chil).toString()+"\n");
			if(i==chils.size()-1)
			{
				Logger.log("Enter 0,1,2,3... or -1 to go up, -2 to exit");
				int a=sc.nextInt();
				if(a==-1) traverseTree(s, s.getParent(current));
				else if(a==-2) return;
				else traverseTree(s, chils.get(a));
			}
		}	
	}
	public void doProcessing(SemanticGraph s)
	{
		IndexedWord froot=s.getFirstRoot();
		traverseTree(s, froot);
	}
	
	/*
	 * Implementation methods follow:-
	 * Used to retrieve words along with its respective adjectives, adverbs and preposition etc.
	 */
	private String getCompleteDataOfNode(SemanticGraph semGraph,IndexedWord node,ComplexUnit cUnit)
	{
		Logger.log(node.word()+" check "+mCorefHandler.isMention(node));
		Logger.log(node.word()+" r check "+mCorefHandler.isRepresentativeMention(node));
		if(mCorefHandler.isMention(node))
			return mCorefHandler.getRepresentativeMentionOf(node);
		StringBuilder result=new StringBuilder();
		List<IndexedWord> children=semGraph.getChildList(node);
		for(IndexedWord child:children)
		{
			String rel=semGraph.getEdge(node, child).getRelation().toString();
			if(rel.equals("amod"))
			{
				result.append(child.word()+" ");
			}
			else if(rel.equals("nn"))
			{
				result.append(child.word()+" ");
			}
			else if(rel.startsWith("prep"))
			{
				int index=rel.lastIndexOf('_')+1;
				if(index!=0)
				{
					Doublet d=new Doublet(rel.substring(index),child.word());
					cUnit.addAdditionalData(d);
           	 	}
			}
		}
		result.append(node.word());
		if(mCorefHandler.isRepresentativeMention(node))
			mCorefHandler.setStringMention(node, result.toString());
		return result.toString();
	}
	/*
	 * While setting Subject, only nn is important. No data about adjective
	 * or adverbs are stored in Subject.
	 * However, prepositions are important as the object of the prepositions refer to the 
	 * main concepts that it is about.
	 */
	private String getSubjectDataOfNode(SemanticGraph semGraph,IndexedWord node)
	{
		Logger.log(node.word()+" check "+mCorefHandler.isMention(node));
		Logger.log(node.word()+" r check "+mCorefHandler.isRepresentativeMention(node));
		if(mCorefHandler.isMention(node))
			return mCorefHandler.getRepresentativeMentionOf(node);
		StringBuilder result=new StringBuilder();
		List<IndexedWord> children=semGraph.getChildList(node);
		for(IndexedWord child:children)
		{
			String rel=semGraph.getEdge(node, child).getRelation().getShortName();
			if(rel.equals("nn"))
			{
				result.append(child.word()+" ");
			}
			else if(rel.startsWith("prep"))
			{
				result.append(child.word()+":");
			}
			
		}
		result.append(node.word());
		if(mCorefHandler.isRepresentativeMention(node))
			mCorefHandler.setStringMention(node, result.toString());
		return result.toString();
	}
	/*
	 * 
	 */
	public void addSentence(SentenceUnit senUnit,IndexedWord w,SemanticGraph semGraph)
	{
		ComplexUnit cu=new ComplexUnit();
		List<IndexedWord>children=semGraph.getChildList(w);
		cu.setVerb(w.word());
		boolean prevValueOfPassive=isPassive;
		isPassive=false;
		for(IndexedWord child:children)
		{
			processChildNode(semGraph, w, child, senUnit, cu);
		}
		//Logger.log(cu.toString());
		isPassive=prevValueOfPassive;
		senUnit.addComplexUnit(cu);
	}
	/*
	 * A complement of a verb is something that increases the meaning of that verb, but is not
	 * acting as an independent verb in itself. Example, I am *ready to *leave. Here, until a new
	 * way is thought of to solve this, each is treated as an independent verb.
	 */
	public void processComplement(SemanticGraph semGraph,
			IndexedWord currentWord,SentenceUnit senUnit,ComplexUnit cUnit)
	{
		List<IndexedWord>children=semGraph.getChildList(currentWord);
		cUnit.setVerb(currentWord.word());
		boolean prevValueOfPassive=isPassive;
		isPassive=false;
		for(IndexedWord child:children)
		{
			processChildNode(semGraph, currentWord, child, senUnit, cUnit);
		}
		isPassive=prevValueOfPassive;
	}
	/*
	 * 
	 */
	public void processChildNode(SemanticGraph semGraph,IndexedWord currentRoot,
			IndexedWord child,SentenceUnit senUnit,ComplexUnit cUnit)
	{
		String rel=semGraph.getEdge(currentRoot, child).getRelation().toString();
         if(rel.startsWith("conj"))
         {
        	 //if(rel.startsWith("conj_and"))
               //  cUnit.addVerb(child.word());
        	 //else if(rel.startsWith("conj_but"))
    		 addSentence(senUnit,child,semGraph);
        	 /*
        	 List<IndexedWord>innerSent=semGraph.getChildList(child);
        	 Logger.log("the size and verb to do "+innerSent.size());
        	 if(innerSent.size()!=0)
        	 {
            	 ComplexUnit inner=new ComplexUnit();
        		 for(IndexedWord ind:semGraph.getChildList(child))
        			 processChildNode(semGraph, child, ind, senUnit, inner);
        		 Logger.log(inner.toString());
        		 senUnit.addComplexUnit(inner);
        	 }
        	 */
         }
         else if(rel.endsWith("xcomp"))
         {
        	 if(rel.startsWith("a"));
        	 else processComplement(semGraph,child,senUnit,cUnit);
         }
		
         //Logic for getting subject
         else if(rel.equals("nsubjpass"))
         {
        	 isPassive=true;
        	 cUnit.addObject(getCompleteDataOfNode(semGraph, child,cUnit));
         }
         else if(rel.equals("nsubj"))
         {
        	 cUnit.addSubject(getSubjectDataOfNode(semGraph, child));
         }
         
         //Logic for getting object
         else if(rel.equals("agent")||rel.contains("obj"))
         {
        	 if(isPassive)
        		 cUnit.addSubject(getSubjectDataOfNode(semGraph, child));
        	 else cUnit.addObject(getCompleteDataOfNode(semGraph, child,cUnit));
         }
         
         //Logic for additional data like prepositions.
         else if(rel.startsWith("prep"))
         {
        	 int index=rel.lastIndexOf('_')+1;
        	 if(index!=0)
        	 {
        		 Doublet d=new Doublet(rel.substring(index),child.word());
        		 cUnit.addAdditionalData(d);
        	 }
         }
	}
	/*
	 * Used to process a sentence through its SemanticGraph and retrieve a SentenceUnit 
	 * containing the structured data in the sentence.
	 */
	private SentenceUnit processSentence(SemanticGraph semGraph,String sentence,DocumentReference dr)
	{
		SentenceUnit result=new SentenceUnit();
		result.setSentence(sentence);
		result.setDocumentReference(dr);
		IndexedWord root=semGraph.getFirstRoot();
		
			
		//checking if the root node is a verb. If not, it is a description type sentence.
		if(!root.tag().startsWith("VB"))
		{
			ComplexUnit comUnit=new ComplexUnit();
			comUnit.setVerb("description");
			List<IndexedWord>children=semGraph.getChildList(root);
			for(IndexedWord ind:children)
				if(semGraph.getEdge(root, ind).getRelation().toString().startsWith("nsubj"))
					comUnit.addSubject(getSubjectDataOfNode(semGraph, ind));
			result.addComplexUnit(comUnit);
			return result;
		}
		
		List<IndexedWord>children=semGraph.getChildList(root);
		ComplexUnit cUnit=new ComplexUnit();
		cUnit.setVerb(root.word());
		isPassive=false;
		for(IndexedWord child:children)
		{
			processChildNode(semGraph, root, child, result, cUnit);
		}
		isPassive=false;
		//Logger.log(cUnit.toString());
		result.addComplexUnit(cUnit);
		return result;
	}
	public ArrayList<SentenceUnit> parse(String text)
	{
		ArrayList<SentenceUnit> result=new ArrayList<SentenceUnit>();
		Properties props=new Properties();
		props.put("annotators","tokenize,ssplit,pos,lemma,parse,ner,dcoref");
		StanfordCoreNLP pipeline=new StanfordCoreNLP(props);
		Annotation document=new Annotation(text);
		pipeline.annotate(document);
		List<CoreMap>sentences=document.get(SentencesAnnotation.class);
		String value="";
		Map<Integer, CorefChain> corefGraph = document.get(
				CorefCoreAnnotations.CorefChainAnnotation.class);
		mCorefHandler=new CorefHandler("documentReferenceShit");
		mCorefHandler.init(corefGraph);
        for(CoreMap sentence:sentences)
		{
    		DocumentReference dr=new DocumentReference("jslc142",0);
			SemanticGraph dep=sentence.get(CollapsedCCProcessedDependenciesAnnotation.class);
			//Map<Integer,CorefChain>coRefMap=
                //Logger.log(sentence.get(CorefCoreAnnotations.CorefAnnotation.class));

			result.add(processSentence(dep,sentence.toString(),dr));
			//System.out.println("Sentence: "+sentence.toString());
		    //System.out.println("DEPENDENCIES: "+dep.toList());
			//Set<SemanticGraphEdge> li=dep.getEdgeSet();
		    //Triplet trip=getSubjectVerbObject(li);
		    //Logger.log(trip.toString());
		    //System.out.println("DEPENDENCIES SIZE: "+dep.size());
			//dep.prettyPrint();
		}
		return result;
	}
}
