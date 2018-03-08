package org.usfirst.frc.team930.robot;

import org.usfirst.frc.team930.robot.TeleopHandler.ElevatorStates;

public class Actions {
	
	public boolean switchPosition() {
		
		boolean isFinished = false;
			
		Elevator.run(ElevatorStates.SWITCH_POSITION);
		Elevator.run(0);
		isFinished = true;
		
		return isFinished;
		
	}
	
	public boolean scaleMPosition() {
		
		boolean isFinished = false;
		
		Elevator.run(ElevatorStates.SCALE_POSITION_M);
		Elevator.run(0);
		isFinished = true;
		
		return isFinished;
		
	}
	
	public void slowOuttake() {
		
		Intake.run(TeleopHandler.IntakeStates.SLOW_OUTTAKING);
		
	}
	
	public void wristUp() {
		
		Intake.run(TeleopHandler.IntakeStates.LIFTER_DOWN);
		
	}
	
	public void stopIntake() {
		
		Intake.run(TeleopHandler.IntakeStates.INTAKE_DONE);
		
	}

}
