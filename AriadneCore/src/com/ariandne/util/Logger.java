package com.ariandne.util;

public class Logger 
{
	private static boolean listening;
	public static void init()
	{
		listening=true;
		Logger.log("Started Listening");
	}
	public static void log(String data)
	{
		if(listening)
			System.out.println(data);
	}
	
}
