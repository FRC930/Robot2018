package org.usfirst.frc.team930.robot;

import edu.wpi.first.wpilibj.Timer;
/*
 * Timed auto routine to cross the auto line and score on the switch
 */
public class LineScore extends Routine{
	
	Timer time = new Timer();
	
	// Initializes variation and time delay for auto routine
	public LineScore(String v, double d) {
		
		super(v, d);
		time.reset();
		time.start();
		
	}
	
	// Lifts elevator, then drives forward at 1/4 speed for 5 sec, then outtakes cube to score in switch
	public void variation() {
		
		System.out.println("Line & Score");
		System.out.println("Elevator Up");
		Elevator.setTargetPos(TeleopHandler.ElevatorStates.SCALE_POSITION_L);
		Elevator.run(0);
		if(time.get()<5)
			Drive.runAt(0.25, 0.25);
		else {
			Drive.runAt(0, 0);
			System.out.println("Outtaking Cube");
			Intake.run(TeleopHandler.IntakeStates.SLOW_OUTTAKING);
		}
			
	}

}
