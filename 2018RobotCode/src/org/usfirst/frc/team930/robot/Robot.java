/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team930.robot;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;

/*
 * Controls autonomous, teleop, and initilizes robot settings
 */
public class Robot extends IterativeRobot {

	/*
	 *Sets up robot functions when robot is turned on
	 */
	@Override
	public void robotInit() {
		
		Drive.init();
		Intake.init();
		Elevator.init();
		Ramp.init();
		AutoHandler.robotInit();
		TeleopHandler.init();
		//LEDHandler.init();
		
		LiveWindow.disableAllTelemetry();
		
	}

	/*
	 *Sets up robot settings for autonomous
	 */
	@Override
	public void autonomousInit() {
		
		System.out.println("Init Start");
		System.out.println("Gyro: " + Drive.gyro.isConnected());
		AutoHandler.autoInit();
		System.out.println("Init End");
		
	}
	
	/*
	 * Runs the autonomous code
	 */
	@Override
	public void autonomousPeriodic() {
		//LEDHandler.autoRun();
		AutoHandler.run();
		
	}
	
	/*
	 * Readies robot for teleop
	 */
	public void teleopInit() {
		
		TeleopHandler.init();
		Drive.changeSensorPhase(false, true);
		Drive.invertMotorsForwards();
		
	}

	/*
	 * Runs driver control functions 
	 */
	@Override
	public void teleopPeriodic() {
		
		TeleopHandler.run();
		
	}
	
	/*
	 * Sets settings for when robot is disabled
	 */
	public void disabledInit() {
		AutoHandler.mpStartRScaleR.disabled();
	}
	/*
	 * Continuously sets disabled settings
	 */
	@Override
	public void disabledPeriodic() {
		
		AutoHandler.disabled();
		
	}
	
	@Override
	public void testPeriodic() {
		
	}
}
