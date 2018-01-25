/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team930.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.Sendable;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Robot extends TimedRobot {
	TalonSRX motor1 = new TalonSRX(0);

	@Override
	public void robotInit() {
		SmartDashboard.putNumber("Talon Output", motor1.getMotorOutputPercent());
		SmartDashboard.putBoolean("Update Values", false);
		
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
		//update values using SmartDashboard
		if(SmartDashboard.getBoolean("Update Values", false))
		{
			motor1.set(ControlMode.PercentOutput, SmartDashboard.getNumber("Talon Output", 0));
			SmartDashboard.putBoolean("Update Values", false);
			
			System.out.println("Updated Values");
		}
		SmartDashboard.putNumber("Talon Output", motor1.getMotorOutputPercent());
	}

	/**
	 * This function is called periodically during test mode.
	 */
	@Override
	public void testPeriodic() {
		//update values using SmartDashboard
		if(SmartDashboard.getBoolean("Update Values", false))
		{
			motor1.set(ControlMode.PercentOutput, SmartDashboard.getNumber("Talon Output", 0));
			SmartDashboard.putBoolean("Update Values", false);
			
			System.out.println("Updated Values");
		}
		SmartDashboard.putNumber("Talon Output", motor1.getMotorOutputPercent());
	}
}
