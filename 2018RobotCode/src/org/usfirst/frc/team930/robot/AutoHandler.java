package org.usfirst.frc.team930.robot;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Waypoint;

public class AutoHandler {
	
	public static SendableChooser<StartPositions> posChooser = new SendableChooser<StartPositions>();
	public static SendableChooser<Goal> goalChooser = new SendableChooser<Goal>();
	
	static Waypoint[] leftLeftScale = new Waypoint[] {
			new Waypoint(0.7, 3.1, Pathfinder.d2r(0)),
			new Waypoint(7.1, 4.0, Pathfinder.d2r(0)),
			new Waypoint(8.0, 3.3, Pathfinder.d2r(270)),
	};
	
	static Waypoint[] leftLeftSwitch = new Waypoint[] {
			new Waypoint(0.7, 3.1, Pathfinder.d2r(0)),
			new Waypoint(3.2, 3.1, Pathfinder.d2r(0)),
			new Waypoint(4.0, 2.5, Pathfinder.d2r(270)),
	};
	
	static Waypoint[] leftLeftSwitch2 = new Waypoint[] {
			new Waypoint(0, 0, Pathfinder.d2r(0)),
			new Waypoint(3.0, -3.0, Pathfinder.d2r(90)),
			
			/*new Waypoint(4.0, 6.0, Pathfinder.d2r(0)),
			new Waypoint(4.5, 6.93, Pathfinder.d2r(60)),
			new Waypoint(5.15, 7.1, Pathfinder.d2r(90)),
			new Waypoint(5.8, 6.94, Pathfinder.d2r(120)),
			new Waypoint(6.3, 6.0, Pathfinder.d2r(180)),*/
			
			/*new Waypoint(4.0, 6.0, Pathfinder.d2r(270)),
			new Waypoint(4.5, 6.93, Pathfinder.d2r(330)),
			new Waypoint(5.15, 7.1, Pathfinder.d2r(0)),
			new Waypoint(5.8, 6.94, Pathfinder.d2r(30)),
			new Waypoint(6.3, 6.0, Pathfinder.d2r(90)),*/
	};
	
	static Waypoint[] leftRightScale = new Waypoint[] {
			new Waypoint(0, 7, Pathfinder.d2r(0)),
			new Waypoint(4.5, 7.5, Pathfinder.d2r(345)),
			new Waypoint(5.75, 5, Pathfinder.d2r(270)),
			new Waypoint(5.75, 3.15, Pathfinder.d2r(270)),
			new Waypoint(6.9, 1.75, Pathfinder.d2r(0)),
	};
	
	static Waypoint[] middleLeftSwitch = new Waypoint[] {
			new Waypoint(0.7, 3.1, Pathfinder.d2r(0)),
			new Waypoint(4.25, 3.1, Pathfinder.d2r(0)),
			new Waypoint(5.5, 2, Pathfinder.d2r(270)),
	};
	
	static Waypoint[] middleRightSwitch = new Waypoint[] {
			new Waypoint(0.7, 3.1, Pathfinder.d2r(0)),
			new Waypoint(4.25, 3.1, Pathfinder.d2r(0)),
			new Waypoint(5.5, 2, Pathfinder.d2r(270)),
	};
	
	static Waypoint[] rightRightScale = new Waypoint[] {
			new Waypoint(0.7, 3.1, Pathfinder.d2r(0)),
			new Waypoint(4.25, 3.1, Pathfinder.d2r(0)),
			new Waypoint(5.5, 2, Pathfinder.d2r(270)),
	};
	
	static Waypoint[] rightRightSwitch = new Waypoint[] {
			new Waypoint(0.7, 3.1, Pathfinder.d2r(0)),
			new Waypoint(4.25, 3.1, Pathfinder.d2r(0)),
			new Waypoint(5.5, 2, Pathfinder.d2r(270)),
	};
	
	static Waypoint[] rightLeftScale = new Waypoint[] {
			new Waypoint(0.7, 3.1, Pathfinder.d2r(0)),
			new Waypoint(4.25, 3.1, Pathfinder.d2r(0)),
			new Waypoint(5.5, 2, Pathfinder.d2r(270)),
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
        
        MotionProfile1A.init();
        MotionProfile2A.init();
        MotionProfile2B.init();
        MotionProfile3A.init();
        MotionProfile6A.init();
        MotionProfile4A.init();
		
	}
	
	public static void autoInit() {
		
		MotionProfile1A.first = Timer.getFPGATimestamp();
		
		Utilities.setCompressor(false);
		Intake.setIntakeGrip(true);
		
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
					//auto= new LeftRightSwitch(variation, delay);
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
					auto= new LineElevator(variation, delay);
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
					auto= new LineElevator(variation, delay);
					break;

				}
				break;
			case LINE:
					auto= new LineElevator(variation, delay);
				break;
			case LINE_SCORE:
				switch(variation){
					case "LRL":
					case "LLL":
						auto= new LineScore(variation, delay);
						break;
					case "RLR":
					case "RRR":
						auto= new LineElevator(variation, delay);
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
				auto= new LineElevator(variation, delay);
				break;
			case LINE_SCORE:
				auto= new LineScore(variation,delay);
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
					//auto= new RightLeftSwitch(variation, delay);
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
					auto= new LineElevator(variation, delay);
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
					auto= new LineElevator(variation, delay);
					break;
				
				}
				break;
			case LINE:
				auto= new LineElevator(variation, delay);
				break;
			case LINE_SCORE:
				switch(variation){
					case "LRL":
					case "LLL":
						auto= new LineElevator(variation, delay);
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
		
	}
	
	public static void run() {
		////////LEDHandler.autoRun();
		//if(!Drive.checkSensors()) {
			//Drive.resetSensorCheck();
			auto.run();
			//MotionProfile.run();
		//}
		//else
			//Drive.runAt(0, 0);
		
	}
	
	public static void disabled() {
		
		if(LeftLeftScale.n != null)
			LeftLeftScale.n.stop();
		
		else if(LeftLeftSwitch.n != null)
			LeftLeftSwitch.n.stop();
		
		else if(LeftRightScale.n != null)
			LeftRightScale.n.stop();
		
		else if(MiddleLeftSwitch.n != null)
			MiddleLeftSwitch.n.stop();
		
		else if(MiddleRightSwitch.n != null)
			MiddleRightSwitch.n.stop();
		
		else if(RightRightScale.n != null)
			RightRightScale.n.stop();
		
		else if(RightRightSwitch.n != null)
			RightRightSwitch.n.stop();
		
		else if(RightLeftScale.n != null)
			RightLeftScale.n.stop();
		
	}

}
