package com.ariadne.nlp;

import java.util.List;
import java.util.Properties;

import com.ariadne.units.AnswerUnit;
import com.ariadne.units.QuestionUnit;
import com.ariadne.units.SimpleQuestionUnit;
import com.ariadne.util.Logger;

import edu.stanford.nlp.ling.CoreAnnotations.SentencesAnnotation;
import edu.stanford.nlp.ling.IndexedWord;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.semgraph.SemanticGraph;
import edu.stanford.nlp.semgraph.SemanticGraphCoreAnnotations.CollapsedCCProcessedDependenciesAnnotation;
import edu.stanford.nlp.util.CoreMap;

public class QueryParserHelper 
{
	public String getSubjectDataOfNode(SemanticGraph semGraph,IndexedWord node)
	{
		StringBuilder result=new StringBuilder();
		List<IndexedWord> children=semGraph.getChildList(node);
		for(IndexedWord child:children)
		{
			String rel=semGraph.getEdge(node, child).getRelation().toString();
			Logger.log("sm "+rel);
			if(rel.equals("nn"))
			{
				result.append(child.lemma()+" ");
			}
			else if(rel.startsWith("prep_of"))
			{
				result.append(child.lemma()+":");
			}
			
		}
		result.append(node.lemma());
		return result.toString();
	}
	public QuestionUnit getQuestionUnitFromGraph(SemanticGraph semGraph) throws Exception
	{
		SimpleQuestionUnit result=new SimpleQuestionUnit(0);
		IndexedWord root=semGraph.getFirstRoot();
		Logger.log("sss "+root.toString()+" "+root.tag());
		result.setType(AnswerUnit.DESCRIBE);
		if(root.lemma().equalsIgnoreCase("be")||root.lemma().equalsIgnoreCase("what"))
		{
			List<IndexedWord>children=semGraph.getChildList(root);
			for(IndexedWord ind:children)
			{
				String rel=semGraph.getEdge(root, ind).getRelation().toString();
				if(rel.contains("nsubj"))
				{
					if(ind.lemma().equalsIgnoreCase("who"))
						continue;
					result.setSubject(getSubjectDataOfNode(semGraph,ind));
				}
				else if(rel.contains("obj"))
					result.setObject(getSubjectDataOfNode(semGraph,ind));
			}
		}
		else if(root.lemma().equalsIgnoreCase("explain")||root.lemma().equalsIgnoreCase("describe"))
		{
			List<IndexedWord>children=semGraph.getChildList(root);
			for(IndexedWord ind:children)
			{
				String rel=semGraph.getEdge(root, ind).getRelation().toString();
				if(rel.equals("ccomp"))
				{
					result.setVerb(getSubjectDataOfNode(semGraph,ind));
				}
				else if(rel.contains("obj")||rel.contains("nsubj"))
				{
					if(ind.lemma().equalsIgnoreCase("who"))
						continue;
					result.setSubject(getSubjectDataOfNode(semGraph,ind));
				}
			}
		}
		else if(root.tag().toLowerCase().contains("vb")||root.tag().equalsIgnoreCase("NNS"))
		{
			result.setVerb(getSubjectDataOfNode(semGraph,root));
			List<IndexedWord>children=semGraph.getChildList(root);
			for(IndexedWord ind:children)
			{
				String rel=semGraph.getEdge(root, ind).getRelation().toString();
				if(rel.equals("advmod"))
				{
					if(ind.lemma().equalsIgnoreCase("where"))
					{
						result.setType(AnswerUnit.LOCATION);
						result.setPrep("in");
					}
					else if(ind.lemma().equalsIgnoreCase("who"))
					{
						result.setType(AnswerUnit.PERSON);
					}
				}
				else if(rel.contains("nsubj"))
				{
					if(ind.lemma().equalsIgnoreCase("who"))
						continue;
					result.setSubject(getSubjectDataOfNode(semGraph,ind));
				}
				else if(rel.contains("obj"))
				{
					if(result.getType()!=AnswerUnit.PERSON)
						result.setObject(getSubjectDataOfNode(semGraph,ind));
				}
				else if(rel.startsWith("prep"))
				{
					result.setPrep(semGraph.getEdge(root, ind).getRelation().getSpecific());
					result.setPrepData(ind.lemma());
				}
			}
		}
		else throw new Exception("Query not understood or not supported.");
		return result;
	}
	public QuestionUnit parseQuery(String query) throws Exception
	{
		Properties props=new Properties();
		props.put("annotators","tokenize,ssplit,pos,lemma,parse");
		StanfordCoreNLP pipeline=new StanfordCoreNLP(props);
		Annotation document=new Annotation(query);
		pipeline.annotate(document);
		List<CoreMap>sentences=document.get(SentencesAnnotation.class);
		if(sentences.size()>1)
			throw new Exception("Query must be of one sentence length.");
		return getQuestionUnitFromGraph(sentences.get(0).get(CollapsedCCProcessedDependenciesAnnotation.class));
	}
}
