package org.usfirst.frc.team930.robot;

import edu.wpi.first.wpilibj.Timer;

/*
 * Timed auto routine to lift the elevator to the switch position and cross the auto line
 */
public class LineElevator extends Routine{
	
	Timer time = new Timer();
	
	// Initializes variation and time delay for auto routine
	public LineElevator(String v, double d) {
		
		super(v, d);
		time.reset();
		time.start();
		
	}
	
	// Lifts elevator then drives forward at 1/4 speed for 5 sec
	public void variation() {
		
		System.out.println("Line");
		System.out.println("Elevator Up");
		Elevator.setTargetPos(TeleopHandler.ElevatorStates.SWITCH_POSITION);
		Elevator.run(0);
		if(time.get() <= 5)
			Drive.runAt(0.25, 0.25);
		else {
			Drive.runAt(0, 0);
		}
	}

}

