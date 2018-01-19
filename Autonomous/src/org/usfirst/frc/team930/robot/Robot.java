/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team930.robot;

import java.util.Random;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Robot extends TimedRobot {
	
	AutoRoutine auto;
	SendableChooser<Routines> autoChooser = new SendableChooser<Routines>();
	
	enum Routines {
		
		 ROUTINE1,
		 ROUTINE2
		 
	}

	@Override
	public void robotInit() {
		
		autoChooser.addObject("Routine 1", Routines.ROUTINE1);
        autoChooser.addObject("Routine 2", Routines.ROUTINE2);
        SmartDashboard.putData("Auto Routines", autoChooser);
        
	}

	@Override
	public void autonomousInit() {
		
		Enum routine = (Enum) autoChooser.getSelected();
		String variation = DriverStation.getInstance().getGameSpecificMessage();
		
		// Randomizing variations without FMS
		/*Random randVar = new Random();
		String[] variationOptions = {"LRL", "RLR", "LLL", "RRR"};
		int selectedVar = randVar.nextInt(variationOptions.length);
		variation = variationOptions[selectedVar];*/
		
		auto = new AutoRoutine(routine, variation);
		
	}

	@Override
	public void autonomousPeriodic() {
		
		auto.run();
		
	}

	@Override
	public void teleopPeriodic() {
	}

	@Override
	public void testPeriodic() {
	}
}
