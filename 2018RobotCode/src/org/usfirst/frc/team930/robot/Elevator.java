package org.usfirst.frc.team930.robot;

import org.usfirst.frc.team930.robot.TeleopHandler.ElevatorStates;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.StatusFrameEnhanced;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Timer;

public class Elevator {
	// Object Declarations
	public static TalonSRX lift1 = new TalonSRX(Constants.liftTalonID);
	
	// Variable Declarations
	private static ElevatorStates stateEnum;
	private static double targetPosition;
	
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
	}
	
	// Set motor's position to double value that is passed through using motion magic
	public static void goToPosition(double height) {
		lift1.set(ControlMode.MotionMagic, height);
	}

	// Set motor's target position based on enum passed through
	public static void run(Enum pos1) {
		stateEnum = (ElevatorStates) pos1;
		
		switch(stateEnum) {
		case INTAKE_POSITION:
			targetPosition = Constants.intakePosition;
			break;
		case SWITCH_POSITION:
			targetPosition = Constants.switchPosition;
			break;
		case SCALE_POSITION_L:
			targetPosition = Constants.scalePositionLow;
			break;
		case SCALE_POSITION_M:
			targetPosition = Constants.scalePositionMid;
			break;
		case SCALE_POSITION_H:
			targetPosition = Constants.scalePositionHigh;
			break;
		}
	}

	// Set motor's target position based on joystick value
	public static void run(double axisValue) {
		// If joystick moves, change target position based on the joystick's value
		if(Math.abs(axisValue) > Constants.deadBand){
			targetPosition += (axisValue * Constants.targetMultiplier);
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
	public boolean atPosition(Enum pos2) {
		stateEnum = (ElevatorStates) pos2;
		
		switch(stateEnum) {
		case INTAKE_POSITION:
			if (lift1.getSelectedSensorPosition(0) > 0 && lift1.getSelectedSensorPosition(0) < (Constants.intakePosition + 10)) {
				return true;
			} else {
				return false;
			}
		case SWITCH_POSITION:
			if (lift1.getSelectedSensorPosition(0) > (Constants.switchPosition - 10) && lift1.getSelectedSensorPosition(0) < (Constants.switchPosition + 10)) {
				return true;
			} else {
				return false;
			}
		case SCALE_POSITION_L:
			if (lift1.getSelectedSensorPosition(0) > (Constants.scalePositionLow - 10) && lift1.getSelectedSensorPosition(0) < (Constants.scalePositionLow + 10)) {
				return true;
			} else {
				return false;
			}
		case SCALE_POSITION_M:
			if (lift1.getSelectedSensorPosition(0) > (Constants.scalePositionMid - 10) && lift1.getSelectedSensorPosition(0) < (Constants.scalePositionMid + 10)) {
				return true;
			} else {
				return false;
			}
		case SCALE_POSITION_H:
			if (lift1.getSelectedSensorPosition(0) > (Constants.scalePositionHigh - 10) && lift1.getSelectedSensorPosition(0) < (Constants.scalePositionHigh + 10)) {
				return true;
			} else {
				return false;
			}
		default:
			return false;
		}
	}
	
	// Returns the actual position of the elevator
	public double getPosition() {
		return lift1.getSelectedSensorPosition(0);
	}
}
