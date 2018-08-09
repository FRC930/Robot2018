package org.usfirst.frc.team930.robot;

import edu.wpi.first.wpilibj.Timer;

/*
 * Timed auto routine to cross the auto line
 */
public class Line extends Routine{
	
	Timer time = new Timer();
	
	// Initializes variation and time delay for auto routine
	public Line(String v, double d) {
		
		super(v, d);
		time.reset();
		time.start();
		
	}

	// Driving forward at 1/4 speed for 5 sec
	public void variation() {
		
		System.out.println("Just Line");
		if(time.get() <= 5)
			Drive.runAt(0.25, 0.25);
		else {
			Drive.runAt(0, 0);
		}
		
	}

}

