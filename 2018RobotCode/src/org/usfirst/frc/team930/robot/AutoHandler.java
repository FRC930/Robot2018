package org.usfirst.frc.team930.robot;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class AutoHandler {
	
	public static SendableChooser<StartPositions> posChooser = new SendableChooser<StartPositions>();
	public static SendableChooser<Goal> goalChooser = new SendableChooser<Goal>();
	
	public static Routine auto;
	
	public static MotionProfile1A myMP1A;
	public static MotionProfile2A myMP2A;
	public static MotionProfile2B myMP2B;
	public static MotionProfile3A myMP3A;
	public static MotionProfile4A myMP4A;
	public static MotionProfile5A myMP5A;
	public static MotionProfile6A myMP6A;
	public static MotionProfile7A myMP7A;
	public static MotionProfile8A myMP8A;
	
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
        
        myMP1A = new MotionProfile1A();
        myMP2A = new MotionProfile2A();
        myMP2B = new MotionProfile2B();
        myMP3A = new MotionProfile3A();
        myMP4A = new MotionProfile4A();
        myMP5A = new MotionProfile5A();
        myMP6A = new MotionProfile6A();
        myMP7A = new MotionProfile7A();
        myMP8A = new MotionProfile8A();
		
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
