package org.usfirst.frc.team930.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.Sendable;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Robot extends TimedRobot {
	
	TalonSRX motor1 = new TalonSRX(0);
	boolean empty;
	boolean buttons[] = new boolean[10];
	String functionNames[] = new String[10];
	String functionOutcome[] = new String[10];

	@Override
	public void robotInit() {
		
		for (int x = 0; x < 10; x++) {
			buttons[x] = false;
		}
		functionNames[0] = "Left Drive Encoder";
		functionNames[1] = "Right Drive Encoder";
		functionNames[2] = "Elevator Encoder";
		
		empty = SmartDashboard.putNumber("Talon Output", motor1.getMotorOutputPercent());
		empty = SmartDashboard.putBoolean("Update Values", false);
		empty = SmartDashboard.putBooleanArray("Buttons", buttons);
		empty = SmartDashboard.putStringArray("Functions", functionNames);
		empty = SmartDashboard.putStringArray("Function Outcome", functionOutcome);
		
		
		LiveWindow.add((Sendable)motor1);
		((Sendable) motor1).setName("Subsystem", "Talon");
	}

	@Override
	public void autonomousInit() {
	}

	@Override
	public void autonomousPeriodic() {
	}

	@Override
	public void teleopPeriodic() {
	}
	
	@Override
	public void testPeriodic() {
		//update values using SmartDashboard
		if(SmartDashboard.getBoolean("Update Values", false))
		{
			motor1.set(ControlMode.PercentOutput, SmartDashboard.getNumber("Talon Output", 0));
			empty = SmartDashboard.putBoolean("Update Values", false);
			
			System.out.println("Updated Values");
		}
		SmartDashboard.putNumber("Talon Output", motor1.getMotorOutputPercent());
	}
}
