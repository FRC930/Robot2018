package org.usfirst.frc.team930.robot;

import org.usfirst.frc.team930.robot.TeleopHandler.ElevatorStates;

import com.ctre.phoenix.motorcontrol.ControlMode;

public class Actions {
	
	public void switchPosition() {
			
		Elevator.setTargetPos(ElevatorStates.SWITCH_POSITION);
		Elevator.run(0);
		
	}
	
	public void scaleMPosition() {
		
		Elevator.setTargetPos(ElevatorStates.SCALE_POSITION_M);
		Elevator.run(0);
		
	}
	
	public void slowOuttake() {
		
		Intake.run(TeleopHandler.IntakeStates.SLOW_OUTTAKING);
		
	}
	
	public void wristUp() {
		
		Intake.run(TeleopHandler.IntakeStates.LIFTER_UP);
		
	}
	
	public void wristDown() {
		
		Intake.run(TeleopHandler.IntakeStates.LIFTER_DOWN);
		
	}
	
	public void stopIntake() {
		
		Intake.run(TeleopHandler.IntakeStates.INTAKE_DONE);
		
	}
	
	public void intake() {
		
		Intake.rightIntakeWheel.set(ControlMode.PercentOutput, -Constants.autoIntakeSpeed);
		Intake.leftIntakeWheel.set(ControlMode.PercentOutput, -Constants.autoIntakeSpeed);
		
	}

}
