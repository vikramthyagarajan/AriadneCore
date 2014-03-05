package com.ariadne.units;

import java.util.ArrayList;

import com.ariadne.nlp.DocumentReference;

public class SentenceUnit 
{
	private ArrayList<ComplexUnit>units;
	private DocumentReference docRef;
	private String sentence;
	public SentenceUnit()
	{
		units=new ArrayList<ComplexUnit>();
	}
	public String toString()
	{
		StringBuilder str=new StringBuilder();
		for(int i=0;i<units.size();i++)
			str.append(units.get(i).toString());
		
		return //+"\nDocument Reference: "+docRef.toString()
				"\nData: \n"+str;
	}
	public void addComplexUnit(ComplexUnit data)
	{
		units.add(data);
	}
	public DocumentReference getDocumentReference()
	{
		return this.docRef;
	}
	public void setDocumentReference(DocumentReference docRef)
	{
		this.docRef=docRef;
	}
	public String getSentence()
	{
		return this.sentence;
	}
	public void setSentence(String s)
	{
		this.sentence=s;
	}
}
