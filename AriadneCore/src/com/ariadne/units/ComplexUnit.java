package com.ariadne.units;

import java.util.ArrayList;

public class ComplexUnit 
{
	ArrayList<String>subjects,objects;
	String verb;
	ArrayList<Doublet>additionalData;
	public ComplexUnit() 
	{
		subjects=new ArrayList<String>();
		objects=new ArrayList<String>();
		additionalData=new ArrayList<Doublet>();
	}
	public ComplexUnit(ComplexUnit compUnit)
	{
		subjects=new ArrayList<String>(compUnit.getSubjects());
		objects=new ArrayList<String>(compUnit.getObjects());
		additionalData=new ArrayList<Doublet>(compUnit.getAdditionalData());
	}
	public void addSubject(String subj)
	{
		subjects.add(subj);
	}
	public void setVerb(String verb)
	{
		this.verb=verb;
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
	public String getSubjectAt(int i)
	{
		return subjects.get(i);
	}
	public String getVerb()
	{
		return this.verb;
	}
	public ArrayList<String>getObjects()
	{
		return this.objects;
	}
	public String getObjectAt(int i)
	{
		return objects.get(i);
	}
	public ArrayList<Doublet>getAdditionalData()
	{
		return this.additionalData;
	}
	public Doublet getAdditionalDataAt(int i)
	{
		return additionalData.get(i);
	}
	public String toString()
	{
		StringBuilder res=new StringBuilder();
		res.append("Subjects: ");
		for(String s:subjects)
			res.append(s+" ");
		res.append("\nVerb: ");
		res.append(verb+" ");
		res.append("\nObjects: ");
		for(String s:objects)
			res.append(s+" ");
		res.append("\nAdditional Data: ");
		for(Doublet d:additionalData)
			res.append(d.toString()+"\n");
		return res.toString();
	}
}
