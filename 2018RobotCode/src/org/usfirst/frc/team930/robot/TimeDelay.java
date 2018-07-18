package org.usfirst.frc.team930.robot;
//Sets different delays all in auto
public class TimeDelay {
	
	private double delay = 0;
	private boolean timerStarted = false;
	private double start = 0;
	//Sets delay to 0
	public TimeDelay(){
		delay = 0;
	}
	//Sets delay to d on the activation of timedelay veriable
	public TimeDelay(double d){
		delay = d;
	}
	//Changes delay to d
	public void set(double d){
		delay = d;
	}
	//waits and then goes after done returns true when time delay is done.
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
