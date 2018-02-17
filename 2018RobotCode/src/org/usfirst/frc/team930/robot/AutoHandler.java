package org.usfirst.frc.team930.robot;

import org.usfirst.frc.team930.robot.AutoHandler.Goal;
import org.usfirst.frc.team930.robot.AutoHandler.StartPositions;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Waypoint;

public class AutoHandler {
	
	public static SendableChooser<StartPositions> posChooser = new SendableChooser<StartPositions>();
	public static SendableChooser<Goal> goalChooser = new SendableChooser<Goal>();
	
	static Waypoint[] points = new Waypoint[] {
			new Waypoint(0, 0, Pathfinder.d2r(0)),      // Waypoint @ x=-4, y=-1, exit angle=-45 degrees
		    new Waypoint(0, 0, Pathfinder.d2r(0))
	};
	
	public static Routine auto;
	
	enum StartPositions {
		
		 RIGHT,
		 MIDDLE,
		 LEFT,
		 NOTHING
		 
	}
	
	enum Goal {
		
		ALWAYS_SWITCH,
		ALWAYS_SCALE,
		PERFERRED_SWITCH,
		PERFERRED_SCALE,
		LINE,
		LINE_SCORE
		
	}
	
	public static void robotInit() {
		
		posChooser.addObject("Left", StartPositions.LEFT);
		posChooser.addObject("Middle", StartPositions.MIDDLE);
        posChooser.addObject("Right", StartPositions.RIGHT);
        posChooser.addObject("Do Nothing", StartPositions.NOTHING);
        SmartDashboard.putData("Positions", posChooser);
        
        goalChooser.addObject("Always Switch", Goal.ALWAYS_SWITCH);
        goalChooser.addObject("Always Scale", Goal.ALWAYS_SCALE);
        goalChooser.addObject("Perferred Switch", Goal.PERFERRED_SWITCH);
        goalChooser.addObject("Perferred Scale", Goal.PERFERRED_SCALE);
        goalChooser.addObject("Just Line", Goal.LINE);
        goalChooser.addObject("Line & Score", Goal.LINE_SCORE);
        SmartDashboard.putData("Goals", goalChooser);
        
        SmartDashboard.putNumber("Time Delay", 0);
		
	}
	
	public static void autoInit() {
		
		Enum startPositions = (Enum) posChooser.getSelected();
		Enum goal = (Enum) goalChooser.getSelected();
		String variation = DriverStation.getInstance().getGameSpecificMessage();
		
		StartPositions posEnum = (StartPositions) startPositions;
		Goal goalEnum = (Goal) goal;
	
		double delay = SmartDashboard.getNumber("Time Delay", 0);
		
		switch (posEnum) {

		case NOTHING:
			auto = new NothingAuto(variation, delay);
			break;
			
		case LEFT:
			switch (goalEnum) {

			case ALWAYS_SCALE:
				switch (variation) {

				case "LRL":
				case "RRR":
					auto= new LeftRightScale(variation, delay);
					break;
				case "RLR":
				case "LLL":
					auto= new LeftLeftScale(variation, delay);
					break;
				
				}
				break;
			case ALWAYS_SWITCH:
				switch (variation) {

				case "LRL":	
				case "LLL":
					auto= new LeftLeftSwitch(variation, delay);
					break;
				case "RLR":
				case "RRR":
					auto= new LeftRightSwitch(variation, delay);
					break;

				}
				break;
			case PERFERRED_SCALE:
				switch (variation) {

				case "LRL":
					auto= new LeftLeftSwitch(variation, delay);
					break;
				case "RLR":
				case "LLL":
					auto= new LeftLeftScale(variation, delay);
					break;
				case "RRR":
					auto= new Line(variation, delay);
					break;

				}
				break;
			case PERFERRED_SWITCH:
				switch (variation) {

				case "LRL":
				case "LLL":
					auto= new LeftLeftSwitch(variation, delay);
					break;
				case "RLR":
					auto= new LeftLeftScale(variation, delay);
					break;
				case "RRR":
					auto= new Line(variation, delay);
					break;

				}
				break;
			case LINE:
				switch (variation) {

				case "LRL":
				case "LLL":
					auto= new LineScore(variation, delay);
					break;
				case "RLR":
				case "RRR":
					auto= new Line(variation, delay);
					break;

				}
				break;
			}
			break;
		case MIDDLE:
			switch (goalEnum) {

			case ALWAYS_SWITCH:
				switch (variation) {

				case "LRL":
				case "LLL":
					auto= new MiddleLeftSwitch(variation, delay);
					break;
				case "RLR":
				case "RRR":
					auto= new MiddleRightSwitch(variation, delay);
					break;

				}
				break;
			case LINE:
				auto= new Line(variation, delay);
				break;

			}
			break;
		case RIGHT:
			switch (goalEnum) {

			case ALWAYS_SCALE:
				switch (variation) {

				case "LRL":
				case "RRR":
					auto= new RightRightScale(variation, delay);
					break;
				case "RLR":
				case "LLL":
					auto= new RightLeftScale(variation, delay);
					break;
				
				}
				break;
			case ALWAYS_SWITCH:
				switch (variation) {

				case "LRL":
				case "LLL":
					auto= new RightLeftSwitch(variation, delay);
					break;
				case "RLR":
				case "RRR":
					auto= new RightRightSwitch(variation, delay);
					break;

				}
				break;
			case PERFERRED_SCALE:
				switch (variation) {

				case "LRL":
				case "RRR":
					auto= new RightRightScale(variation, delay);
					break;
				case "RLR":
					auto= new RightRightSwitch(variation, delay);
					break;
				case "LLL":
					auto= new Line(variation, delay);
					break;
			
				}
				break;
			case PERFERRED_SWITCH:
				switch (variation) {

				case "LRL":
					auto= new RightRightScale(variation, delay);
					break;
				case "RLR":
				case "RRR":
					auto= new RightRightSwitch(variation, delay);
					break;
				case "LLL":
					auto= new Line(variation, delay);
					break;
				
				}
				break;
			case LINE:
				switch (variation) {

				case "LRL":
				case "LLL":
					auto= new Line(variation, delay);
					break;
				case "RLR":
				case "RRR":
					auto= new LineScore(variation, delay);
					break;

				}
				break;
			}
			break;
		}
		
		MotionProfile.init();
		
	}
	
	public static void run() {
		
		//if(!Drive.checkSensors()){
			auto.run();
			//MotionProfile.run();
		//}
		//else
			//Drive.runAt(0, 0);
		
	}

}
