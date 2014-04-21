package com.ariadne.units;

import java.util.ArrayList;


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
		
		return "\nDocument Reference: "+docRef.toString()+"\nSentence: "+sentence+
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
	public ArrayList<ComplexUnit> getComplexUnits()
	{
		return this.units;
	}/*
	public boolean equals(Object o)
	{
		if(o instanceof SentenceUnit)
		{
			SentenceUnit new_name = (SentenceUnit) o;			
			if(this.docRef.equals(new_name.getDocumentReference()))
				return true;
		}
		return false;
	}*/
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((docRef == null) ? 0 : docRef.hashCode());
		result = prime * result
				+ ((sentence == null) ? 0 : sentence.hashCode());
		result = prime * result + ((units == null) ? 0 : units.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SentenceUnit other = (SentenceUnit) obj;
		if (docRef == null) {
			if (other.docRef != null)
				return false;
		} else if (!docRef.equals(other.docRef))
			return false;
		if (sentence == null) {
			if (other.sentence != null)
				return false;
		} else if (!sentence.equals(other.sentence))
			return false;
		if (units == null) {
			if (other.units != null)
				return false;
		} else if (!units.equals(other.units))
			return false;
		return true;
	}
}
