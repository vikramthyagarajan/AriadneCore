package com.ariadne.data;

import com.hp.hpl.jena.query.Dataset;
import com.hp.hpl.jena.rdf.model.Model;

public class AriadneDataReader 
{
	Model mModel;
	Dataset mDataSet;
	public AriadneDataReader(Dataset dataset,Model model)
	{
		this.mDataSet=dataset;
		this.mModel=model;
	}
	private void queryModel(String query)
	{
		
	}
}
