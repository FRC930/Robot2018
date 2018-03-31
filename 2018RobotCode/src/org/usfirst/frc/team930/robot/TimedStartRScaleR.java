package org.usfirst.frc.team930.robot;

import edu.wpi.first.wpilibj.Timer;

public class TimedStartRScaleR extends Routine{
	
	Timer time = new Timer();
	
	public TimedStartRScaleR(String v, double d) {
		
		super(v, d);
		time.start();
		
	}

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
