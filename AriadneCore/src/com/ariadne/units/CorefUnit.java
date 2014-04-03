package com.ariadne.units;

public class CorefUnit 
{
	private String docRef;
	private int sentenceNo;
	private int wordNo;
	//public NERUnit(){}
	public CorefUnit(String docID)
	{
		this.docRef=docID;
	}
	public CorefUnit(String docID,int sent,int word)
	{
		this.docRef=docID;
		this.sentenceNo=sent;
		this.wordNo=word;
	}
	public String getDocRef() {
		return docRef;
	}
	public void setDocRef(String docRef) {
		this.docRef = docRef;
	}
	public int getSentenceNo() {
		return sentenceNo;
	}
	public void setSentenceNo(int sentenceNo) {
		this.sentenceNo = sentenceNo;
	}
	public int getWordNo() {
		return wordNo;
	}
	public void setWordNo(int wordNo) {
		this.wordNo = wordNo;
	}
	/*public boolean equals(Object o)
	{
		if(o instanceof CorefUnit)
		{
			CorefUnit test=(CorefUnit)o;
			if(this.docRef.equals(test.getDocRef())
					&&this.sentenceNo==test.getSentenceNo()
					&&this.wordNo==test.getWordNo())
				return true;
			else return false;
		}
		else return false;
	}*/
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((docRef == null) ? 0 : docRef.hashCode());
		result = prime * result + sentenceNo;
		result = prime * result + wordNo;
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CorefUnit other = (CorefUnit) obj;
		if (docRef == null) {
			if (other.docRef != null)
				return false;
		} else if (!docRef.equals(other.docRef))
			return false;
		if (sentenceNo != other.sentenceNo)
			return false;
		if (wordNo != other.wordNo)
			return false;
		return true;
	}
	public String toString()
	{
		return docRef+" "+sentenceNo+" "+wordNo;
	}

}
