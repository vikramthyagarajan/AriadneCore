package com.ariadne.nlp;

import java.io.IOException;
import java.util.ArrayList;

import com.ariadne.data.AriadneDataWriter;
import com.ariadne.data.DataConfiguration;
import com.ariadne.units.SentenceUnit;

public class TextProcessor 
{
	/*private TextParserHelper mTextParserHelper;
	private AriadneDataWriter mDataWriter;
	
	public TextProcessor() throws IOException
	{
		mTextParserHelper=new TextParserHelper();
		mDataWriter=new AriadneDataWriter(new DataConfiguration());//DataConfiguration.getDataset(),DataConfiguration.getModel());
	}

	public void uploadText(String text,String documentID)
	{
		try 
		{
			ArrayList<SentenceUnit>sentences=mTextParserHelper.parse(text,documentID);
			for(SentenceUnit sentence:sentences)
				mDataWriter.addSentenceToBeWritten(sentence);
			mDataWriter.write();
			mDataWriter.close();
		}
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		
	}*/
}
