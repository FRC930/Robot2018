/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team930.robot;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Robot extends TimedRobot {
	
	AutoRoutine auto;
	SendableChooser<Enum> autoChooser = new SendableChooser<Enum>();
	
	enum Routines {
		
		 ROUTINE1,
		 ROUTINE2
		 
	}

	@Override
	public void robotInit() {
		
		//autoChooser.addObject("Routine 1", new Enum(Routines.ROUTINE1));
        //autoChooser.addObject("Routine 2", new Enum(Routines.ROUTINE2));
        SmartDashboard.putData("Auto Routines", autoChooser);
        
	}

	@Override
	public void autonomousInit() {
		
		Enum routine = autoChooser.getSelected();
		String variation = DriverStation.getInstance().getGameSpecificMessage();
		//auto = new AutoRoutine(routine, variation);
		
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
