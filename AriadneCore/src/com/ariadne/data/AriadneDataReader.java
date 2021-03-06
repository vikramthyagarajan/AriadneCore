package com.ariadne.data;

import java.util.ArrayList;

import com.ariadne.units.AriadneStatement;
import com.ariadne.units.ComplexUnit;
import com.ariadne.units.DocumentReference;
import com.ariadne.units.Doublet;
import com.ariadne.units.SentenceUnit;
import com.ariadne.util.Logger;
import com.hp.hpl.jena.query.Dataset;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.ReadWrite;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.rdf.model.ResIterator;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.Statement;
import com.hp.hpl.jena.rdf.model.StmtIterator;

public class AriadneDataReader 
{
	Model mModel;
	Dataset mDataSet;
	private DataConfiguration mDataConfiguration;
	public static final int SELECT_QUERY=0;
	public static final int DESCRIBE_QUERY=1;
	public static final int CONSTRUCT_QUERY=2;
	public AriadneDataReader(DataConfiguration df)
	{
		this.mDataConfiguration=df;
		this.mDataSet=df.getDataset();
		this.mModel=df.getModel();
	}
	private void begin()
	{
		mDataSet.begin(ReadWrite.READ);
	}
	private void putObjectData(ComplexUnit comUnit,Resource anonObject)
	{
		StmtIterator stit=anonObject.listProperties();
		while(stit.hasNext())
		{
			Statement node=stit.next();
			RDFNode predicate=node.getPredicate();
			RDFNode object=node.getObject();
			if(((Property)predicate).equals(AriadneStatement.object))
			{
				comUnit.addObject(object.toString().replace('_',' '));
			}
			else
			{
				comUnit.addAdditionalData(new Doublet(predicate.toString(), object.toString()));
			}
		}
	}
	/*
	 * A method to iterate within a resource and put in all the data within that resource, 
	 * into the SentenceUnit.
	 */
	private void putStatementDataIntoSentenceUnit(SentenceUnit senUnit,ComplexUnit comUnit,Resource anonSubject)
	{
		StmtIterator stit=anonSubject.listProperties();
		while(stit.hasNext())
		{
			Statement node=stit.next();
			Property predicate=(Property)node.getPredicate();
			RDFNode object=node.getObject();
			if(object.isLiteral())
			{
				if(predicate.equals(AriadneStatement.verb))
				{
					comUnit.setVerb(object.toString().replace('_',' '));
				}
                else if(predicate.equals(AriadneStatement.sentence))
                {
                	senUnit.setSentence(object.toString());
                }
                else if(predicate.equals(AriadneStatement.documentReference))
                {
                	senUnit.setDocumentReference(new DocumentReference(object.toString()));
                }
			}
			else 
			{
				putObjectData(comUnit,(Resource)object);
			}
		}
		senUnit.addComplexUnit(comUnit);
	}
	/*
	 * Method to get all the SentenceUnits within a model resource.
	 */
	private ArrayList<SentenceUnit> getSentenceUnitsFromResource(Resource subject)
	{
		ArrayList<SentenceUnit>sentences=new ArrayList<SentenceUnit>();
        StmtIterator statementData=subject.listProperties();
        while(statementData.hasNext())
        {
        	SentenceUnit senUnit=new SentenceUnit();
        	ComplexUnit comUnit=new ComplexUnit();
        	comUnit.addSubject(subject.getLocalName().replace('_',' '));
        	putStatementDataIntoSentenceUnit(senUnit,comUnit,(Resource) statementData.next().getObject());
        	sentences.add(senUnit);
        }
		return sentences;
	}
	private ArrayList<SentenceUnit> getSentencesFromModel(Model mModel)
	{
		ArrayList<SentenceUnit>sentences=new ArrayList<SentenceUnit>();
		ResIterator rsi=mModel.listResourcesWithProperty(AriadneStatement.statementData);
		while(rsi.hasNext())		//iterating through every subject.
		{
			sentences.addAll(getSentenceUnitsFromResource(rsi.next()));
		}
		return sentences;
	}
	/*
	 * Queries given a query and the type of query to be executed on TripleDB 
	 * and retrieves all the sentences in the form of SentenceUnits.
	 */
	public ArrayList<SentenceUnit> queryModel(String query,int type)
	{
		ArrayList<SentenceUnit>sentences;
		try
		{
			QueryExecution qe=QueryExecutionFactory.create(query,mDataSet);
			try
			{
				Model rs;
				if(type==AriadneDataReader.CONSTRUCT_QUERY)
					rs=qe.execConstruct();
				else if(type==AriadneDataReader.DESCRIBE_QUERY)
					rs=qe.execDescribe();
				else{throw new Exception("Only construct and describe queries allowed.");}
				sentences=getSentencesFromModel(rs);
				return sentences;
			}
			catch(Exception e)
			{
				Logger.log("Error: "+e.getMessage());
			}
			finally
			{
				qe.close();
			}
		}
		finally
		{
			mModel.close();
			mDataConfiguration.closeDataset();
		}
		return null;
	}
}


