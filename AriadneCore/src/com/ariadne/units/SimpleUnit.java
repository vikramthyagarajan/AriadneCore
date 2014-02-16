package com.ariadne.units;

import java.util.ArrayList;

public class SimpleUnit 
{
	private Triplet triplet;
	private ArrayList<Doublet> additionalInfo;
	public SimpleUnit()
	{
		additionalInfo=new ArrayList<Doublet>();
		triplet=new Triplet();
	}
	public String toString()
	{
		return "Triplet: "+triplet.toString();
	}
	public Triplet getTriplet()
	{
		return triplet;
	}
	public void setTriplet(Triplet t)
	{
		triplet=t;
	}
	public ArrayList<Doublet> getAdditionalData()
	{
		return additionalInfo;
	}
	public void setAdditionalData(ArrayList<Doublet> additionalInfo)
	{
		this.additionalInfo=additionalInfo;
	}
	public void addAdditionalData(Doublet data)
	{
		additionalInfo.add(data);
	}
	public int getAdditionalDataCount()
	{
		return additionalInfo.size();
	}
}
