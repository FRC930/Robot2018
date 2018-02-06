package org.usfirst.frc.team930.robot;

import org.usfirst.frc.team930.robot.TeleopHandler.States;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.StatusFrameEnhanced;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

public class Elevator {
	public static TalonSRX lift1 = new TalonSRX(6);
	private static States stateEnum;
	
	public static void init() {
		/* first choose the sensor */
		lift1.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, Constants.kPIDLoopIdx, Constants.kTimeoutMs);
		lift1.setSensorPhase(false);
		lift1.setInverted(true);

		/* Set relevant frame periods to be at least as fast as periodic rate */
		lift1.setStatusFramePeriod(StatusFrameEnhanced.Status_13_Base_PIDF0, 10, Constants.kTimeoutMs);
		lift1.setStatusFramePeriod(StatusFrameEnhanced.Status_10_MotionMagic, 10, Constants.kTimeoutMs);

		/* set the peak and nominal outputs */
		lift1.configNominalOutputForward(0, Constants.kTimeoutMs);
		lift1.configNominalOutputReverse(0, Constants.kTimeoutMs);
		lift1.configPeakOutputForward(1, Constants.kTimeoutMs);
		lift1.configPeakOutputReverse(-1, Constants.kTimeoutMs);
		
		lift1.configForwardSoftLimitThreshold(7000, Constants.kTimeoutMs);
		lift1.configReverseSoftLimitThreshold(0, Constants.kTimeoutMs);

		/* set closed loop gains in slot0 - see documentation */
		lift1.selectProfileSlot(Constants.kSlotIdx, Constants.kPIDLoopIdx);
		lift1.config_kF(0, 1.89, Constants.kTimeoutMs);
		lift1.config_kP(0, 0.5, Constants.kTimeoutMs);
		lift1.config_kI(0, 0, Constants.kTimeoutMs);
		lift1.config_kD(0, 10, Constants.kTimeoutMs);
		/* set acceleration and cruise velocity - see documentation */
		lift1.configMotionCruiseVelocity(800, Constants.kTimeoutMs);
		lift1.configMotionAcceleration(800, Constants.kTimeoutMs);
		/* zero the sensor */
		lift1.setSelectedSensorPosition(0, Constants.kPIDLoopIdx, Constants.kTimeoutMs);
	}
	
	public static void goToPosition(double height) {
		lift1.set(ControlMode.MotionMagic, height);
	}

	public static void run(Enum pos1) {
		stateEnum = (States) pos1;
		
		switch(stateEnum) {
		case INTAKE_POSITION:
			goToPosition(Constants.intakePosition);
			break;
		case SWITCH_POSITION:
			goToPosition(Constants.switchPosition);
			break;
		case SCALE_POSITION_L:
			goToPosition(Constants.scalePositionLow);
			break;
		case SCALE_POSITION_M:
			goToPosition(Constants.scalePositionMid);
			break;
		case SCALE_POSITION_H:
			goToPosition(Constants.scalePositionHigh);
			break;
		default:
			lift1.set(ControlMode.PercentOutput, 0);
		}
	}

	
	public boolean atPosition(Enum pos2) {
		stateEnum = (States) pos2;
		
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
	
	public double getPosition() {
		return lift1.getSelectedSensorPosition(0);
	}
}
