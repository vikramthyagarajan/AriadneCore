package com.ariadne.nlp;

import com.ariandne.util.Logger;

public class Test 
{
	public static void main(String[] args)
	{
		Logger.init();
		ParserHelper.parse("Abby is liked by Sam in China: which she hated.");
		ParserHelper.parse("The first working steam-powered vehicle was designed - and most " +
				"likely built - by Ferdinand Verbiest, a Flemish member of a Jesuit mission in " +
				"China around 1672." +
				" Nicolas-Joseph Cugnot is widely credited with building the first full-scale, " +
				"self-propelled mechanical vehicle or automobile in about 1769.	");
		ParserHelper.parse("Bell, based in Los Angeles, " +
				"makes and distributes electronic, computer and building products.");		
		//ParserHelper.parse("Bell, based in Los Angeles, makes " +
			//	"and distributes electronic, computer and building products.");
	}
}
