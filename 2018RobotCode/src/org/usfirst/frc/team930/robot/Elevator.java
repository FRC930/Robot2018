package org.usfirst.frc.team930.robot;

import org.usfirst.frc.team930.robot.TeleopHandler.ElevatorStates;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.StatusFrameEnhanced;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Elevator {
	// Object Declarations
	public static TalonSRX lift1 = new TalonSRX(Constants.liftTalonID);
	public static TalonSRX lift2 = new TalonSRX(Constants.lift2TalonID);
	
	// Variable Declarations
	private static ElevatorStates stateEnum;
	private static double targetPosition;
	private static boolean positionBool;
	private static boolean check;
	private static double counter;
	
	//true is open-loop, false is closed-loop
	public static boolean loopState;
	
	public static void init() {
		
		//Sets the 2nd Talon to follow the main
		lift2.follow(lift1);
		
		// Setup the sensor
		lift1.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, Constants.kPIDLoopIdx, Constants.kTimeoutMs);
		lift1.setSensorPhase(true);
		lift1.setInverted(false);
		
		/*
		lift1.setSensorPhase(false);
		lift1.setInverted(true);
		lift2.setInverted(true);*/
		
		// Set relevant frame periods to be at least as fast as periodic rate
		lift1.setStatusFramePeriod(StatusFrameEnhanced.Status_13_Base_PIDF0, 10, Constants.kTimeoutMs);
		lift1.setStatusFramePeriod(StatusFrameEnhanced.Status_10_MotionMagic, 10, Constants.kTimeoutMs);

		// Set the peak and nominal outputs
		lift1.configNominalOutputForward(0, Constants.kTimeoutMs);
		lift1.configNominalOutputReverse(0, Constants.kTimeoutMs);
		lift1.configPeakOutputForward(1, Constants.kTimeoutMs);
		lift1.configPeakOutputReverse(-1, Constants.kTimeoutMs);
		
		// Set forward and reverse soft limits
		lift1.configForwardSoftLimitThreshold(8300, Constants.kTimeoutMs);
		lift1.configReverseSoftLimitThreshold(10, Constants.kTimeoutMs);

		// Set closed loop gains in slot 0
		lift1.selectProfileSlot(Constants.kSlotIdx, Constants.kPIDLoopIdx);
		lift1.config_kF(0, 0.5, Constants.kTimeoutMs);
		lift1.config_kP(0, 2.0, Constants.kTimeoutMs);
		lift1.config_kI(0, 0.004, Constants.kTimeoutMs);
		lift1.config_kD(0, 7.0, Constants.kTimeoutMs);

		
		// Set acceleration and cruise velocity
		lift1.configMotionCruiseVelocity(1200, Constants.kTimeoutMs);
		lift1.configMotionAcceleration(1500, Constants.kTimeoutMs);
		
		// Zero the sensor
		lift1.setSelectedSensorPosition(0, Constants.kPIDLoopIdx, Constants.kTimeoutMs);
		
		// Set starting target position to intake position
		targetPosition = Constants.intakePosition;
		positionBool = true;
		check = false;
		counter = 0;
		loopState = false;
	}
	
	// Set motor's position to double value that is passed through using motion magic
	public static void goToPosition(double height) {
		lift1.set(ControlMode.MotionMagic, height);
	}

	// Set motor's target position based on enum passed through
	public static void setTargetPos(Enum pos1) {
		updateDashboard();
		stateEnum = (ElevatorStates) pos1;
		
		switch(stateEnum) {
		case INTAKE_POSITION:
			targetPosition = Constants.intakePosition;
			positionBool = true;
			break;
		case EXCHANGE_POSITION:
			targetPosition = Constants.exchangePosition;
			positionBool = true;
			break;
		case PORTAL_POSITION:
			targetPosition = Constants.portalPosition;
			positionBool = true;
			break;
		case SWITCH_POSITION:
			targetPosition = Constants.switchPosition;
			positionBool = true;
			break;
		case SCALE_POSITION_L:
			targetPosition = Constants.scalePositionLow;
			positionBool = true;
			break;
		case SCALE_POSITION_M:
			targetPosition = Constants.scalePositionMid;
			positionBool = true;
			break;
		case SCALE_POSITION_H:
			targetPosition = Constants.scalePositionHigh;
			positionBool = true;
			break;
		}
	}

	// Set motor's target position based on joystick value
	public static void run(double axisValue) {
		// If joystick moves, change target position based on the joystick's value
		
		//getSelectedSensorPosition should return a value from 0 - 8000.
		LEDHandler.updateElevator(lift1.getSelectedSensorPosition(0));
		
		axisValue = Math.pow(axisValue, 3);
		
		if(Math.abs(axisValue) > Constants.elevatorDeadBand){
			targetPosition += (axisValue * Constants.targetMultiplier);
			positionBool = false;
		}
		
		// Keep target position within a select range
		if(targetPosition > Constants.scalePositionHigh) {
			targetPosition = Constants.scalePositionHigh;
		} else if (targetPosition < Constants.intakePosition) {
			targetPosition = Constants.intakePosition;
		}
		
		goToPosition(targetPosition);
	}
	
	// Check to confirm the elevator has reached its target position
	public static boolean atPosition() {
		if ((lift1.getSelectedSensorPosition(0) > (targetPosition - 10) && lift1.getSelectedSensorPosition(0) < (targetPosition + 10)) && positionBool) {
			return true;
		} else {
			return false;
		} 
	}
	
	// Returns the actual position of the elevator
	public static double getPosition() {
		return lift1.getSelectedSensorPosition(0);
	}
	
	//Check if encoder is returning information
	public static boolean checkSensor() {
		if(lift1.getMotorOutputPercent() != 0 && lift1.getSelectedSensorVelocity(0) == 0) {
			counter++;
			if(counter >= Constants.counterLimit) {
				check = true;
			} 
		} else {
			counter = 0;
			check = false;
		}
		
		return check;
	}
	
	// set the elevator motor to manual percent output mode
	public static void runManualControl(double axisValue) {
		lift1.set(ControlMode.PercentOutput, axisValue);
	}
	
	public static boolean atIntakePosition() {
		if((stateEnum == TeleopHandler.ElevatorStates.INTAKE_POSITION) && (lift1.getSelectedSensorPosition(0) < (Constants.intakePosition + 25)))
			return true;
		
		return false;
	}
	
	public static void switchToPercentOutput(double axisValue) {
		if(Math.abs(axisValue) > Constants.elevatorDeadBand)
			run(axisValue);
		else
			lift1.set(ControlMode.PercentOutput, 0);
	}
	
	public static void updateDashboard(){
		SmartDashboard.putNumber("Elevator Encoder",lift1.getSelectedSensorPosition(0));
	}
}
