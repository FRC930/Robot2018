package org.usfirst.frc.team930.robot;

import org.usfirst.frc.team930.robot.AutoHandler.Goal;
import org.usfirst.frc.team930.robot.AutoHandler.StartPositions;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class AutoHandler {
	
	public static SendableChooser<StartPositions> posChooser = new SendableChooser<StartPositions>();
	public static SendableChooser<Goal> goalChooser = new SendableChooser<Goal>();
	
	public static Routine auto;
	
	enum StartPositions {
		
		 RIGHT,
		 MIDDLE,
		 LEFT
		 
	}
	
	enum Goal {
		
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
        SmartDashboard.putData("Positions", posChooser);
        
        goalChooser.addObject("Always Switch", Goal.ALWAYS_SWITCH);
        goalChooser.addObject("Always Scale", Goal.ALWAYS_SCALE);
        goalChooser.addObject("Perferred Switch", Goal.PERFERRED_SWITCH);
        goalChooser.addObject("Perferred Scale", Goal.PERFERRED_SCALE);
        goalChooser.addObject("Just Line", Goal.LINE);
        SmartDashboard.putData("Goals", goalChooser);
        
        SmartDashboard.putNumber("Time Delay", 0);
		
	}
	
	public static void autoInit() {
		
		Enum startPositions = (Enum) posChooser.getSelected();
		Enum goal = (Enum) goalChooser.getSelected();
		String variation = "LRL";//DriverStation.getInstance().getGameSpecificMessage();
		
		StartPositions posEnum = (StartPositions) startPositions;
		Goal goalEnum = (Goal) goal;
		
		switch (posEnum) {

		case LEFT:
			switch (goalEnum) {

			case ALWAYS_SCALE:
				switch (variation) {

				case "LRL":
					auto = new LeftRightScale(variation);
					break;
				case "RLR":
					auto = new LeftLeftScale(variation);
					break;
				case "LLL":
					auto = new LeftLeftScale(variation);
					break;
				case "RRR":
					auto = new LeftRightScale(variation);
					break;

				}
				break;
			case ALWAYS_SWITCH:
				switch (variation) {

				case "LRL":
					auto = new LeftRightSwitch(variation);
					break;
				case "RLR":
					auto = new LeftLeftSwitch(variation);
					break;
				case "LLL":
					auto = new LeftLeftSwitch(variation);
					break;
				case "RRR":
					auto = new LeftRightSwitch(variation);
					break;

				}
				break;
			case PERFERRED_SCALE:
				switch (variation) {

				case "LRL":
					auto = new LeftLeftSwitch(variation);
					break;
				case "RLR":
					auto = new LeftLeftScale(variation);
					break;
				case "LLL":
					auto = new LeftLeftScale(variation);
					break;
				case "RRR":
					auto = new Line(variation);
					break;

				}
				break;
			case PERFERRED_SWITCH:
				switch (variation) {

				case "LRL":
					auto = new LeftLeftSwitch(variation);
					break;
				case "RLR":
					auto = new LeftLeftScale(variation);
					break;
				case "LLL":
					auto = new LeftLeftSwitch(variation);
					break;
				case "RRR":
					auto = new Line(variation);
					break;

				}
				break;

			}
		case MIDDLE:
			switch (goalEnum) {

			case ALWAYS_SWITCH:
				switch (variation) {

				case "LRL":
					auto = new MiddleLeftSwitch(variation);
					break;
				case "RLR":
					auto = new MiddleRightSwitch(variation);
					break;
				case "LLL":
					auto = new MiddleLeftSwitch(variation);
					break;
				case "RRR":
					auto = new MiddleRightSwitch(variation);
					break;

				}
				break;
			case LINE:
				auto = new Line(variation);
				break;

			}
			break;
		case RIGHT:
			switch (goalEnum) {

			case ALWAYS_SCALE:
				switch (variation) {

				case "LRL":
					auto = new RightRightScale(variation);
					break;
				case "RLR":
					auto = new RightLeftScale(variation);
					break;
				case "LLL":
					auto = new RightLeftScale(variation);
					break;
				case "RRR":
					auto = new RightRightScale(variation);
					break;

				}
				break;
			case ALWAYS_SWITCH:
				switch (variation) {

				case "LRL":
					auto = new RightRightSwitch(variation);
					break;
				case "RLR":
					auto = new RightLeftSwitch(variation);
					break;
				case "LLL":
					auto = new RightLeftSwitch(variation);
					break;
				case "RRR":
					auto = new RightRightSwitch(variation);
					break;

				}
				break;
			case PERFERRED_SCALE:
				switch (variation) {

				case "LRL":
					auto = new RightScale(variation);
					break;
				case "RLR":
					auto = new RightSwitch(variation);
					break;
				case "LLL":
					auto = new Line(variation);
					break;
				case "RRR":
					auto = new RightScale(variation);
					break;

				}
				break;
			case PERFERRED_SWITCH:
				switch (variation) {

				case "LRL":
					auto = new RightScale(variation);
					break;
				case "RLR":
					auto = new RightSwitch(variation);
					break;
				case "LLL":
					auto = new Line(variation);
					break;
				case "RRR":
					auto = new RightSwitch(variation);
					break;

				}
				break;

			}

		}
		
		MotionProfile.init();
		
	}
	
	public static void run() {
		
		auto.run();
		//MotionProfile.run(SmartDashboard.getNumber("Time Delay", 0););
		
	}

}
