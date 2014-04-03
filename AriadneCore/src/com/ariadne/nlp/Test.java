package com.ariadne.nlp;

import java.util.ArrayList;
import java.util.Scanner;

import com.ariadne.data.AriadneDataReader;
import com.ariadne.data.AriadneDataWriter;
import com.ariadne.data.DataConfiguration;
import com.ariadne.units.AnswerUnit;
import com.ariadne.units.AriadneStatement;
import com.ariadne.units.SentenceUnit;
import com.ariadne.units.SimpleQuestionUnit;
import com.ariadne.util.Logger;

public class Test 
{
	public static void main(String[] args)
	{
		Logger.init();
		Scanner sc=new Scanner(System.in);
        AriadneDataWriter adw;
        ArrayList<SentenceUnit>sentences=new ArrayList<SentenceUnit>();
		try 
		{
			
			adw = new AriadneDataWriter(DataConfiguration.getDataset(),
					DataConfiguration.getModel());
			TextParserHelper ph=new TextParserHelper();
			sentences=ph.parse(sc.nextLine());
			for(SentenceUnit senUnit:sentences)
				//Logger.log(senUnit.toString());
                adw.addSentenceToBeWritten(senUnit);
			//ParserHelper.parse("Sam likes Abby.");
			//ParserHelper.parse("The first working steam-powered vehicle was designed - and most " +
				//"likely built - by Ferdinand Verbiest, a Flemish member of a Jesuit mission in " +
				//"China around 1672.");
			///ParserHelper.parse(" Nicolas-Joseph Cugnot is widely credited with building the first full-scale, " +
				//	"self-propelled mechanical vehicle or automobile in about 1769.	");
			//ParserHelper.parse("Bell makes laptops, which are small computers");		
			//ParserHelper.parse("Bell, based in Los Angeles, makes " +
				//	"and distributes electronic, computer and building products.");
			adw.write();
			adw.close();
			DataConfiguration.closeDataset();
			/*
        	AriadneDataReader adr=new AriadneDataReader(DataConfiguration.getDataset(),
        			DataConfiguration.getModel());
        	SimpleQuestionUnit squ=new SimpleQuestionUnit(AnswerUnit.LIST);
        	squ.setSubject("Sam");
        	//squ.setVerb("loved");
        	//squ.setObject("Jim");
        	squ.setPrep("with");
        	//sentences=adr.queryModel(squ.generateQuery(),AriadneDataReader.CONSTRUCT_QUERY);
        	
        	sentences=adr.queryModel("construct where {<http://ariadne.com/Sam> <http://ariadne.com/statementData> ?o." +
        			"?o <http://ariadne.com/data> ?d." +
        			"?o <http://ariadne.com/docRef> ?r." +
        			"?o <http://ariadne.com/verb> ?v." +
        			"?d <http://ariadne.com/object> ?h." +
        			"?d <with> ?pd}",AriadneDataReader.CONSTRUCT_QUERY);
        			
        	Logger.log("displaying results:");
			for(SentenceUnit s:sentences)
				Logger.log(s.toString());
        */
        }
		catch (Exception e) 
		{
			Logger.log("Error encountered while closing dataset.");
			e.printStackTrace();
		}
	}
}
