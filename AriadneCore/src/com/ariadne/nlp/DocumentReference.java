package com.ariadne.nlp;

public class DocumentReference 
{
	private String docID;
	private int sentenceNo;
	public DocumentReference(String docId,int sentenceNo)
	{
		this.docID=docId;
		this.sentenceNo=sentenceNo;
	}
	public void setDocumentId(String docId)
	{
		this.docID=docId;
	}
	public String getDocumentId()
	{
		return this.docID;
	}
	public void setSentenceNo(int no)
	{
		this.sentenceNo=no;
	}
	public int getSentenceNo()
	{
		return this.sentenceNo;
	}
	public String toString()
	{
		return "DocumentID: "+docID+" || Sentence Number: "+sentenceNo;
	}
}
