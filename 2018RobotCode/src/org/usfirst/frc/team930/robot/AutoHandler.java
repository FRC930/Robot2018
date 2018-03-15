package org.usfirst.frc.team930.robot;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import motionProfile.MotionProfile10A;
import motionProfile.MotionProfile11A;
import motionProfile.MotionProfile12A;
import motionProfile.MotionProfile13A;
import motionProfile.MotionProfile14A;
import motionProfile.MotionProfile1A;
import motionProfile.MotionProfile2A;
import motionProfile.MotionProfile2B;
import motionProfile.MotionProfile2C;
import motionProfile.MotionProfile3A;
import motionProfile.MotionProfile4A;
import motionProfile.MotionProfile5A;
import motionProfile.MotionProfile6A;
import motionProfile.MotionProfile7A;
import motionProfile.MotionProfile8A;
import motionProfile.MotionProfile9A;

public class AutoHandler {
	
	public static SendableChooser<StartPositions> posChooser = new SendableChooser<StartPositions>();
	public static SendableChooser<Goal> goalChooser = new SendableChooser<Goal>();
	
	public static Routine auto;
	
	public static MotionProfile1A myMP1A;
	public static MotionProfile2A myMP2A;
	public static MotionProfile2B myMP2B;
	public static MotionProfile2C myMP2C;
	public static MotionProfile3A myMP3A;
	public static MotionProfile4A myMP4A;
	public static MotionProfile5A myMP5A;
	public static MotionProfile6A myMP6A;
	public static MotionProfile7A myMP7A;
	public static MotionProfile8A myMP8A;
	public static MotionProfile9A myMP9A;
	public static MotionProfile10A myMP10A;
	public static MotionProfile11A myMP11A;
	public static MotionProfile12A myMP12A;
	public static MotionProfile13A myMP13A;
	public static MotionProfile14A myMP14A;
	
	public static GyroTurn myAutoGT;
	
	enum StartPositions {
		
		 RIGHT,
		 MIDDLE,
		 LEFT,
		 NOTHING
		 
	}
	
	enum Goal {
		
		LINE,
		LINE_SCORE,
		DOUBLE_SCALE,
		DOUBLE_SWITCH,
		SCALE_SWITCH
		
	}
	
	public static void robotInit() {
		
		posChooser.addObject("Left", StartPositions.LEFT);
		posChooser.addObject("Middle", StartPositions.MIDDLE);
        posChooser.addObject("Right", StartPositions.RIGHT);
        posChooser.addObject("Do Nothing", StartPositions.NOTHING);
        SmartDashboard.putData("Positions", posChooser);
        
        goalChooser.addObject("Double Scale", Goal.DOUBLE_SCALE);
        goalChooser.addObject("Double Switch", Goal.DOUBLE_SWITCH);
        goalChooser.addObject("Scale and Switch", Goal.SCALE_SWITCH);
        goalChooser.addObject("Just Line", Goal.LINE);
        goalChooser.addObject("Line & Score", Goal.LINE_SCORE);
        SmartDashboard.putData("Goals", goalChooser);
        
        SmartDashboard.putNumber("Time Delay", 0);
        
        myMP1A = new MotionProfile1A();
        myMP2A = new MotionProfile2A();
        myMP2B = new MotionProfile2B();
        myMP2C = new MotionProfile2C();
        myMP3A = new MotionProfile3A();
        myMP4A = new MotionProfile4A();
        myMP5A = new MotionProfile5A();
        myMP6A = new MotionProfile6A();
        myMP7A = new MotionProfile7A();
        myMP8A = new MotionProfile8A();
        myMP9A = new MotionProfile9A();
        myMP10A = new MotionProfile10A();
        myMP11A = new MotionProfile11A();
        myMP12A = new MotionProfile12A();
        myMP13A = new MotionProfile13A();
        myMP14A = new MotionProfile14A();
        myAutoGT = new GyroTurn();
		
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

			case DOUBLE_SCALE:
				switch (variation) {

				case "LRL":
				case "RRR":
					auto= new StartLDoubleScaleR(variation, delay);
					break;
				case "RLR":
				case "LLL":
					auto= new StartLDoubleScaleL(variation, delay);
					break;
				
				}
				break;
			case SCALE_SWITCH:
				switch (variation) {

				case "LRL":
					auto= new StartLSwitchLScaleR(variation, delay);
					break;
				case "RLR":
					auto= new StartLScaleLSwitchR(variation, delay);
					break;
				case "LLL":
					auto= new StartLScaleLSwitchL(variation, delay);
					break;
				case "RRR":
					auto= new StartLScaleRSwitchR(variation, delay);
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

			case DOUBLE_SWITCH:
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

			case DOUBLE_SCALE:
				switch (variation) {

				case "LRL":
				case "RRR":
					auto= new StartRDoubleScaleR(variation, delay);
					break;
				case "RLR":
				case "LLL":
					auto= new StartRDoubleScaleL(variation, delay);
					break;
				
				}
				break;
			case SCALE_SWITCH:
				switch (variation) {

				case "LRL":
					auto= new StartRScaleRSwitchL(variation, delay);
					break;
				case "RRR":
					auto= new StartRScaleRSwitchR(variation, delay);
					break;
				case "RLR":
					auto= new StartRSwitchRScaleL(variation, delay);
					break;
				case "LLL":
					auto= new StartRScaleLSwitchL(variation, delay);
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
		
		if(StartLScaleLSwitchL.n != null)
			StartLScaleLSwitchL.n.stop();
		
		else if(StartLScaleLSwitchR.n != null)
			StartLScaleLSwitchR.n.stop();
		
		else if(StartLScaleRSwitchR.n != null)
			StartLScaleRSwitchR.n.stop();
		
		else if(StartLSwitchLScaleR.n != null)
			StartLSwitchLScaleR.n.stop();
		
		else if(MiddleLeftSwitch.n != null)
			MiddleLeftSwitch.n.stop();
		
		else if(MiddleRightSwitch.n != null)
			MiddleRightSwitch.n.stop();
		
		else if(StartRScaleRSwitchR.n != null)
			StartRScaleRSwitchR.n.stop();
		
		else if(StartRScaleRSwitchL.n != null)
			StartRScaleRSwitchL.n.stop();
		
		else if(StartRScaleLSwitchL.n != null)
			StartRScaleLSwitchL.n.stop();
		
		else if(StartRSwitchRScaleL.n != null)
			StartRSwitchRScaleL.n.stop();
		
		else if(StartLDoubleScaleL.n != null)
			StartLDoubleScaleL.n.stop();
		
		else if(StartLDoubleScaleR.n != null)
			StartLDoubleScaleR.n.stop();
		
		else if(StartRDoubleScaleR.n != null)
			StartRDoubleScaleR.n.stop();
		
		else if(StartRDoubleScaleL.n != null)
			StartRDoubleScaleL.n.stop();
		
		else if(LeftLeftSwitch.n != null)
			LeftLeftSwitch.n.stop();
		
	}

}
