package com.ariadne.data;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.jena.riot.RDFDataMgr;
import org.apache.jena.riot.RDFFormat;

import com.ariadne.units.AriadneStatement;
import com.ariadne.units.ComplexUnit;
import com.ariadne.units.Doublet;
import com.ariadne.units.SentenceUnit;
import com.ariadne.util.Logger;
import com.hp.hpl.jena.query.Dataset;
import com.hp.hpl.jena.query.ReadWrite;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.Resource;

public class AriadneDataWriter 
{
	private Dataset mDataset;
	private Model mModel;
	private DataConfiguration mDataConfiguration;
	private ArrayList<SentenceUnit>sentencesToWrite;
	private FileOutputStream fileOutputStream;
	public AriadneDataWriter(DataConfiguration mDataConfiguration) throws IOException
	{
		this.mDataConfiguration=mDataConfiguration;
		this.mDataset=mDataConfiguration.getDataset();
		this.mModel=mDataConfiguration.getModel();
		this.sentencesToWrite=new ArrayList<SentenceUnit>();
		File f=new File(DataConfiguration.getDataPath()+DataConfiguration.FILENAME);
		if(!f.exists())
			f.createNewFile();
		this.fileOutputStream=new FileOutputStream(f);
	}
	public void addSentenceToBeWritten(SentenceUnit sentence)
	{
		sentencesToWrite.add(sentence);
	}
	private void begin()
	{
		mDataset.begin(ReadWrite.WRITE);
	}
	public void write()
	{
		this.begin();
		// Create the resource
		mModel.setNsPrefix("ar",AriadneStatement.URI);
		
		Property preposition;
		Resource anonObject,anonSubject,subject;
		Doublet prepData;
		int subjectCount,objectCount,prepCount;
		for(SentenceUnit senUnit:sentencesToWrite)
		{
			ArrayList<ComplexUnit> comUnits=senUnit.getComplexUnits();
			for(ComplexUnit comUnit:comUnits)
			{
				subjectCount=comUnit.getSubjectCount();
				objectCount=comUnit.getObjectCount();
				prepCount=comUnit.getAdditionalDataCount();
				for(int k=0;k<subjectCount;k++)
				{
					subject=mModel.createResource(AriadneStatement.URI+comUnit.getSubjectAt(k));
					anonObject=mModel.createResource();
                	for(int i=0;i<objectCount;i++)
                	{
                		anonObject.addProperty(AriadneStatement.object,comUnit.getObjectAt(i));
                		for(int j=0;j<prepCount;j++)
                		{
                			prepData=comUnit.getAdditionalDataAt(j);
                			preposition=mModel.createProperty(AriadneStatement.URI+prepData.getPreposition());
                			//anonObject.addProperty(AriadneStatement.preposition,prepData.getPreposition());
                			//anonObject.addProperty(AriadneStatement.prepositionData,prepData.getObject());
                			anonObject.addProperty(preposition, prepData.getObject());
                		}
                	}
                	//adding all other important data into subject node.
                	anonSubject=mModel.createResource();
                	anonSubject.addProperty(AriadneStatement.documentReference,
                			senUnit.getDocumentReference().toString());
                	anonSubject.addProperty(AriadneStatement.sentence,senUnit.getSentence());
                	anonSubject.addProperty(AriadneStatement.data,anonObject);
                	anonSubject.addProperty(AriadneStatement.verb,comUnit.getVerb());
                	subject.addProperty(AriadneStatement.statementData,anonSubject);
				}
                
			}
		}
		Logger.log("Writing data...");
		RDFDataMgr.write(this.fileOutputStream,mModel,RDFFormat.TURTLE_BLOCKS);
		Logger.log("Data successfully written.");
	}
	public void close() throws IOException
	{
		if(mModel!=null)
			mModel.close();
		mDataConfiguration.closeDataset();
		fileOutputStream.close();
	}
}
