/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team930.robot;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.properties file in the
 * project.
 */
public class Robot extends IterativeRobot {
	Joystick stick = new Joystick(0);
	
	Solenoid sole1 = new Solenoid(0);
	Solenoid sole2 = new Solenoid(1);
	Solenoid sole3 = new Solenoid(2);
	Solenoid sole4 = new Solenoid(3);
	Solenoid sole5 = new Solenoid(4);
	Solenoid sole6 = new Solenoid(5);
	Solenoid sole7 = new Solenoid(6);
	Solenoid sole8 = new Solenoid(7);

	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {
		sole1.set(false);
		sole2.set(false);
		sole3.set(false);
		sole4.set(false);
		sole5.set(false);
		sole6.set(false);
		sole7.set(false);
		sole8.set(false);
	}

	/**
	 * This autonomous (along with the chooser code above) shows how to select
	 * between different autonomous modes using the dashboard. The sendable
	 * chooser code works with the Java SmartDashboard. If you prefer the
	 * LabVIEW Dashboard, remove all of the chooser code and uncomment the
	 * getString line to get the auto name from the text box below the Gyro
	 *
	 * <p>You can add additional auto modes by adding additional comparisons to
	 * the switch structure below with additional strings. If using the
	 * SendableChooser make sure to add them to the chooser code above as well.
	 */
	@Override
	public void autonomousInit() {
		
	}

	/**
	 * This function is called periodically during autonomous.
	 */
	@Override
	public void autonomousPeriodic() {
		
	}

	/**
	 * This function is called periodically during operator control.
	 */
	@Override
	public void teleopPeriodic() {
		if(stick.getRawButton(1))
			sole1.set(true);
		else
			sole1.set(false);
		
		if(stick.getRawButton(2))
			sole2.set(true);
		else
			sole2.set(false);
		
		if(stick.getRawButton(3))
			sole3.set(true);
		else
			sole3.set(false);
		
		if(stick.getRawButton(4))
			sole4.set(true);
		else
			sole4.set(false);
		
		if(stick.getRawButton(5))
			sole5.set(true);
		else
			sole5.set(false);
		
		if(stick.getRawButton(6))
			sole6.set(true);
		else
			sole6.set(false);
		
		if(stick.getRawButton(7))
			sole7.set(true);
		else
			sole7.set(false);
		
		if(stick.getRawButton(8))
			sole8.set(true);
		else
			sole8.set(false);
	}

	/**
	 * This function is called periodically during test mode.
	 */
	@Override
	public void testPeriodic() {
	}
}
