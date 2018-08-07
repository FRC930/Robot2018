package org.usfirst.frc.team930.robot;

import edu.wpi.first.wpilibj.Timer;

/*
 * Sets up structure for subroutine in auto 
 */
public abstract class Routine {
	
	protected static String variation;
	protected int autoStep = 1;
	protected Segments segList = new Segments();
	protected Actions actList = new Actions();
	protected Timer time = new Timer();
	protected TimeDelay initDelay;
	
	/*
	 * Initializes variation, and Initial time delays
	 */
	public Routine(String v, double delay) {
		
		initDelay = new TimeDelay(delay);
		variation = v;
		reset();
		
		time.start();
		
	}
	
	/*
	 * Waits for initial time delay to pass then runs chosen variation method
	 */
	public void run() {
		
		if(initDelay.execute(time.get())) {
			this.variation();
		}
		
	}
	
	/*
	 * Runs specific variation method within the subroutine chosen for auto 
	 */
	public abstract void variation();
	/*
	 * Rests the control variable for the routine switch case
	 */
	public void reset() {
		
		this.autoStep = 1;
		
	}

}
