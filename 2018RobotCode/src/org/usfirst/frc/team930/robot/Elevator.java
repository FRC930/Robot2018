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
	
	// Variable Declarations
	private static ElevatorStates stateEnum;
	private static double targetPosition;
	private static boolean positionBool;
	
	public static void init() {
		// Setup the sensor
		lift1.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, Constants.kPIDLoopIdx, Constants.kTimeoutMs);
		lift1.setSensorPhase(false);
		lift1.setInverted(false);

		// Set relevant frame periods to be at least as fast as periodic rate
		lift1.setStatusFramePeriod(StatusFrameEnhanced.Status_13_Base_PIDF0, 10, Constants.kTimeoutMs);
		lift1.setStatusFramePeriod(StatusFrameEnhanced.Status_10_MotionMagic, 10, Constants.kTimeoutMs);

		// Set the peak and nominal outputs
		lift1.configNominalOutputForward(0, Constants.kTimeoutMs);
		lift1.configNominalOutputReverse(0, Constants.kTimeoutMs);
		lift1.configPeakOutputForward(1, Constants.kTimeoutMs);
		lift1.configPeakOutputReverse(-1, Constants.kTimeoutMs);
		
		// Set forward and reverse soft limits
		lift1.configForwardSoftLimitThreshold(7000, Constants.kTimeoutMs);
		lift1.configReverseSoftLimitThreshold(0, Constants.kTimeoutMs);

		// Set closed loop gains in slot 0
		lift1.selectProfileSlot(Constants.kSlotIdx, Constants.kPIDLoopIdx);
		lift1.config_kF(0, 1.89, Constants.kTimeoutMs);
		lift1.config_kP(0, 0.5, Constants.kTimeoutMs);
		lift1.config_kI(0, 0, Constants.kTimeoutMs);
		lift1.config_kD(0, 10, Constants.kTimeoutMs);
		
		// Set acceleration and cruise velocity
		lift1.configMotionCruiseVelocity(800, Constants.kTimeoutMs);
		lift1.configMotionAcceleration(800, Constants.kTimeoutMs);
		
		// Zero the sensor
		lift1.setSelectedSensorPosition(0, Constants.kPIDLoopIdx, Constants.kTimeoutMs);
		
		// Set starting target position to intake position
		targetPosition = Constants.intakePosition;
		positionBool = true;
	}
	
	// Set motor's position to double value that is passed through using motion magic
	public static void goToPosition(double height) {
		lift1.set(ControlMode.MotionMagic, height);
	}

	// Set motor's target position based on enum passed through
	public static void run(Enum pos1) {
		updateDashboard();
		stateEnum = (ElevatorStates) pos1;
		
		switch(stateEnum) {
		case INTAKE_POSITION:
			targetPosition = Constants.intakePosition;
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
		if(Math.abs(axisValue) > Constants.deadBand){
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
	public boolean atPosition() {
		if ((lift1.getSelectedSensorPosition(0) > (targetPosition - 10) && lift1.getSelectedSensorPosition(0) < (targetPosition + 10)) && positionBool) {
			return true;
		} else {
			return false;
		}
	}
	
	// Returns the actual position of the elevator
	public double getPosition() {
		return lift1.getSelectedSensorPosition(0);
	}
	public static void updateDashboard(){
		SmartDashboard.putNumber("Elevator Encoder",lift1.getSelectedSensorPosition(0));
	}
}
