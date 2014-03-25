package com.ariadne.units;

public class DocumentReference 
{
	private String docID;
	private int sentenceNo;
	public static final String delimiter=";;";
	public DocumentReference()
	{
		this.docID="";
	}
	public DocumentReference(String docRef)
	{
		String a[]=docRef.split(delimiter);
		if(a.length==2)
		{	
			this.docID=a[0];
			this.sentenceNo=Integer.parseInt(a[1]);
		}
		else if(a.length==1)
			this.docID=a[0];
	}
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
		return docID+sentenceNo;
	}
	public boolean equals(Object o)
	{
		if(o instanceof DocumentReference)
		{
			DocumentReference new_name = (DocumentReference) o;			
			if(this.getDocumentId().equals(new_name.getDocumentId())&&this.sentenceNo==new_name.getSentenceNo())
				return true;
		}
		return false;
	}
}
