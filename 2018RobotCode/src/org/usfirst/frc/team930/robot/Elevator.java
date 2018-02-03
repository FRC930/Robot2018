package org.usfirst.frc.team930.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.StatusFrameEnhanced;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.Joystick;

public class Elevator {
	private static TalonSRX lift1 = new TalonSRX(6);
	private static Joystick controller2 = new Joystick(1);
	
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
	
	public void goToPosition(double height) {
		lift1.set(ControlMode.MotionMagic, height);
	}

	public void run() {
		if (controller2.getRawButton(1)) {
			goToPosition(Constants.intakePosition);
		} else if (controller2.getRawButton(2)) {
			goToPosition(Constants.switchPosition);
		} else if (controller2.getRawButton(3)) {
			goToPosition(Constants.scalePosition);
		} else {
			lift1.set(ControlMode.PercentOutput, 0);
		}
	}
	
	/*
	  x = 1 : intake position
	  x = 2 : switch position
	  x = 3 : scale position
	 */
	public boolean atPosition(int x)
	{
		if (x == 1) {
			if (lift1.getSelectedSensorPosition(0) > 0 && lift1.getSelectedSensorPosition(0) < (Constants.intakePosition + 10)) {
				return true;
			} else {
				return false;
			}
		} else if (x == 2) {
			if (lift1.getSelectedSensorPosition(0) > (Constants.switchPosition - 10) && lift1.getSelectedSensorPosition(0) < (Constants.switchPosition + 10)) {
				return true;
			} else {
				return false;
			}
		} else if (x == 3) {
			if (lift1.getSelectedSensorPosition(0) > (Constants.scalePosition - 10) && lift1.getSelectedSensorPosition(0) < (Constants.scalePosition + 10)) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}
	
	public double getPosition() {
		return lift1.getSelectedSensorPosition(0);
	}
}
