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
		
		
		//used in case motor's value can't be updated to zero
		SmartDashboard.putBoolean("Emergency Stop", false);
		
		//motor explicitly added to test mode display
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
<<<<<<< HEAD
		//update values using SmartDashboard
		if(SmartDashboard.getBoolean("Update Values", false))
		{
			motor1.set(ControlMode.PercentOutput, SmartDashboard.getNumber("Talon Output", 0));
			SmartDashboard.putBoolean("Update Values", false);
			
			System.out.println("Updated Values");
		}
		SmartDashboard.putNumber("Talon Output", motor1.getMotorOutputPercent());
		
		//used to stop motor if update values doesn't work
		if(SmartDashboard.getBoolean("Emergency Stop", false))
		{
			motor1.set(ControlMode.PercentOutput, 0);
			SmartDashboard.putBoolean("Emergency Stop", false);
			
			System.out.println("Motor stopped");
		}
=======
>>>>>>> 5530a5fda8f910cea1ec7e200311f829d0ad0766
	}
	
	@Override
	public void testPeriodic() {
		System.out.println("Test mode");
		
		//update values using SmartDashboard
		if(SmartDashboard.getBoolean("Update Values", false))
		{
			motor1.set(ControlMode.PercentOutput, SmartDashboard.getNumber("Talon Output", 0));
			empty = SmartDashboard.putBoolean("Update Values", false);
			
			System.out.println("Updated Values");
		}
		SmartDashboard.putNumber("Talon Output", motor1.getMotorOutputPercent());
		
		//used to stop motor if update values doesn't work
		if(SmartDashboard.getBoolean("Emergency Stop", false))
		{
			motor1.set(ControlMode.PercentOutput, 0);
			SmartDashboard.putBoolean("Emergency Stop", false);
			
			System.out.println("Motor stopped");
		}
	}
}
