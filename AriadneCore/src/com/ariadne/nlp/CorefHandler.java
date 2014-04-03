package com.ariadne.nlp;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.ariadne.units.CorefUnit;
import com.ariadne.util.Logger;

import edu.stanford.nlp.dcoref.CorefChain;
import edu.stanford.nlp.dcoref.CorefChain.CorefMention;
import edu.stanford.nlp.dcoref.Dictionaries.MentionType;
import edu.stanford.nlp.ling.IndexedWord;

public class CorefHandler
{
	/*
	 * The explanation of the use of the variables used in this class-
	 * headMentionLookup maps corefMentions to their Representative mentions(their parent)
	 * mentionsLeft consists of all representative mentions. While parsing, we check each word,
	 * 		and if they're in this queue, then their respective String is captured and 
	 * added to mentionStringLookup variable.
	 */
	private HashMap<CorefUnit,CorefUnit> headMentionLookup;
	private HashMap<CorefUnit,String> mentionStringLookup;
	private String documentID;
	
	public CorefHandler(String docID)
	{
		headMentionLookup=new HashMap<CorefUnit, CorefUnit>();
		mentionStringLookup=new HashMap<CorefUnit, String>();
		this.documentID=docID;
	}
	public CorefHandler(int capacity)
	{
		headMentionLookup=new HashMap<CorefUnit, CorefUnit>(capacity);
		mentionStringLookup=new HashMap<CorefUnit, String>(capacity);
	}
	public void init(Map<Integer,CorefChain> corefGraph)
	{
		Iterator<Integer> itr = corefGraph.keySet().iterator();

        while (itr.hasNext()) 
        {
        	Integer ch=itr.next();
            String key = ch.toString();
             
            if(corefGraph.get(ch)!=null)
            {
            	 CorefChain mChain=corefGraph.get(ch);
            	 CorefMention representativeMention=mChain.getRepresentativeMention();
            	 Logger.log("rep: "+representativeMention.toString());
            	 CorefUnit rep=new CorefUnit(this.documentID, representativeMention.sentNum,representativeMention.headIndex),men;
            	 if(mChain.getMentionsInTextualOrder().size()<=1)
            		 continue;
            	 mentionStringLookup.put(rep,"");
            	 for(CorefMention mention:mChain.getMentionsInTextualOrder())
             	 {
             		 Logger.log("mens :"+mention.toString()+" id :"+mention.headIndex+" "+mention.mentionType);
             		 if(mention.mentionType==MentionType.PRONOMINAL)
             		 {
             			 men=new CorefUnit(this.documentID, mention.sentNum,mention.headIndex);
             			 headMentionLookup.put(men, rep);
             		 }
             	 }
            }     
        }
	}
	public CorefUnit getCorefUnitFromMention(CorefMention cm,String docID)
	{
		return new CorefUnit(docID,cm.sentNum,cm.headIndex);
	}
	public CorefUnit getCorefUnitFromWord(IndexedWord ind)
	{
		return new CorefUnit(this.documentID,ind.sentIndex()+1,ind.index());
	}
	public boolean isRepresentativeMention(IndexedWord ind)
	{
		String test=mentionStringLookup.get(getCorefUnitFromWord(ind));
		if(test==null)
			return false;
		else return true;
	}
	public boolean isMention(IndexedWord ind)
	{
		CorefUnit test=headMentionLookup.get(getCorefUnitFromWord(ind));
		if(test==null)
			return false;
		else return true;
	}
	public void setStringMention(IndexedWord representativeMention,String data)
	{
		mentionStringLookup.put(new CorefUnit(documentID, representativeMention.sentIndex()+1, 
										representativeMention.index()),data);
	}
	public String getRepresentativeMentionOf(IndexedWord ind)
	{
		CorefUnit head=headMentionLookup.get(getCorefUnitFromWord(ind));
		String result=mentionStringLookup.get(head);
		return result;
	}
}
