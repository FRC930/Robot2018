package org.usfirst.frc.team930.robot;

import edu.wpi.first.wpilibj.Timer;

public abstract class Routine {
	
	protected static String variation;
	protected int autoStep = 1;
	protected Segments segList = new Segments();
	protected Actions actList = new Actions();
	protected Timer time = new Timer();
	
	public Routine(String v, double delay) {
		
		TimeDelay time = new TimeDelay(delay);
		variation = v;
		reset();
		
	}
	
	public void run() {
		
		this.variation();
		
	}
	
	public abstract void variation();
	
	public void reset() {
		
		this.autoStep = 1;
		
	}

}
