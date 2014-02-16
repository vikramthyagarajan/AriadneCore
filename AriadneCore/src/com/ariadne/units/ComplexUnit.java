package com.ariadne.units;

import java.util.ArrayList;

public class ComplexUnit 
{
	ArrayList<String>subjects,verbs,objects;
	ArrayList<Doublet>additionalData;
	public ComplexUnit() 
	{
		subjects=new ArrayList<String>();
		verbs=new ArrayList<String>();
		objects=new ArrayList<String>();
		additionalData=new ArrayList<Doublet>();
	}
	public void addSubject(String subj)
	{
		subjects.add(subj);
	}
	public void addVerb(String verb)
	{
		this.verbs.add(verb);
	}
	public void addObject(String obj)
	{
		this.objects.add(obj);
	}
	public void addAdditionalData(Doublet d)
	{
		additionalData.add(d);
	}
	public int getSubjectCount()
	{
		return subjects.size();
	}	
	public int getVerbCount()
	{
		return verbs.size();
	}	
	public int getObjectCount()
	{
		return objects.size();
	}
	public int getAdditionalDataCount()
	{
		return additionalData.size();
	}
	public String toString()
	{
		StringBuilder res=new StringBuilder();
		res.append("Subjects: ");
		for(String s:subjects)
			res.append(s+" ");
		res.append("\nVerbs: ");
		for(String s:verbs)
			res.append(s+" ");
		res.append("\nObjects: ");
		for(String s:objects)
			res.append(s+" ");
		res.append("\nAdditional Data: ");
		for(Doublet d:additionalData)
			res.append(d.toString()+"\n");
		return res.toString();
	}
}
