package com.ariadne.nlp;

import java.util.ArrayList;
import java.util.Scanner;

import com.ariadne.data.AriadneDataReader;
import com.ariadne.data.AriadneDataWriter;
import com.ariadne.data.DataConfiguration;
import com.ariadne.units.AnswerUnit;
import com.ariadne.units.AriadneStatement;
import com.ariadne.units.QuestionUnit;
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
			//TextProcessor tp=new TextProcessor();
			//tp.uploadText(sc.nextLine());
			sentences=new AriadneDataReader(new DataConfiguration())
							.queryModel("construct where { <http://ariadne.com/Karl_Benz> <http://ariadne.com/statementData> ?s." +
									"?s <http://ariadne.com/docRef> ?n." +
									"?s <http://ariadne.com/verb> \"invent\"." +
									"?s <http://ariadne.com/data> ?h. "+
									"?h <http://ariadne.com/object> \"car\".}",AriadneDataReader.CONSTRUCT_QUERY);
			for(SentenceUnit s:sentences)
				Logger.log(s.toString());
			sentences=new AriadneDataReader(new DataConfiguration())
			.queryModel("construct where { <http://ariadne.com/car> <http://ariadne.com/statementData> ?s." +
					"?s <http://ariadne.com/docRef> ?n." +
					"?s <http://ariadne.com/verb> ?kkk." +
					"?s <http://ariadne.com/data> ?h. "+
					"?h <http://ariadne.com/object> ?sens.}",AriadneDataReader.CONSTRUCT_QUERY);
			for(SentenceUnit s:sentences)
				Logger.log(s.toString());
			//QueryProcessor qp=new QueryProcessor();
			//Logger.log(qp.executeQuery(sc.nextLine()));
        }
		catch (Exception e) 
		{
			Logger.log("Error encountered while closing dataset.");
			e.printStackTrace();
		}
	}
}
