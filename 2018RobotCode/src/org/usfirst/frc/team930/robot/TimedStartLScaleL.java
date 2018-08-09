package org.usfirst.frc.team930.robot;

import edu.wpi.first.wpilibj.Timer;

/*
 * Timed auto routine to score in left scale starting in the left position
 */
public class TimedStartLScaleL extends Routine{
	
	Timer time = new Timer();
	
	// Initializes variation and initial time delay for auto routine
	public TimedStartLScaleL(String v, double d) {
		
		super(v, d);
		time.start();
		
	}

	// Lifts elevator, drives forward, and outtakes cube
	public void variation() {
		
		Elevator.setTargetPos(TeleopHandler.ElevatorStates.SCALE_POSITION_H);
		Elevator.run(0);
		if(time.get() <= Constants.ScaleDrive1) {
			Drive.runAt(0.5, 0.5);
		}
		else if((time.get() > Constants.ScaleDrive1) && (time.get() <= Constants.ScaleOuttake)){
			Drive.runAt(0, 0);
		}
		else {
			Intake.run(TeleopHandler.IntakeStates.SLOW_OUTTAKING);
		}
		
	}

}
