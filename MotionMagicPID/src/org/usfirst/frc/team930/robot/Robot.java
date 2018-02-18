package org.usfirst.frc.team930.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.Joystick;

import java.util.concurrent.TimeUnit;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.StatusFrameEnhanced;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

public class Robot extends TimedRobot {
	TalonSRX _talon = new TalonSRX(7);
	TalonSRX _talon2 = new TalonSRX(8);
	Joystick _joy = new Joystick(0);
	Compressor comp = new Compressor(0);
	StringBuilder _sb = new StringBuilder();
	double kF = 1.1, kP = 1.6, kI = 0, kD = 0, targetPos = 0, returnPos = 0, softLimF = 7500, softLimR = 0;
	int velocity = 900, accel = 900;
	boolean positionBool = false;
	double highPosition = 7500, lowPosition = 10, multiplier = -0.2;
	
	int count = 0;
	boolean aPressed = false, bPressed = false;
	// a-button 6, b-button 5
	
	@Override
	public void robotInit() {
		
		//encoder: -10 to -8420 before setting sensor phase to true
		
		_talon2.follow(_talon);
		
		//_talon.configPeakOutputForward(0.7, Constants2.kTimeoutMs);
		//_talon.configPeakOutputReverse(0.7, Constants2.kTimeoutMs);

		/* first choose the sensor */
		_talon.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, Constants2.kPIDLoopIdx, Constants2.kTimeoutMs);
		_talon.setSensorPhase(true);
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
		SmartDashboard.putNumber("High Position", highPosition);
		SmartDashboard.putNumber("Low Position", lowPosition);
		SmartDashboard.putNumber("Multiplier", multiplier);
		SmartDashboard.putNumber("P Value", kP);
		SmartDashboard.putNumber("I Value", kI);
		SmartDashboard.putNumber("D Value", kD);
		SmartDashboard.putNumber("F Value", kF);
		SmartDashboard.putNumber("Cruise Velocity", velocity);
		SmartDashboard.putNumber("Acceleration", accel);
		SmartDashboard.putNumber("Target Position", targetPos);
		SmartDashboard.putBoolean("Update Values", false);
		
		comp.setClosedLoopControl(true);
	}

	
	@Override
	public void autonomousInit() {		
		
	}

	
	@Override
	public void autonomousPeriodic() {

	}

	
	@Override
	public void teleopPeriodic() {
		// Updates Shuffleboard values
		if(SmartDashboard.getBoolean("Update Values", false)) {
			highPosition = SmartDashboard.getNumber("High Position", highPosition);
			lowPosition = SmartDashboard.getNumber("Low Position", lowPosition);
			multiplier = SmartDashboard.getNumber("Multiplier", multiplier);
			_talon.config_kF(0, SmartDashboard.getNumber("F Value", kF), Constants2.kTimeoutMs);
			_talon.config_kP(0, SmartDashboard.getNumber("P Value", kP), Constants2.kTimeoutMs);
			_talon.config_kI(0, SmartDashboard.getNumber("I Value", kI), Constants2.kTimeoutMs);
			_talon.config_kD(0, SmartDashboard.getNumber("D Value", kD), Constants2.kTimeoutMs);
			_talon.configForwardSoftLimitThreshold((int) SmartDashboard.getNumber("Forward Soft Limit", softLimF), Constants2.kTimeoutMs);
			_talon.configReverseSoftLimitThreshold((int) SmartDashboard.getNumber("Reverse Soft Limit", softLimR), Constants2.kTimeoutMs);
			targetPos = SmartDashboard.getNumber("Target Position", targetPos);
			_talon.configMotionCruiseVelocity((int) SmartDashboard.getNumber("Cruise Velocity", velocity), Constants2.kTimeoutMs);
			_talon.configMotionAcceleration((int) SmartDashboard.getNumber("Acceleration", accel), Constants2.kTimeoutMs);
			System.out.println("Values Updated");
			SmartDashboard.putBoolean("Update Values", false);
		} 
		
		// calculate the percent motor output
		double motorOutput = _talon.getMotorOutputPercent();
		// prepare line to print
		_sb.append("\tOut%:");
		_sb.append(motorOutput);
		_sb.append("\tVel:");
		_sb.append(_talon.getSelectedSensorVelocity(Constants2.kPIDLoopIdx));
		
		
		//B button -- up, A button -- down, left stick -- manual control
		if(_joy.getRawButton(2)) {
			_talon.set(ControlMode.MotionMagic, highPosition);
			_sb.append("\terr:");
			_sb.append(_talon.getClosedLoopError(Constants2.kPIDLoopIdx));
			_sb.append("\ttrg:");
			System.out.println(_talon.getMotorOutputVoltage());
		} else if(_joy.getRawButton(1)) {
			_talon.set(ControlMode.MotionMagic, lowPosition);
			_sb.append("\terr:");
			_sb.append(_talon.getClosedLoopError(Constants2.kPIDLoopIdx));
			_sb.append("\ttrg:");
			System.out.println(_talon.getMotorOutputVoltage());
		} else if(Math.abs(_joy.getRawAxis(5)) > 0.15 ){	
			_talon.set(ControlMode.PercentOutput, _joy.getRawAxis(5) * multiplier);
		}
		// instrumentation
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
