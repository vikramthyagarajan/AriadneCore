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
	public ComplexUnit(ComplexUnit compUnit)
	{
		subjects=new ArrayList<String>(compUnit.getSubjects());
		verbs=new ArrayList<String>(compUnit.getVerbs());
		objects=new ArrayList<String>(compUnit.getObjects());
		additionalData=new ArrayList<Doublet>(compUnit.getAdditionalData());
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
	public ArrayList<String>getSubjects()
	{
		return this.subjects;
	}
	public ArrayList<String>getVerbs()
	{
		return this.verbs;
	}
	public ArrayList<String>getObjects()
	{
		return this.objects;
	}
	public ArrayList<Doublet>getAdditionalData()
	{
		return this.additionalData;
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
