package org.usfirst.frc.team930.robot;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class AutoHandler {
	
	public static SendableChooser<StartPositions> posChooser = new SendableChooser<StartPositions>();
	public static SendableChooser<Goal> goalChooser = new SendableChooser<Goal>();
	
	public static AutoRoutine auto;
	
	enum StartPositions {
		
		 RIGHT,
		 MIDDLE,
		 LEFT
		 
	}
	enum Goal{
		
		ALWAYS_SWITCH,
		ALWAYS_SCALE,
		PERFERRED_SWITCH,
		PERFERRED_SCALE,
		LINE
		
	}
	
	public static void robotInit() {
		
		posChooser.addObject("Left", StartPositions.LEFT);
		posChooser.addObject("Middle", StartPositions.MIDDLE);
        posChooser.addObject("Right", StartPositions.RIGHT);
        SmartDashboard.putData("Postions", posChooser);
        
        goalChooser.addObject("Always Switch", Goal.ALWAYS_SWITCH);
        goalChooser.addObject("Always Scale", Goal.ALWAYS_SCALE);
        goalChooser.addObject("Perferred Switch", Goal.PERFERRED_SWITCH);
        goalChooser.addObject("Perferred Scale", Goal.PERFERRED_SCALE);
        goalChooser.addObject("Just Line", Goal.LINE);
        SmartDashboard.putData("Goals", goalChooser);
        
        SmartDashboard.putNumber("Time Delay", 0);
		
	}
	
	public static void autoInit() {
		
		Enum startPostions = (Enum) posChooser.getSelected();
		Enum goal = (Enum) goalChooser.getSelected();
		String variation = "LRL";//DriverStation.getInstance().getGameSpecificMessage();
		
		auto = new AutoRoutine(startPostions, goal, variation);
		
		MotionProfile.init();
		
	}
	
	public static void run() {
		
		//auto.run();
		//MotionProfile.run(SmartDashboard.getNumber("Time Delay", 0););
		
	}

}
