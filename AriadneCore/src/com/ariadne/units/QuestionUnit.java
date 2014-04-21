package com.ariadne.units;

public abstract class QuestionUnit 
{
	private int type;
	
	public QuestionUnit(int type)
	{
		this.type=type;
	}
	public void setType(int type)
	{
		this.type=type;
	}
	public int getType()
	{
		return this.type;
	}
	public abstract String generateQuery();
	public String toString()
	{
		return "Type: "+this.type+" ";
	}

}
