package com.ariadne.units;

public class AnswerUnit 
{
	public static final int YESNO=0;
	public static final int LIST=1;
	public static final int REASON=2;
	public static final int TIME=3;
	public static final int PERSON=4;
	public static final int LOCATION=5;
	
	private int type;
	private String queryString;
	private String pipeline;
	private String pipelineData;
	
	public AnswerUnit(int type)
	{
		this.type=type;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getQueryString() {
		return queryString;
	}

	public void setQueryString(String queryString) {
		this.queryString = queryString;
	}

	public String getPipeline() {
		return pipeline;
	}

	public void setPipeline(String pipeline) {
		this.pipeline = pipeline;
	}

	public String getPipelineData() {
		return pipelineData;
	}

	public void setPipelineData(String pipelineData) {
		this.pipelineData = pipelineData;
	}

}
