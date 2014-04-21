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
	}/*
	public boolean equals(Object o)
	{
		if(o instanceof DocumentReference)
		{
			DocumentReference new_name = (DocumentReference) o;			
			if(this.getDocumentId().equals(new_name.getDocumentId())&&this.sentenceNo==new_name.getSentenceNo())
				return true;
		}
		return false;
	}*/
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((docID == null) ? 0 : docID.hashCode());
		result = prime * result + sentenceNo;
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
		DocumentReference other = (DocumentReference) obj;
		if (docID == null) {
			if (other.docID != null)
				return false;
		} else if (!docID.equals(other.docID))
			return false;
		if (sentenceNo != other.sentenceNo)
			return false;
		return true;
	}
}
