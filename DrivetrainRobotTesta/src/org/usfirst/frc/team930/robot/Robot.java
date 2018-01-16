/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team930.robot;


import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.Joystick;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;


/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.properties file in the
 * project.
 */
public class Robot extends TimedRobot {
	
	WPI_TalonSRX rightMain = new WPI_TalonSRX(0);
	WPI_TalonSRX leftMain = new WPI_TalonSRX(1);
	VictorSPX rightFollow = new VictorSPX(0);
	VictorSPX leftFollow = new VictorSPX(1);
	
	DifferentialDrive robot = new DifferentialDrive(rightMain, leftMain);
	
	Joystick stick = new Joystick(0);
	
	@Override
	public void robotInit() {
		rightFollow.follow(rightMain);
		leftFollow.follow(leftMain);
	}

	
	@Override
	public void autonomousInit() {
		
		
		
	}

	
	@Override
	public void autonomousPeriodic() {
	
		
		
	}

	
	@Override
	public void teleopPeriodic() {
		boolean check;
		double leftXStick = stick.getRawAxis(0);
		double leftYStick = stick.getRawAxis(1);
		
		
		robot.setDeadband(0.02);
		
		if(stick.getRawAxis(1)< 0.02)
			check = true;
		else
			check = false;
		
		robot.curvatureDrive(leftYStick, leftXStick, check);
	}

	
	@Override
	public void testPeriodic() {
	}
}
