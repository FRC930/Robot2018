package org.usfirst.frc.team930.robot;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class AutoHandler {
	
	public static SendableChooser<Routines> autoChooser = new SendableChooser<Routines>();
	
	//public static AutoRoutine auto;
	
	enum Routines {
		
		 ROUTINE1,
		 ROUTINE2
		 
	}
	
	public static void robotInit() {
		
		autoChooser.addObject("Routine 1", Routines.ROUTINE1);
        autoChooser.addObject("Routine 2", Routines.ROUTINE2);
        SmartDashboard.putData("Auto Routines", autoChooser);
		
	}
	
	public static void autoInit() {
		
		Enum routine = (Enum) autoChooser.getSelected();
		String variation = "LRL";//DriverStation.getInstance().getGameSpecificMessage();
		
		//auto = new AutoRoutine(routine, variation);
		
	}
	
	public static void run() {
		
		//auto.run();
		
	}

}
