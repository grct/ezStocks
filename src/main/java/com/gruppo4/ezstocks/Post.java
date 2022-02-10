package com.gruppo4.ezstocks;

public class Post {
	private String ticker;
	private String starting_date;
	private String ending_date;
	
	public String getTicker() {
		return ticker;
	}
	public void setTicker(String ticker) {
		this.ticker = ticker;
	}
	public String getStarting_date() {
		return starting_date;
	}
	public void setStarting_date(String starting_date) {
		this.starting_date = starting_date;
	}
	public String getEnding_date() {
		return ending_date;
	}
	public void setEnding_date(String ending_date) {
		this.ending_date = ending_date;
	}
	
	@Override
	public String toString() {
		return "Post [ticker=" + ticker + ", starting_date=" + starting_date + ", ending_date=" + ending_date + "]";
	}
	
	
	
}
