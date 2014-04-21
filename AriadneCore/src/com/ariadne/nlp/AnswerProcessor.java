package com.ariadne.nlp;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import com.ariadne.data.AriadneDataReader;
import com.ariadne.data.DataConfiguration;
import com.ariadne.units.AnswerUnit;
import com.ariadne.units.DocumentReference;
import com.ariadne.units.SentenceUnit;
import com.ariadne.util.Logger;

public class AnswerProcessor 
{
	private AriadneDataReader adr;
	public AnswerProcessor()
	{
		adr=new AriadneDataReader(new DataConfiguration());
	}
	private ArrayList<SentenceUnit> getSentences(String query)
	{
		return adr.queryModel(query, AriadneDataReader.CONSTRUCT_QUERY);
	}
	public String summarize(ArrayList<SentenceUnit> sentences)
	{
		Set<DocumentReference>uniqueSentences=new HashSet<DocumentReference>();
		StringBuilder result=new StringBuilder();
		for(SentenceUnit sentence:sentences)
		{
			if(!uniqueSentences.contains(sentence.getDocumentReference()))
			{
				uniqueSentences.add(sentence.getDocumentReference());
				result.append(sentence.getSentence()+" ");
			}
		}
		return result.toString();
	}
	public String singleAnswer(ArrayList<SentenceUnit>sentences,String data)
	{
		if(sentences.size()>1||sentences.size()==0)
			return null;
		else
		{
			SentenceUnit sentence=sentences.get(0);
			if(data.equals("subject"))
				return sentence.getComplexUnits().get(0).getSubjectAt(0);
			else if(data.equals("object"))
				return sentence.getComplexUnits().get(0).getObjectAt(0);
			else if(data.equals("verb"))
				return sentence.getComplexUnits().get(0).getVerb();
			else if(data.equals("prepData"))
				return sentence.getComplexUnits().get(0).getAdditionalDataAt(0).getObject();
			else return null;
		}
	}
	public String processUnit(AnswerUnit answerUnit)
	{
		ArrayList<SentenceUnit> sentences=getSentences(answerUnit.getQueryString());
		Logger.log("sgsgs "+answerUnit.getPipelineData()+"   "+sentences.get(0).toString()+"sss "+answerUnit.getPipeline());
		if(answerUnit.getPipeline().equals("summarize"))
		{
			return summarize(sentences);
		}
		else if(answerUnit.getPipeline().equals("singleAnswer"))
		{
			return singleAnswer(sentences,answerUnit.getPipelineData());
		}
		else return null;
	}
}
