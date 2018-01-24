package org.usfirst.frc.team930.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.StatusFrameEnhanced;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.TimedRobot;



public class Robot extends TimedRobot {
	
	WPI_TalonSRX lift1 = new WPI_TalonSRX(0);
	VictorSPX lift2 = new VictorSPX(1);
	Joystick controller = new Joystick(0);
	
	boolean aPressed, onOffA, bPressed, onOffB, test1;
	double targetPosition, fValue, pValue, iValue, dValue;
	int velocity, acceleration;
	
	@Override
	public void robotInit() {		
		
		// Set PIDF values, velocity, acceleration, and target position
		fValue = 0.2;
		pValue = 0.2;
		iValue = 0.0;
		dValue = 0.0;
		velocity = 6000;
		acceleration = 15000;
		targetPosition = 9000.0;
		// ------------------------------------------------------------
		
		
		aPressed = false;
		onOffA = false;
		bPressed = false;
		onOffB = false;
		
		lift2.set(ControlMode.Follower, 0);
		
		lift1.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 10);
		lift1.setSensorPhase(true);
		lift1.setInverted(false);
		
		lift1.setStatusFramePeriod(StatusFrameEnhanced.Status_13_Base_PIDF0, 10, 10);
		lift1.setStatusFramePeriod(StatusFrameEnhanced.Status_10_MotionMagic, 10, 10);
		
		lift1.configNominalOutputForward(0, 10);
		lift1.configNominalOutputReverse(0, 10);
		lift1.configPeakOutputForward(1, 10);
		lift1.configPeakOutputReverse(-1, 10);
		
		lift1.selectProfileSlot(0, 0);
		lift1.config_kF(0, fValue, 10);
		lift1.config_kP(0, pValue, 10);
		lift1.config_kI(0, iValue, 10);
		lift1.config_kD(0, dValue, 10);
		lift1.configMotionCruiseVelocity(velocity, 10);
		lift1.configMotionAcceleration(acceleration, 10);
		lift1.setSelectedSensorPosition(0, 0, 10);
	}

	
	@Override
	public void autonomousInit() {
	}

	
	@Override
	public void autonomousPeriodic() {
	}

	
	@Override
	public void teleopPeriodic() {
		
		//a pressed -- elevator up
		if(controller.getRawButton(1) && (!aPressed))
		{
			aPressed = true;
			onOffA = !onOffA;
		} else if((!controller.getRawButton(1)) && aPressed) {
			aPressed = false;
		}
		
		if (onOffA) {
			double targetPos = targetPosition;
			lift1.set(ControlMode.MotionMagic, targetPos);
			onOffA = false;
		} else {
			lift1.set(ControlMode.PercentOutput, 0);
		}
		
		//b pressed -- elevator down
		if(controller.getRawButton(2) && (!bPressed))
		{
			bPressed = true;
			onOffB = !onOffB;
		} else if((!controller.getRawButton(2)) && bPressed) {
			bPressed = false;
		}
		
		if (onOffB) {
			double targetPos = targetPosition * -1.0;
			lift1.set(ControlMode.MotionMagic, targetPos);
			onOffB = false;
		} else {
			lift1.set(ControlMode.PercentOutput, 0);
		}
		
		
		/* a pressed -- elevator up
		if(controller.getRawButton(1) && (!aPressed))
		{
			aPressed = true;
			onOffA = !onOffA;
		} else if((!controller.getRawButton(1)) && aPressed) {
			aPressed = false;
		}
		
		if(onOffA) {
			lift1.set(ControlMode.PercentOutput, 1);
		} else {
			lift1.set(ControlMode.PercentOutput, 0);
		}
		
		
		// b pressed -- elevator down
		if(controller.getRawButton(2) && (!bPressed))
		{
			bPressed = true;
			onOffB = !onOffB;
		} else if((!controller.getRawButton(2)) && bPressed) {
			bPressed = false;
		}
				
		if(onOffB) {
			lift1.set(ControlMode.PercentOutput, -1);
		} else {
			lift1.set(ControlMode.PercentOutput, 0);
		}*/
	}


	@Override
	public void testPeriodic() {
	}
}
