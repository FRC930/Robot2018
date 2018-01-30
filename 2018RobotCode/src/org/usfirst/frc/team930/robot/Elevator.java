package org.usfirst.frc.team930.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.StatusFrameEnhanced;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.Joystick;

public class Elevator {
	private static TalonSRX lift1 = new TalonSRX(6);
	private static Joystick controller2 = new Joystick(2);
	
	public void init() {
		/* first choose the sensor */
		lift1.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, Constants1.kPIDLoopIdx, Constants1.kTimeoutMs);
		lift1.setSensorPhase(false);
		lift1.setInverted(false);

		/* Set relevant frame periods to be at least as fast as periodic rate */
		lift1.setStatusFramePeriod(StatusFrameEnhanced.Status_13_Base_PIDF0, 10, Constants1.kTimeoutMs);
		lift1.setStatusFramePeriod(StatusFrameEnhanced.Status_10_MotionMagic, 10, Constants1.kTimeoutMs);

		/* set the peak and nominal outputs */
		lift1.configNominalOutputForward(0, Constants1.kTimeoutMs);
		lift1.configNominalOutputReverse(0, Constants1.kTimeoutMs);
		lift1.configPeakOutputForward(1, Constants1.kTimeoutMs);
		lift1.configPeakOutputReverse(-1, Constants1.kTimeoutMs);
		
		lift1.configForwardSoftLimitThreshold(7000, Constants1.kTimeoutMs);
		lift1.configReverseSoftLimitThreshold(0, Constants1.kTimeoutMs);

		/* set closed loop gains in slot0 - see documentation */
		lift1.selectProfileSlot(Constants1.kSlotIdx, Constants1.kPIDLoopIdx);
		lift1.config_kF(0, 1.55, Constants1.kTimeoutMs);
		lift1.config_kP(0, 0.505, Constants1.kTimeoutMs);
		lift1.config_kI(0, 0.002, Constants1.kTimeoutMs);
		lift1.config_kD(0, 10, Constants1.kTimeoutMs);
		/* set acceleration and cruise velocity - see documentation */
		lift1.configMotionCruiseVelocity(680, Constants1.kTimeoutMs);
		lift1.configMotionAcceleration(680, Constants1.kTimeoutMs);
		/* zero the sensor */
		lift1.setSelectedSensorPosition(0, Constants1.kPIDLoopIdx, Constants1.kTimeoutMs);
	}
	
	public void goToPosition(double height) {
		lift1.set(ControlMode.MotionMagic, height);
	}

	public void run() {
		if (controller2.getRawButton(1)) {
			goToPosition(Constants1.intakePosition);
		} else if (controller2.getRawButton(2)) {
			goToPosition(Constants1.switchPosition);
		} else if (controller2.getRawButton(3)) {
			goToPosition(Constants1.scalePosition);
		} else {
			lift1.set(ControlMode.PercentOutput, 0);
		}
	}
	
	public double getPosition()
	{
		return lift1.getSelectedSensorPosition(0);
	}
	
	public class Constants1 {
		public static final int kSlotIdx = 0;
		public static final int kPIDLoopIdx = 0;
		public static final int kTimeoutMs = 10;
		public static final double intakePosition = 0;
		public static final double switchPosition = 2000;
		public static final double scalePosition = 7000;
	}
}
