/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team930.robot;


import com.ctre.phoenix.motion.TrajectoryPoint;
import com.ctre.phoenix.motorcontrol.ControlMode;
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
	
	WPI_TalonSRX rightMain = new WPI_TalonSRX(0);  //Declarations for talons
	WPI_TalonSRX leftMain = new WPI_TalonSRX(1);  //These will be the main motor controllers
	VictorSPX rightFollow = new VictorSPX(2);     //Declarations for victors that are
	VictorSPX leftFollow = new VictorSPX(3);   //followers to the talons
	VictorSPX right2Follow = new VictorSPX(4);     //Declarations for victors that are
	VictorSPX left2Follow = new VictorSPX(5);  
	//VictorSPX rightFollow2 = new VictorSPX(4);     //Declarations for victors that are
	//VictorSPX leftFollow2 = new VictorSPX(5);   //followers to the talons
		
	Joystick stick = new Joystick(0);   //XBOX Controller
	
	TrajectoryPoint point = new TrajectoryPoint();

	
	@Override
	public void robotInit() {
		
		rightFollow.follow(rightMain);   //Sets the victors to follow their 
		leftFollow.follow(leftMain);  
		right2Follow.follow(rightMain);   //Sets the victors to follow their 
		left2Follow.follow(leftMain); //respective talons
		//rightFollow2.follow(rightMain);   //Sets the victors to follow their 
		//leftFollow2.follow(leftMain);   //respective talons
		rightMain.set(ControlMode.MotionProfile, 0);
		rightMain.configMotionProfileTrajectoryPeriod(10);

		
	}

	
	@Override
	public void autonomousInit() {
		
		
		
	}

	
	@Override
	public void autonomousPeriodic() {
for (int i = 0; i < totalCnt; ++i) {
			
		rightMain.processMotionProfileBuffer();
		leftMain.processMotionProfileBuffer();
		point.position = pointArray[i][0];
		point.velocity = pointArray[i][1];
		point.profileSlotSelect = 0; /* which set of gains would you like to use? */
}
		System.out.println("CONTROL MODE: " + rightMain.getControlMode());
		rightMain.pushMotionProfileTrajectory(point);
	}

	
	@Override
	public void teleopPeriodic() {
		

	}

	
	@Override
	public void testPeriodic() {
	}
}
