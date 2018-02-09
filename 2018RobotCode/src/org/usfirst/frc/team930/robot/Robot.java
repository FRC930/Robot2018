/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team930.robot;

import edu.wpi.first.wpilibj.IterativeRobot;

public class Robot extends IterativeRobot {

	@Override
	public void robotInit() {
		
		Drive.init();
		Intake.init();
		Elevator.init();
		AutoHandler.robotInit();
		TeleopHandler.init();
		
	}

	@Override
	public void autonomousInit() {
		
		AutoHandler.autoInit();
		
	}

	@Override
	public void autonomousPeriodic() {
		
		AutoHandler.run();
		
	}

	@Override
	public void teleopPeriodic() {
		
		TeleopHandler.run();
		
	}
	
	@Override
	public void disabledPeriodic() {
		
		TeleopHandler.disabled();
		
	}

	@Override
	public void testPeriodic() {
		
	}
}
