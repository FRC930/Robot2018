package org.usfirst.frc.team930.robot;

import org.usfirst.frc.team930.robot.TeleopHandler.ElevatorStates;

import com.ctre.phoenix.motorcontrol.ControlMode;

/*
 * Methods for robot functions during autonomous.
 * (Ex: Elevator to switch position, intake, outtake, etc.)
 */
public class Actions {
	
	/*
	 * Elevator moves to switch position
	 */
	public void switchPosition() {
			
		Elevator.setTargetPos(ElevatorStates.SWITCH_POSITION);
		Elevator.run(0);
		
	}
	
	/*
	 * Elevator moves to middle scale position
	 */
	public void scaleMPosition() {
		
		Elevator.setTargetPos(ElevatorStates.SCALE_POSITION_M);
		Elevator.run(0);
		
	}
	
	/*
	 * Elevator moves to high scale position
	 */
	public void scaleHPosition() {
		
		Elevator.setTargetPos(ElevatorStates.SCALE_POSITION_H);
		Elevator.run(0);
		
	}
	
	/*
	 * Outtake cube
	 */
	public void slowOuttake() {
		
		Intake.run(TeleopHandler.IntakeStates.SLOW_OUTTAKING);
		
	}
	
	/*
	 * Intake wrist lifts up
	 */
	public void wristUp() {
		
		Intake.run(TeleopHandler.IntakeStates.LIFTER_UP);
		
	}
	
	/*
	 * Intake wrist goes down
	 */
	public void wristDown() {
		
		Intake.run(TeleopHandler.IntakeStates.LIFTER_DOWN);
		
	}
	
	/*
	 * Intake wheels stop
	 */
	public void stopIntake() {
		
		Intake.run(TeleopHandler.IntakeStates.INTAKE_DONE);
		
	}
	
	/*
	 * Intake cube
	 */
	public void intake() {
		
		Intake.rightIntakeWheel.set(ControlMode.PercentOutput, -Constants.autoIntakeSpeed);
		Intake.leftIntakeWheel.set(ControlMode.PercentOutput, -Constants.autoIntakeSpeed);
		
	}

}
