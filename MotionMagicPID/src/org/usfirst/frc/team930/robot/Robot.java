package org.usfirst.frc.team930.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.Joystick;

import java.util.concurrent.TimeUnit;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.StatusFrameEnhanced;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

public class Robot extends TimedRobot {
	TalonSRX _talon = new TalonSRX(6);
	Joystick _joy = new Joystick(0);
	StringBuilder _sb = new StringBuilder();
	boolean stopBool = false;
	double kF = 1.0, kP = 1.0, kI = 0.002, kD = 10.0, targetPos = 6500, returnPos = 0, softLimF = 6500, softLimR = 0;
	int velocity = 800, accel = 800;
	
	@Override
	public void robotInit() {

		/* first choose the sensor */
		_talon.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, Constants2.kPIDLoopIdx, Constants2.kTimeoutMs);
		_talon.setSensorPhase(false);
		_talon.setInverted(false);

		/* Set relevant frame periods to be at least as fast as periodic rate */
		_talon.setStatusFramePeriod(StatusFrameEnhanced.Status_13_Base_PIDF0, 10, Constants2.kTimeoutMs);
		_talon.setStatusFramePeriod(StatusFrameEnhanced.Status_10_MotionMagic, 10, Constants2.kTimeoutMs);

		/* set the peak and nominal outputs */
		_talon.configNominalOutputForward(0, Constants2.kTimeoutMs);
		_talon.configNominalOutputReverse(0, Constants2.kTimeoutMs);
		_talon.configPeakOutputForward(1, Constants2.kTimeoutMs);
		_talon.configPeakOutputReverse(-1, Constants2.kTimeoutMs);
		
		_talon.configForwardSoftLimitThreshold((int) softLimF, Constants2.kTimeoutMs);
		_talon.configReverseSoftLimitThreshold((int) softLimR, Constants2.kTimeoutMs);

		/* set closed loop gains in slot0 - see documentation */
		_talon.selectProfileSlot(Constants2.kSlotIdx, Constants2.kPIDLoopIdx);
		_talon.config_kF(0, kF, Constants2.kTimeoutMs);
		_talon.config_kP(0, kP, Constants2.kTimeoutMs);
		_talon.config_kI(0, kI, Constants2.kTimeoutMs);
		_talon.config_kD(0, kD, Constants2.kTimeoutMs);
		/* set acceleration and cruise velocity - see documentation */
		_talon.configMotionCruiseVelocity(velocity, Constants2.kTimeoutMs);
		_talon.configMotionAcceleration(accel, Constants2.kTimeoutMs);
		/* zero the sensor */
		_talon.setSelectedSensorPosition(0, Constants2.kPIDLoopIdx, Constants2.kTimeoutMs);
		
		SmartDashboard.putNumber("Forward Soft Limit", softLimF);
		SmartDashboard.putNumber("Reverse Soft Limit", softLimR);
		SmartDashboard.putNumber("P Value", kP);
		SmartDashboard.putNumber("I Value", kI);
		SmartDashboard.putNumber("D Value", kD);
		SmartDashboard.putNumber("F Value", kF);
		SmartDashboard.putNumber("Cruise Velocity", velocity);
		SmartDashboard.putNumber("Acceleration", accel);
		SmartDashboard.putNumber("Taget Position", targetPos);
		SmartDashboard.putBoolean("Update Values", false);
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
			_talon.config_kF(0, SmartDashboard.getNumber("F Value", kF), Constants2.kTimeoutMs);
			_talon.config_kP(0, SmartDashboard.getNumber("P Value", kP), Constants2.kTimeoutMs);
			_talon.config_kI(0, SmartDashboard.getNumber("I Value", kI), Constants2.kTimeoutMs);
			_talon.config_kD(0, SmartDashboard.getNumber("D Value", kD), Constants2.kTimeoutMs);
			_talon.configForwardSoftLimitThreshold((int) SmartDashboard.getNumber("Forward Soft Limit", softLimF), Constants2.kTimeoutMs);
			_talon.configReverseSoftLimitThreshold((int) SmartDashboard.getNumber("Reverse Soft Limit", softLimR), Constants2.kTimeoutMs);
			targetPos = SmartDashboard.getNumber("Target Position", targetPos);
			_talon.configMotionCruiseVelocity((int) SmartDashboard.getNumber("Cruise Velocity", velocity), Constants2.kTimeoutMs);
			_talon.configMotionAcceleration((int) SmartDashboard.getNumber("Acceleration", velocity), Constants2.kTimeoutMs);
			System.out.println("Values Updated");
			SmartDashboard.putBoolean("Update Values", false);
		} 
		
		/* calculate the percent motor output */
		double motorOutput = _talon.getMotorOutputPercent();
		/* prepare line to print */
		_sb.append("\tOut%:");
		_sb.append(motorOutput);
		_sb.append("\tVel:");
		_sb.append(_talon.getSelectedSensorVelocity(Constants2.kPIDLoopIdx));
		
		
		if(_joy.getRawAxis(1) < -0.2)
		{
			_talon.set(ControlMode.MotionMagic, targetPos);
			_sb.append("\terr:");
			_sb.append(_talon.getClosedLoopError(Constants2.kPIDLoopIdx));
			_sb.append("\ttrg:");
			stopBool = true;
		}
		else if(_joy.getRawAxis(1) > 0.2 && _talon.getSelectedSensorPosition(0) > 0)
		{
			_talon.set(ControlMode.MotionMagic, 0);
			_sb.append("\terr:");
			_sb.append(_talon.getClosedLoopError(Constants2.kPIDLoopIdx));
			_sb.append("\ttrg:");
			stopBool = true;
		}
		else
		{
			if(stopBool) {
				returnPos = _talon.getSelectedSensorPosition(0);
				if(_talon.getSelectedSensorVelocity(0) > 0) {
					returnPos += (_talon.getSelectedSensorVelocity(0) /1.5);
				} else if(_talon.getSelectedSensorVelocity(0) < 0) {
					returnPos -= (_talon.getSelectedSensorVelocity(0) * 3.0);
				}
				stopBool = false;
			}
			
			_talon.set(ControlMode.MotionMagic, returnPos);
			
			/*
			if(_joy.getRawAxis(5) > 0.2 || _joy.getRawAxis(5) < 0.2)
			{
				_talon.set(ControlMode.PercentOutput, (_joy.getRawAxis(5) * -0.5));
			} else {
				_talon.set(ControlMode.PercentOutput, 0);
			}
			*/
		}
		/* instrumentation */
		Instrum1.Process(_talon, _sb);
		try {
			TimeUnit.MILLISECONDS.sleep(10);
		} catch (Exception e) {
		}
	}
	
	@Override
	public void testPeriodic() {
	}
}
