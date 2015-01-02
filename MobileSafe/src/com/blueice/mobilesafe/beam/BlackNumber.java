package com.blueice.mobilesafe.beam;

public class BlackNumber {

	private String number;
	private String mode;
	
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public String getMode() {
		return mode;
	}
	public void setMode(String mode) {
		this.mode = mode;
	}
	@Override
	public String toString() {
		return "BlackNumber [number=" + number + ", mode=" + mode + "]";
	}
}
