package com.ariadne.nlp;

import java.util.List;
import java.util.Properties;
import java.util.Scanner;
import java.util.Set;

import com.ariadne.units.SentenceUnit;
import com.ariadne.units.Triplet;
import com.ariadne.util.Logger;

import edu.stanford.nlp.ling.CoreAnnotations.SentencesAnnotation;
import edu.stanford.nlp.ling.IndexedWord;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.semgraph.SemanticGraph;
import edu.stanford.nlp.semgraph.SemanticGraphCoreAnnotations.CollapsedCCProcessedDependenciesAnnotation;
import edu.stanford.nlp.semgraph.SemanticGraphEdge;
import edu.stanford.nlp.util.CoreMap;

public class ParserHelper 
{
	public static Triplet getSubjectVerbObject(Set<SemanticGraphEdge> sem)
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
	public static void traverseTree(SemanticGraph s,IndexedWord parent)
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
	/*
	 * Method used for debugging.
	 */
	public static void doProcessing(SemanticGraph s)
	{
		IndexedWord froot=s.getFirstRoot();
		traverseTree(s, froot);
	}
	/*
	 * Used to retrieve words along with its respective adjectives, adverbs etc.
	 */
	private static String getCompleteDataOfNode(SemanticGraph semGraph,IndexedWord node)
	{
		StringBuilder result=new StringBuilder();
		List<IndexedWord> children=semGraph.getChildList(node);
		for(IndexedWord child:children)
		{
			String rel=semGraph.getEdge(node, child).getRelation().getShortName();
			if(rel.equals("amod"))
			{
				result.append(child.word()+" ");
			}
			else if(rel.equals("nn"))
			{
				result.append(child.word()+" ");
			}
			
		}
		result.append(node.word());
		return result.toString();
	}
	/*
	 * Used to process a sentence through its SemanticGraph and retrieve a SentenceUnit 
	 * containing the structured data in the sentence.
	 */
	private static SentenceUnit processSentence(SemanticGraph semGraph)
	{
		SentenceUnit result=new SentenceUnit();
		IndexedWord root=semGraph.getFirstRoot();
		Triplet triplet=new Triplet();
		triplet.setVerb(root.word());
		List<IndexedWord>children=semGraph.getChildList(root);
		for(IndexedWord child:children)
		{
			String rel=semGraph.getEdge(root, child).getRelation().getShortName();
			/*logic for additional verbs. 
			 * TODO:- Implement later.
             */
             //if()
			
             //Logic for getting subject
             if(rel.startsWith("nsubj"))
             {
            	 triplet.setSubject(getCompleteDataOfNode(semGraph, child));
             }
             
             //Logic for getting object
             if(rel.equals("agent")||rel.contains("obj"))
             {
            	 triplet.setObject(getCompleteDataOfNode(semGraph, child));
             }
		}
		//result.setTriplet(triplet);
		Logger.log(result.toString());
		return result;
	}
	public static void parse(String text)
	{
		Properties props=new Properties();
		props.put("annotators","tokenize,ssplit,parse");
		StanfordCoreNLP pipeline=new StanfordCoreNLP(props);
		Annotation document=new Annotation(text);
		pipeline.annotate(document);
		List<CoreMap>sentences=document.get(SentencesAnnotation.class);
		
		for(CoreMap sentence:sentences)
		{
			SemanticGraph dep=sentence.get(CollapsedCCProcessedDependenciesAnnotation.class);
			//doShit(dep);
			processSentence(dep);
			//System.out.println("Sentence: "+sentence.toString());
		    //System.out.println("DEPENDENCIES: "+dep.toList());
			//Set<SemanticGraphEdge> li=dep.getEdgeSet();
		    //Triplet trip=getSubjectVerbObject(li);
		    //Logger.log(trip.toString());
		    //System.out.println("DEPENDENCIES SIZE: "+dep.size());
			//dep.prettyPrint();
		}
		
	}
}
