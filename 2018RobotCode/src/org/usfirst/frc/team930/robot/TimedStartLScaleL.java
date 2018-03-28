package org.usfirst.frc.team930.robot;

import edu.wpi.first.wpilibj.Timer;

public class TimedStartLScaleL extends Routine{
	
	Timer time = new Timer();
	
	public TimedStartLScaleL(String v, double d) {
		
		super(v, d);
		time.reset();
		time.start();
		
	}

	public void variation() {
		
		Elevator.setTargetPos(TeleopHandler.ElevatorStates.SCALE_POSITION_H);
		Elevator.run(0);
		if(time.get() <= 4) {
			Drive.runAt(0.5, 0.5);
		}
		else {
			Intake.run(TeleopHandler.IntakeStates.OUTTAKING);
			Drive.runAt(0, 0);
		}
		
	}

}
