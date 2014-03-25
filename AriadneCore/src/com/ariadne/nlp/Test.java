package com.ariadne.nlp;

import java.util.ArrayList;
import java.util.Scanner;

import com.ariadne.data.AriadneDataReader;
import com.ariadne.data.AriadneDataWriter;
import com.ariadne.data.DataConfiguration;
import com.ariadne.units.SentenceUnit;
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
			sentences=ParserHelper.parse(sc.nextLine());
			for(SentenceUnit senUnit:sentences)
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
        	adr.queryModel("describe ?s where {?s <http://amazing.com/statementData> ?o." +
        			"?o <http://amazing.com/data> ?d." +
        			"?o <http://amazing.com/verb> ?v." +
        			"?d <http://amazing.com/object> \"Abby\"}",0);
        	Logger.log("<>");
        */
        }
		catch (Exception e) 
		{
			Logger.log("Error encountered while closing dataset.");
			e.printStackTrace();
		}
	}
}
