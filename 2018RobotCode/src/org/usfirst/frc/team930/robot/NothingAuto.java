package org.usfirst.frc.team930.robot;

import edu.wpi.first.wpilibj.Timer;

/*
 * Auto in which the robot does nothing 
 */
public class NothingAuto extends Routine{
	
	Timer time = new Timer();
	
	/*
	 * Initializes the variation and delay
	 */
	public NothingAuto(String v, double d) {
		
		super(v, d);
		time.reset();
		time.start();
		
	}

	/*
	 * Setting the drive motors to zero
	 */
	public void variation() {
		
		System.out.println("Nothing Auto");
		Drive.runAt(0, 0);
		
	}

}

