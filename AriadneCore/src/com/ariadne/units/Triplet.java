package com.ariadne.units;


public class Triplet 
{
	private String subject,verb,object;
	public Triplet()
	{
		
	}
	public Triplet(String subject,String verb,String object)
	{
		this.subject=subject;
		this.verb=verb;
		this.object=object;
	}
	@Override
	public String toString()
	{
		return "Subject: "+subject+" Predicate: "+verb+" Object: "+object;
	}
	
	//getters and setters-
	public String getSubject()
	{
		return subject;
	}
	public void setSubject(String subject)
	{
		this.subject=subject;
	}
	public String getVerb()
	{
		return verb;
	}
	public void setVerb(String verb)
	{
		this.verb=verb;
	}
	public String getObject()
	{
		return object;
	}
	public void setObject(String object)
	{
		this.object=object;
	}
}
