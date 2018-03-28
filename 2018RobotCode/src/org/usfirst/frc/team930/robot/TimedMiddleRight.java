package org.usfirst.frc.team930.robot;

import edu.wpi.first.wpilibj.Timer;

public class TimedMiddleRight extends Routine{
	
	Timer time = new Timer();
	
	public TimedMiddleRight(String v, double d) {
		
		super(v, d);
		time.reset();
		time.start();
		
	}

	public void variation() {
		
		if(time.get() <= 1.25) {
			Drive.runAt(0.5, 0.5);
		}
		else if((time.get() > 1.25) && (time.get() <= 2.5)) {
			Drive.runAt(0.5, -0.5);
		}
		else if((time.get() > 2.5) && (time.get() <= 3.75)) {
			Drive.runAt(0.5, 0.5);
		}
		else if((time.get() > 3.75) && (time.get() <= 5)) {
			Drive.runAt(-0.5, 0.5);
		}
		else if((time.get() > 5) && (time.get() <= 6.25)) {
			Elevator.setTargetPos(TeleopHandler.ElevatorStates.SCALE_POSITION_L);
			Elevator.run(0);
			Drive.runAt(0.5, 0.5);
		}
		else {
			Intake.run(TeleopHandler.IntakeStates.OUTTAKING);
			Drive.runAt(0, 0);
		}
		
	}

}
