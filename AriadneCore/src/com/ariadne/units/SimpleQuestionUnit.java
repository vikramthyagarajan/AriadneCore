package com.ariadne.units;

import com.ariadne.data.DataConfiguration;

public class SimpleQuestionUnit extends QuestionUnit 
{
	private String subject,verb,object,prep,prepData;

	public SimpleQuestionUnit(int type)
	{
		super(type);
	}
	@Override
	public String generateQuery() 
	{
		String namespace=AriadneStatement.URI;
		StringBuilder result=new StringBuilder();
		result.append("construct where {");
		if(subject==null)
			result.append("?s <"+AriadneStatement.statementData.toString()+"> ?o.");
		else result.append("<"+namespace+subject.replace(' ','_')+"> <"+AriadneStatement.statementData.toString()+"> ?o.");
		result.append("?o <http://ariadne.com/data> ?d." +
				"?o <http://ariadne.com/docRef> ?r." +
				"?o <http://ariadne.com/sentence> ?sen.");
		if(verb==null)
			result.append("?o <http://ariadne.com/verb> ?v.");
		else result.append("?o <http://ariadne.com/verb> \""+verb.replace(' ','_')+"\".");
		if(object==null&&prep==null&&prepData==null)
		{
			result.append("}");
			return result.toString();
		}
		if(object==null)
			result.append("?d <http://ariadne.com/object> ?h.");
		else result.append("?d <http://ariadne.com/object> \""+object.replace(' ','_')+"\".");
		if(prep==null)
			result.append("?d ?p");
		else result.append("?d <"+namespace+prep+"> ");
		if(prepData==null)
			result.append("?pd.");
		else result.append("\""+prepData+"\".");
		result.append("}");
		return result.toString();
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getVerb() {
		return verb;
	}
	public void setVerb(String verb) {
		this.verb = verb;
	}
	public String getObject() {
		return object;
	}
	public void setObject(String object) {
		this.object = object;
	}
	public String getPrep() {
		return prep;
	}
	public void setPrep(String prep) {
		this.prep = prep;
	}
	public String getPrepData() {
		return prepData;
	}
	public void setPrepData(String prepData) {
		this.prepData = prepData;
	}
	public String toString()
	{
		return super.toString()+"\n "+subject+": "+verb+": "+object+": "+prep;
	}

}
