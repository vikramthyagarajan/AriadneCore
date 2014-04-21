package com.ariadne.nlp;

import com.ariadne.units.AnswerUnit;
import com.ariadne.units.QuestionUnit;
import com.ariadne.util.Logger;

public class QueryProcessor 
{
	private AnswerProcessor mAnswerProcessor;
	private QueryParserHelper mQueryParserHelper;
	
	public QueryProcessor()
	{
		mAnswerProcessor=new AnswerProcessor();
		mQueryParserHelper= new QueryParserHelper();
	}
	private AnswerUnit convertQuestionToAnswerUnit(QuestionUnit qu)
	{
		AnswerUnit result=new AnswerUnit(qu.getType());
		result.setQueryString(qu.generateQuery());
		switch(qu.getType())
		{
		case AnswerUnit.DESCRIBE:
		{
			result.setPipeline("summarize");
			break;
		}
		case AnswerUnit.PERSON:
		{
			result.setPipeline("singleAnswer");
			result.setPipelineData("subject");
			break;
		}
		case AnswerUnit.LOCATION:
		{
			result.setPipeline("singleAnswer");
			result.setPipelineData("prepData");
			break;
		}
		}
		return result;
	}
	public String executeQuery(String query)
	{
		try 
		{
			QuestionUnit question=mQueryParserHelper.parseQuery(query);
			Logger.log("ssss "+question.toString());
			return mAnswerProcessor.processUnit(convertQuestionToAnswerUnit(question));
		} 
		catch (Exception e) 
		{
			Logger.log(e.getMessage());
			return e.getMessage();
		}
	}
}
