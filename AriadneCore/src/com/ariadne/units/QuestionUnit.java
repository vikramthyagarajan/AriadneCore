package com.ariadne.units;

public abstract class QuestionUnit 
{
	/*
	 * Abstract class that specifies the functionality that every QuestionUnit must possess
	 * namely, generating query from itself, and describing the type of question.
	 */
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
