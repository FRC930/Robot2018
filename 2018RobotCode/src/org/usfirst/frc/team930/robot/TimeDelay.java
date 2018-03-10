package org.usfirst.frc.team930.robot;

public class TimeDelay {
	
	private double delay = 0;
	private boolean timerStarted = false;
	private double start = 0;
	
	public TimeDelay(){
		delay = 0;
	}
	public TimeDelay(double d){
		delay = d;
	}
	
	public void set(double d){
		delay = d;
	}
	
	public boolean execute(double time){
		if(!timerStarted){
			start = time;
			timerStarted = true;
		}
		if(time >= start + delay){
			timerStarted = false;
			return true;
		}
		else
			return false;
		
	}
}
