package com.ariadne.units;

import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.impl.PropertyImpl;

public class AriadneStatement 
{
	public static final String URI="http://ariadne.com/";
	public static Property documentReference = null;
	static final String mDocumentReference= "docRef";
	public static Property object= null;
	static final String mObject = "object";
	public static Property sentence= null;
	static final String mSentence= "sentence";
	public static Property verb=null;
	static final String mVerb="verb";
	public static Property statementData=null;
	static final String mStatementData="statementData";
	//public static Property prepositionData=null;
	//static final String mPrepositionData="prepositionData";
	//public static Property preposition= null;
	//static final String mPreposition= "preposition";
	public static Property data= null;
	static final String mData= "data";
	
	static
	{
		try
		{
			documentReference=new PropertyImpl(URI,mDocumentReference);
			object=new PropertyImpl(URI,mObject);
			sentence=new PropertyImpl(URI,mSentence);
			verb=new PropertyImpl(URI,mVerb);
			statementData=new PropertyImpl(URI,mStatementData);
			//prepositionData=new PropertyImpl(URI,mPrepositionData);
			//preposition=new PropertyImpl(URI,mPreposition);
			data=new PropertyImpl(URI,mData);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}
