package com.ariadne.units;

import java.util.ArrayList;

import com.ariadne.nlp.DocumentReference;

public class SentenceUnit 
{
	private ArrayList<SimpleUnit> units;
	private DocumentReference docRef;
	public SentenceUnit()
	{
		units=new ArrayList<SimpleUnit>();
	}
	public String toString()
	{
		StringBuilder str=new StringBuilder();
		for(int i=0;i<units.size();i++)
			str.append(units.get(i).toString());
		
		return //+"\nDocument Reference: "+docRef.toString()
				"\nData: \n"+str;
	}
	public void addSimpleUnit(SimpleUnit data)
	{
		units.add(data);
	}
	public DocumentReference getDocumentReference()
	{
		return this.docRef;
	}
}
