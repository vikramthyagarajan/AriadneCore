package com.ariadne.units;

import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.impl.PropertyImpl;

public class AriadneStatement 
{
	public static final String URI="";
	public static Property documentReference = null;
	static final String mDocumentReference= "docRef";
	public static Property object= null;
	static final String mObject = "object";
	public static Property sentence= null;
	static final String mSentence= "sentence";
	public static Property preposition= null;
	static final String mPreposition= "preposition";
	public static Property prepositionalObject= null;
	static final String mPrepositionalObject= "prepositionalObject";
	
	static
	{
		try
		{
			documentReference=new PropertyImpl(URI,mDocumentReference);
			object=new PropertyImpl(URI,mObject);
			sentence=new PropertyImpl(URI,mSentence);
			preposition=new PropertyImpl(URI,mPreposition);
			prepositionalObject=new PropertyImpl(URI,mPrepositionalObject);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}
