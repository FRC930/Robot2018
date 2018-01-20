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
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
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
	
	public final int FULL_SPEED = 200;
	
	WPI_TalonSRX rightMain = new WPI_TalonSRX(0);  //Declarations for talons
	WPI_TalonSRX leftMain = new WPI_TalonSRX(1);  //These will be the main motor controllers
	VictorSPX rightFollow = new VictorSPX(2);     //Declarations for victors that are
	VictorSPX leftFollow = new VictorSPX(3);   //followers to the talons
	
	DifferentialDrive robot = new DifferentialDrive(leftMain, rightMain);  //Declares the driving method control for robot
	
	Joystick stick = new Joystick(0);   //XBOX Controller
	
	AHRS gyro;
	//PIDController pid;

	double eSum = 0,eLast, P = 1, I = 0.1, D = 0;
	double MP_Speed, MP_Turn;
	double setpoint = 90, output;
	
	@Override
	public void robotInit() {
		
		rightFollow.follow(rightMain);   //Sets the victors to follow their 
		leftFollow.follow(leftMain);   //respective talons
		robot.setQuickStopThreshold(0.1);
		
	}

	
	@Override
	public void autonomousInit() {
		
/*		pid = new PIDController(0,0,0,gyro,
					new PIDOutput(){ 
					public void pidWrite(double output) {
						robot.arcadeDrive(MP_Speed, -output/FULL_SPEED);
					}
				}
						);
		
    	gyro.reset();
    	pid.reset();
    	pid.setSetpoint(MP_Turn*FULL_SPEED);   //Dps is the setpoint
    	pid.enable();*/
		
	}

	
	@Override
	public void autonomousPeriodic() {
		 
		///PID*****************************
		   double error = MP_Turn*FULL_SPEED - gyro.getRawGyroZ();
		   eSum += error;
		   double dErr = (error - eLast);
		  
		   
		   output = P * error + I * eSum + D * dErr;
		  
		   
		   eLast = error;
		  
		   robot.arcadeDrive(MP_Speed, output/FULL_SPEED);
		///PID----Driving Autonomously******************************
	}

	
	@Override
	public void teleopPeriodic() {
		
		boolean check;  //Value to do the quick turn or not 
		
		double rightXStick = stick.getRawAxis(4); //Right joystick X axis
		double leftYStick = stick.getRawAxis(1); //Left joystick Y axis
		
		
		robot.setDeadband(0.02);  //Sets the deadband for the controller values
		
		if(stick.getRawAxis(1)< 0.02)
			check = true;
		else                      //Tells the robot when to do a quick turn
			check = false;
		
		robot.arcadeDrive(leftYStick, -rightXStick);  //sends the values to the drivetrain
		
	}

	
	@Override
	public void testPeriodic() {
	}
}
