package org.usfirst.frc.team930.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.Joystick;

import com.ctre.phoenix.ErrorCode;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.StatusFrameEnhanced;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

public class Robot extends TimedRobot {
	
	boolean aPressed, onOffA, test1;
	double targetPos = 0.0;
	ErrorCode test2;
	
	TalonSRX motor1 = new TalonSRX(6);
	Joystick controller = new Joystick(0);
	
	@Override
	public void robotInit() {
		
		aPressed = false;
		onOffA = false;
		
		test2 = motor1.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 10);
		motor1.setSensorPhase(false);
		motor1.setInverted(false);
		
		test2 = motor1.setStatusFramePeriod(StatusFrameEnhanced.Status_13_Base_PIDF0, 10, 10);
		test2 = motor1.setStatusFramePeriod(StatusFrameEnhanced.Status_10_MotionMagic, 10, 10);
		
		motor1.configNominalOutputForward(0, 10);
		motor1.configNominalOutputReverse(0, 10);
		motor1.configPeakOutputForward(1, 10);
		motor1.configPeakOutputReverse(-1, 10);
		
		test1 = SmartDashboard.putNumber("P Value", 0.2);
		test1 = SmartDashboard.putNumber("I Value", 0.0);
		test1 = SmartDashboard.putNumber("D Value", 0.0);
		test1 = SmartDashboard.putNumber("F Value", 0.2341);
		test1 = SmartDashboard.putNumber("Cruise Velocity", 480);
		test1 = SmartDashboard.putNumber("Acceleration", 480);
		test1 = SmartDashboard.putNumber("Target Position", 0.0);
		test1 = SmartDashboard.putBoolean("Update Values", false);
		test1 = SmartDashboard.putNumber("Position", motor1.getSelectedSensorPosition(0));
		test1 = SmartDashboard.putNumber("Motor Output", motor1.getMotorOutputPercent());
		test1 = SmartDashboard.putNumber("Closed Loop Error",0);
		test1 = SmartDashboard.putNumber("Velocity", motor1.getSelectedSensorVelocity(0));
		
		motor1.selectProfileSlot(0, 0);
		// Initialize PIDF values, velocity, and acceleration
		test2 = motor1.config_kF(0, 0.2341, 10);
		test2 = motor1.config_kP(0, 0.2, 10);
		test2 = motor1.config_kI(0, 0, 10);
		test2 = motor1.config_kD(0, 0, 10);
		test2 = motor1.configMotionCruiseVelocity(480, 10);	//(int sensorUnitsPer100ms, int timeoutMs)
		test2 = motor1.configMotionAcceleration(480, 10);		//(int sensorUnitsPer100msPerSec, int timeoutMs)
		test2 = motor1.setSelectedSensorPosition(0, 0, 10);
	}

	
	@Override
	public void autonomousInit() {		
		
	}

	
	@Override
	public void autonomousPeriodic() {

	}

	
	@Override
	public void teleopPeriodic() {
		
		if(SmartDashboard.getBoolean("Update Values", false)) {
			test2 = motor1.config_kF(0, SmartDashboard.getNumber("F Value", 0.2341), 10);
			test2 = motor1.config_kP(0, SmartDashboard.getNumber("P Value", 0.2), 10);
			test2 = motor1.config_kI(0, SmartDashboard.getNumber("I Value", 0.0), 10);
			test2 = motor1.config_kD(0, SmartDashboard.getNumber("D Value", 0.0), 10);
			test2 = motor1.configMotionCruiseVelocity((int) SmartDashboard.getNumber("Cruise Velocity", 480), 10);
			test2 = motor1.configMotionAcceleration((int)SmartDashboard.getNumber("Acceleration", 480), 10);
			targetPos = SmartDashboard.getNumber("Target Position", 0.0);
			System.out.println("Values Updated");
			test1 = SmartDashboard.putBoolean("Update Values", false);
		}
		
		test1 = SmartDashboard.putNumber("Position", motor1.getSelectedSensorPosition(0));
		test1 = SmartDashboard.putNumber("Motor Output", motor1.getMotorOutputPercent());
		test1 = SmartDashboard.putNumber("Velocity", motor1.getSelectedSensorVelocity(0));
		
		if(controller.getRawButton(1) && (!aPressed))
		{
			aPressed = true;
			onOffA = !onOffA;
			System.out.println("\nMotion Magic:\n");
		} else if((!controller.getRawButton(1)) && aPressed) {
			aPressed = false;
			System.out.println("\nPercent Output:\n");
		}
		/*
		if (onOffA) {
			motor1.set(ControlMode.MotionMagic, targetPos);
			test1 = SmartDashboard.putNumber("Closed Loop Error",motor1.getClosedLoopError(0));
			onOffA = false;
		} else {
			if (controller.getRawAxis(1) > 0.2 || controller.getRawAxis(1) < -0.2) {
				motor1.set(ControlMode.PercentOutput, (controller.getRawAxis(1) / -3.0));
			} else {
				motor1.set(ControlMode.PercentOutput, 0);
			}
		}
		*/
		double leftYstick = -1.0 * controller.getRawAxis(1);
		
		if (controller.getRawButton(1)) {
			double targetPos = leftYstick * 4096 * 10.0;
			motor1.set(ControlMode.MotionMagic, targetPos);
		} else {
			motor1.set(ControlMode.PercentOutput, 0);
		}
		
		if (controller.getRawAxis(5) > 0.2 || controller.getRawAxis(5) < -0.2) {
			motor1.set(ControlMode.PercentOutput, (controller.getRawAxis(1) / -3.0));
		} else {
			motor1.set(ControlMode.PercentOutput, 0);
		}
	}

	
	@Override
	public void testPeriodic() {
	}
}
