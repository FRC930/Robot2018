package org.usfirst.frc.team930.robot;

import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class TeleopHandler {
	
	private static final Joystick stick1 = new Joystick(0);
	private static final Joystick stick2 = new Joystick(1);
	private static final Joystick stick3 = new Joystick(2);
	private static boolean buttonCheckA = false;
	private static boolean buttonCheckB = false;
	private static boolean buttonCheckY = false;
	private static boolean buttonCheckX = false;
	private static boolean buttonCheckStart = false;
	private static boolean buttonCheckLeftJoyButton = false;
	private static boolean buttonCheckRightJoyButton = false;
	private static boolean toggledTwice = false;
	private static boolean trentonCheck = false;
	
	
	public static void init() {
		Utilities.setCompressor(true);
	}
	
	public static void disabled() {
		//Drive.updateDashboard();
		//Elevator.updateDashboard();
		//Elevator.setTargetPos(ElevatorStates.INTAKE_POSITION);
		//Elevator.run(0);
		LEDHandler.run(RobotStates.DISABLED);
		//Utilities.updateDashboard();
	}
	
	enum RobotStates {
		ENABLED,
		DISABLED,
		INTAKING,
		INTAKE_DONE,
		OUTTAKING,
		RAMPS_DOWN,
		RAMPS_UP
	}
	
	enum IntakeStates{
		INTAKING,
		INTAKE_DONE,
		OUTTAKING,
		SLOW_OUTTAKING,
		LIFTER_UP,
		LIFTER_DOWN
	}
	enum ElevatorStates{
		INTAKE_POSITION,
		EXCHANGE_POSITION,
		PORTAL_POSITION,
		SWITCH_POSITION,
		SCALE_POSITION_L,
		SCALE_POSITION_M,
		SCALE_POSITION_H
	}
	enum RampStates{
		RIGHT_RAMP_DOWN,
		LEFT_RAMP_DOWN,
		RIGHT_RAMP_UP,
		LEFT_RAMP_UP
	}
	
	public static void run() {
		
		// LEDs
		if((stick2.getRawAxis(Constants.rightTriggerAxis) <= 0.7) && (stick2.getRawAxis(Constants.leftTriggerAxis) <= 0.7)) {
			LEDHandler.run(RobotStates.ENABLED);
		}
		
		// Drive
		Drive.run(stick1.getRawAxis(Constants.rightXaxis), stick1.getRawAxis(Constants.leftYaxis));
		
		// Intake
		if(stick2.getRawAxis(Constants.rightTriggerAxis) > 0.7)	{
			Intake.run(IntakeStates.INTAKING);
			LEDHandler.run(RobotStates.INTAKING);
		} 
		else if(stick2.getRawAxis(Constants.leftTriggerAxis) > 0.7) {															
			Intake.run(IntakeStates.OUTTAKING);
			LEDHandler.run(RobotStates.OUTTAKING);
		} 
		else if(stick2.getRawButton(Constants.LB)) {
			Intake.run(IntakeStates.SLOW_OUTTAKING);
		}
		else if(stick2.getRawButton(Constants.RB)) {
			Intake.run(IntakeStates.LIFTER_DOWN);
			Intake.stopMotors();
		} 
		else {
			Intake.run(IntakeStates.INTAKE_DONE);
		}
		
		
		// Elevator
		if (stick2.getRawButton(Constants.startButton) && (!buttonCheckStart)) {
			buttonCheckStart = true;	
		}
		else if ((!stick2.getRawButton(Constants.startButton)) && buttonCheckStart) {
			buttonCheckStart = false;
			trentonCheck = !trentonCheck;
			if(!trentonCheck){
				Elevator.resetElevatorSensor();
			}
		}
		
		if(trentonCheck){
			Elevator.runManualControl(stick2.getRawAxis(Constants.rightYaxis));
		}
		else{
			// Button Control
			if (stick2.getRawButton(Constants.A) && (!buttonCheckA)) {
				buttonCheckA = true;
				Elevator.setTargetPos(ElevatorStates.SCALE_POSITION_L);
			}
			else if ((!stick2.getRawButton(Constants.A)) && buttonCheckA) {
				buttonCheckA = false;
			}
			
			if (stick2.getRawButton(Constants.B) && (!buttonCheckB)) {
				buttonCheckB = true;
				Elevator.setTargetPos(ElevatorStates.SCALE_POSITION_M);
			}
			else if ((!stick2.getRawButton(Constants.B)) && buttonCheckB) {
				buttonCheckB = false;
			}
			
			if (stick2.getRawButton(Constants.X) && (!buttonCheckX)) {
				buttonCheckX = true;
				Elevator.setTargetPos(ElevatorStates.SWITCH_POSITION);
			}
			else if ((!stick2.getRawButton(Constants.X)) && buttonCheckX) {
				buttonCheckX = false;
			}
			
			if (stick2.getRawButton(Constants.Y) && (!buttonCheckY)) {
				buttonCheckY = true;
				Elevator.setTargetPos(ElevatorStates.SCALE_POSITION_H);
			}
			else if ((!stick2.getRawButton(Constants.Y)) && buttonCheckY) {
				buttonCheckY = false;
			}
			
			if (stick2.getRawButton(Constants.rightJoyButton) && (!buttonCheckRightJoyButton)) {
				buttonCheckRightJoyButton = true;
				Elevator.setTargetPos(ElevatorStates.INTAKE_POSITION);
			}
			else if ((!stick2.getRawButton(Constants.rightJoyButton)) && buttonCheckRightJoyButton) {
				buttonCheckRightJoyButton = false;
			}
			
			if (stick2.getRawButton(Constants.leftJoyButton) && (!buttonCheckLeftJoyButton)) {
				buttonCheckLeftJoyButton = true;
				Elevator.setTargetPos(ElevatorStates.EXCHANGE_POSITION);
			}
			else if ((!stick2.getRawButton(Constants.leftJoyButton)) && buttonCheckLeftJoyButton) {
				buttonCheckLeftJoyButton = false;
			}
			
			
			// Stop elevator if encoder is not returning information
			if(Elevator.checkSensor()) {
				if(Math.abs(stick2.getRawAxis(Constants.rightYaxis)) > Constants.elevatorDeadBand) {
					System.out.println("Sending " + stick2.getRawAxis(Constants.rightYaxis));
					Elevator.runManualControl(stick2.getRawAxis(Constants.rightYaxis));
					//Elevator.switchToPercentOutput();
				} else {
					Elevator.runManualControl(0);
					//Elevator.switchToPercentOutput();
				}
			}
			// Turn off Motion Magic at intake position
			else if(Elevator.atIntakePosition()) {
				Elevator.switchToPercentOutput(stick2.getRawAxis(Constants.rightYaxis));
			}
			// Manual control with Motion Magic
			else if(Math.abs(stick2.getRawAxis(Constants.rightYaxis)) > Constants.elevatorDeadBand) {
				Elevator.run(stick2.getRawAxis(Constants.rightYaxis));
				System.out.println("Lift1: " + Elevator.lift1.getMotorOutputPercent() + " Lift2: " + Elevator.lift2.getMotorOutputPercent());
			}
			// Run Motion Magic using button positions
			else {
				Elevator.run(stick2.getRawAxis(Constants.rightYaxis));
			}
		}
		//Elevator.runManualControl(-1.0 * stick2.getRawAxis(Constants.rightYaxis));

		
		
		// Ramps
		if(stick3.getRawButton(Constants.btnRightRampDown)) {
			Ramp.run(RampStates.RIGHT_RAMP_DOWN);
			LEDHandler.run(RobotStates.RAMPS_DOWN);
		}
		else {
			Ramp.resetRightRelease();
		}
		if(stick3.getRawButton(Constants.btnLeftRampDown)) {
			Ramp.run(RampStates.LEFT_RAMP_DOWN);
			LEDHandler.run(RobotStates.RAMPS_DOWN);
		}
		else {
			Ramp.resetLeftRelease();
		}
		if(stick3.getRawButton(Constants.btnRightRampUp)) {
			Ramp.run(RampStates.RIGHT_RAMP_UP);
			LEDHandler.run(RobotStates.RAMPS_UP);
		}
		else {
			Ramp.resetRightLift();
		}
		if(stick3.getRawButton(Constants.btnLeftRampUp)) {
			Ramp.run(RampStates.LEFT_RAMP_UP);
			LEDHandler.run(RobotStates.RAMPS_UP);
		}
		else {
			Ramp.resetLeftLift();
		}
		
	}
	
	public static void setRumble(int controller, double intensity){
		if(controller == 1){
			stick1.setRumble(GenericHID.RumbleType.kLeftRumble, intensity);
			stick1.setRumble(GenericHID.RumbleType.kRightRumble, intensity);
		}
		else if(controller == 2){
			stick2.setRumble(GenericHID.RumbleType.kLeftRumble, intensity);
			stick2.setRumble(GenericHID.RumbleType.kRightRumble, intensity);
		}
	}

}
