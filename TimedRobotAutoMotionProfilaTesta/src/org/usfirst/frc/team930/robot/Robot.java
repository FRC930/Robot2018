/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team930.robot;


import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Notifier;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;


/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.properties file in the
 * project.
 */
public class Robot extends TimedRobot {
	
	WPI_TalonSRX rightMain = new WPI_TalonSRX(0);  //Declarations for talons
	WPI_TalonSRX leftMain = new WPI_TalonSRX(1);  //These will be the main motor controllers
	VictorSPX rightFollow = new VictorSPX(2);     //Declarations for victors that are
	VictorSPX leftFollow = new VictorSPX(3);   //followers to the talons
	//VictorSPX rightFollow2 = new VictorSPX(4);     //Declarations for victors that are
	//VictorSPX leftFollow2 = new VictorSPX(5);   //followers to the talons
	
	DifferentialDrive robot = new DifferentialDrive(leftMain, rightMain);  //Declares the driving method control for robot
	
	Joystick stick = new Joystick(0);   //XBOX Controller
	
	AHRS gryo;
	
	blah b = new blah();
	
	Notifier note = new Notifier(b);
	
	double count = 0;

	
	@Override
	public void robotInit() {
		
		rightFollow.follow(rightMain);   //Sets the victors to follow their 
		leftFollow.follow(leftMain);   //respective talons
		//rightFollow2.follow(rightMain);   //Sets the victors to follow their 
		//leftFollow2.follow(leftMain);   //respective talons
		robot.setQuickStopThreshold(0.1);
		setPeriod(0.02);
		
		
	}

	
	@Override
	public void autonomousInit() {
		note.startPeriodic(0.01);
		
		
	}

	
	@Override
	public void autonomousPeriodic() {
		
		//System.out.println("Time - last time:   " + ((Timer.getFPGATimestamp() - count))* 1000);
		
		count = Timer.getFPGATimestamp();
		
	}

	
	@Override
	public void teleopPeriodic() {
		
		boolean check;  //Value to do the quick turn or not 
		
		double rightXStick = stick.getRawAxis(4); //Right joystick X axis
		double leftYStick = stick.getRawAxis(1); //Left joystick Y axis
		
		
		robot.setDeadband(0.1);  //Sets the deadband for the controller values
		
		if(stick.getRawAxis(1) < 0.02)
			check = true;
		else                      //Tells the robot when to do a quick turn
			check = false;
		
		//robot.curvatureDrive(leftYStick, rightXStick, false);  //sends the values to the drivetrain
		robot.arcadeDrive(leftYStick, -rightXStick);
	}

	
	@Override
	public void testPeriodic() {
	}
}
