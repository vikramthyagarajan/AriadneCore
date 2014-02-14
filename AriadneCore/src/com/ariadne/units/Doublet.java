package com.ariadne.units;

public class Doublet 
{
	private String prep,object;
	public Doublet()
	{
		
	}
	public Doublet(String p,String o)
	{
		prep=p;
		object=o;
	}
	public String toString()
	{
		return "Preposition: "+prep+" Object: "+object;
	}
	public void setPreposition(String p)
	{
		prep=p;
	}
	public String getPreposition()
	{
		return prep;
	}
	public void setObject(String o)
	{
		object=o;
	}
	public String getObject()
	{
		return object;
	}
}
