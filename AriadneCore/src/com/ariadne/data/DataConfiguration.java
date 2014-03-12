package com.ariadne.data;

import com.hp.hpl.jena.query.Dataset;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.tdb.TDB;
import com.hp.hpl.jena.tdb.TDBFactory;

public class DataConfiguration 
{
	private static String dataPath="/home/vikram/ontologies/dump/";
	public static final String FILENAME="data.rdf";
	private static Dataset mDataSet;
	public static void setDataPath(String path)
	{
		dataPath=path;
	}
	public static String getDataPath()
	{
		return dataPath;
	}
	public static Model getModel()
	{
		mDataSet=getDataset();
		return mDataSet.getDefaultModel();
	}
	public static Model getModel(String model)
	{
		mDataSet=getDataset();
		return mDataSet.getNamedModel(model);
	}
	public static Dataset getDataset()
	{
		if(mDataSet==null)
			return TDBFactory.createDataset(dataPath);
		else return mDataSet;
	}
	public static void closeDataset()
	{
		if(mDataSet!=null)
			mDataSet.close();
	}
}
