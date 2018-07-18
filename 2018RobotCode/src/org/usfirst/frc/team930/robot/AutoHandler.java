package org.usfirst.frc.team930.robot;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import motionProfile.MPStartLScaleL;
import motionProfile.MPStartLScaleR;
import motionProfile.MPStartRScaleL;
import motionProfile.MPStartRScaleR;
import motionProfile.MPStartLSwitchL;
import motionProfile.MPStartMSwitchL;
import motionProfile.MPStartMSwitchR;
import motionProfile.MPStartRSwitchR;

/*
 * Choosing specific autonomous routine to run based on data from Shuffleboard and FMS
 */
public class AutoHandler {
	
	private static String variation;
	private static double delay;
	private static StartPositions posEnum;
	private static Goal goalEnum;
	
	public static SendableChooser<StartPositions> posChooser = new SendableChooser<StartPositions>();
	public static SendableChooser<Goal> goalChooser = new SendableChooser<Goal>();
	
	public static Routine auto;
	
	public static MPStartLSwitchL mpStartLSwitchL;
	public static MPStartMSwitchL mpStartMSwitchL;
	public static MPStartMSwitchR mpStartMSwitchR;
	public static MPStartRSwitchR mpStartRSwitchR;
	public static MPStartLScaleL mpStartLScaleL;
	public static MPStartLScaleR mpStartLScaleR;
	public static MPStartRScaleL mpStartRScaleL;
	public static MPStartRScaleR mpStartRScaleR;
	
	public static GyroTurn myAutoGT;
	
	// Start position choices
	enum StartPositions {
		
		 RIGHT,
		 MIDDLE,
		 LEFT,
		 NOTHING
		 
	}
	
	// Auto goal choices
	enum Goal {
		
		LINE,
		LINE_SCORE,
		TIMED_SWITCH,
		TIMED_SCALE,
		DOUBLE_SCALE,
		DOUBLE_SWITCH,
		SCALE_SWITCH,
		SAME_SIDE
		
	}
	
	/*
	 * Sending auto chooser data to shuffleboard and initializing auto routines
	 */
	public static void robotInit() {
		
		posChooser.addObject("Left", StartPositions.LEFT);
		posChooser.addObject("Middle", StartPositions.MIDDLE);
        posChooser.addObject("Right", StartPositions.RIGHT);
        posChooser.addObject("Do Nothing", StartPositions.NOTHING);
        SmartDashboard.putData("Positions", posChooser);
        
        goalChooser.addObject("Double Scale", Goal.DOUBLE_SCALE);
        goalChooser.addObject("Double Switch", Goal.DOUBLE_SWITCH);
        goalChooser.addObject("Scale and Switch", Goal.SCALE_SWITCH);
        goalChooser.addObject("Same Side", Goal.SAME_SIDE);
        goalChooser.addObject("Just Line", Goal.LINE);
        goalChooser.addObject("Line & Score", Goal.LINE_SCORE);
        goalChooser.addObject("Timed Switch", Goal.TIMED_SWITCH);
        goalChooser.addObject("Timed Scale", Goal.TIMED_SCALE);
        SmartDashboard.putData("Goals", goalChooser);
        
        SmartDashboard.putNumber("Time Delay", 0);
        
        mpStartLSwitchL = new MPStartLSwitchL();
        mpStartMSwitchL = new MPStartMSwitchL();
        mpStartMSwitchR = new MPStartMSwitchR();
        mpStartRSwitchR = new MPStartRSwitchR();
        mpStartLScaleL = new MPStartLScaleL();
        mpStartLScaleR = new MPStartLScaleR();
        mpStartRScaleL = new MPStartRScaleL();
        mpStartRScaleR = new MPStartRScaleR();
        myAutoGT = new GyroTurn();
		
	}
	
	/*
	 * Gets auto chooser data and selects auto routine to run
	 */
	public static void autoInit() {
		
		Drive.resetSensorCheck();
		
		Utilities.setCompressor(false);
		Intake.setIntakeGrip(true);
		
		// Getting start position and goal from Shuffleboard chooser
		Enum startPositions = (Enum) posChooser.getSelected();
		Enum goal = (Enum) goalChooser.getSelected();
		variation = DriverStation.getInstance().getGameSpecificMessage();
		
		posEnum = (StartPositions) startPositions;
		goalEnum = (Goal) goal;
	
		// Setting time delay before auto routine
		delay = SmartDashboard.getNumber("Time Delay", 0);
		
		/*
		 * Choosing autonomous routine based on start position, goal, and field variation
		 */
		switch (posEnum) {

		case NOTHING:
			auto = new NothingAuto(variation, delay);
			break;
			
		case LEFT:
			switch (goalEnum) {

			case DOUBLE_SCALE:
				switch (variation) {

				case "LLL":
				case "RLR":
					System.out.println("LEFT DOUBLE SCALE");				// Starting in left position doing left scale auto
					auto = new StartLDoubleScaleL(variation, delay);
					break;
				case "LRL":
				case "RRR":
					auto = new StartLDoubleScaleR(variation, delay);		// Starting in left position doing right scale auto
					break;
				
				}
				break;
			case SCALE_SWITCH:
				switch (variation) {

				case "LLL":
				case "LRL":
					auto = new StartLSwitchLScaleR(variation, delay);		// Starting in left position doing left switch auto
					break;
				case "RLR":
					auto = new StartLScaleLSwitchR(variation, delay);		// Starting in left position doing left scale auto
					break;
				case "RRR":
					auto = new StartLScaleRSwitchR(variation, delay);		// Starting in left position doing right scale auto
					break;

				}
				break;
			case SAME_SIDE:
				switch (variation) {

				case "LLL":
					auto = new StartLScaleLSwitchL(variation, delay);		// Starting in left position doing left scale auto
					break;
				case "RLR":
					auto = new StartLScaleLSwitchR(variation, delay);		// Starting in left position doing left scale auto
					break;
				case "LRL":
					auto = new StartLSwitchLScaleR(variation, delay);		// Starting in left position doing left switch auto
					break;
				case "RRR":
					auto = new Line(variation, delay);						// Starting in left position doing drive forward auto
					break;

				}
				break;
			case DOUBLE_SWITCH:
				switch (variation) {

				case "LRL":
				case "LLL":
					auto = new StartLSwitchLScaleR(variation, delay);		// Starting in left position doing left switch auto
					break;
				case "RLR":
				case "RRR":
					auto = new Line(variation, delay);						// Starting in left position doing drive forward auto
					break;

				}
				break;
			case LINE:
					auto = new Line(variation, delay);
				break;
			case LINE_SCORE:
				switch(variation){
					case "LRL":
					case "LLL":
						auto = new LineScore(variation, delay);
						break;
					case "RLR":
					case "RRR":
						auto = new Line(variation, delay);
						break;
				}
				break;
			case TIMED_SCALE:
				switch(variation){
					case "RLR":
					case "LLL":
						auto = new TimedStartLScaleL(variation, delay);
						break;
					case "LRL":
					case "RRR":
						auto = new Line(variation, delay);
						break;
				}
				break;
			default:
				auto = new LineScore(variation, delay);
				break;
			}
			break;
			
		case MIDDLE:
			switch (goalEnum) {

			case DOUBLE_SWITCH:
				switch (variation) {

				case "LLL":
				case "LRL":
					auto = new MiddleLeftSwitch(variation, delay);
					break;
				case "RLR":
				case "RRR":
					auto = new MiddleRightSwitch(variation, delay);
					break;

				}
				break;
			case LINE:
				auto = new Line(variation, delay);
				break;
			case LINE_SCORE:
				auto = new LineScore(variation,delay);
				break;
			case TIMED_SWITCH:
				switch (variation) {

				case "LRL":
				case "LLL":
					auto = new TimedMiddleLeft(variation, delay);
					break;
				case "RLR":
				case "RRR":
					auto = new TimedMiddleRight(variation, delay);
					break;
				
				}
				break;
			default:
				auto = new LineScore(variation, delay);
				break;
			}
			
			break;
		case RIGHT:
			switch (goalEnum) {

			case DOUBLE_SCALE:
				switch (variation) {

				case "LLL":
				case "RLR":
					auto = new StartRDoubleScaleL(variation, delay);
					break;
				case "LRL":
				case "RRR":
					auto = new StartRDoubleScaleR(variation, delay);
					break;
				
				}
				break;
			case SCALE_SWITCH:
				switch (variation) {

				case "LLL":
					auto = new StartRScaleLSwitchL(variation, delay);
					break;
				case "LRL":
					auto = new StartRScaleRSwitchL(variation, delay);
					break;
				case "RRR":
				case "RLR":
					auto = new StartRSwitchRScaleL(variation, delay);
					break;
			
				}
				break;
			case SAME_SIDE:
				switch (variation) {

				case "LLL":
					auto = new Line(variation, delay);
					break;
				case "RLR":
					auto = new StartRSwitchRScaleL(variation, delay);
					break;
				case "LRL":
					auto = new StartRScaleRSwitchL(variation, delay);
					break;
				case "RRR":
					auto = new StartRScaleRSwitchR(variation, delay);
					break;

				}
				break;
			case DOUBLE_SWITCH:
				switch (variation) {

				case "LRL":
				case "LLL":
					auto = new Line(variation, delay);
					break;
				case "RLR":
				case "RRR":
					auto = new StartRSwitchRScaleL(variation, delay);
					break;

				}
				break;
			case LINE:
				auto = new Line(variation, delay);
				break;
			case LINE_SCORE:
				switch(variation){
					case "LRL":
					case "LLL":
						auto = new Line(variation, delay);
						break;
					case "RLR":
					case "RRR":
						auto = new LineScore(variation, delay);
						break;
				}
				break;
			case TIMED_SCALE:
				switch(variation){
					case "RLR":
					case "LLL":
						auto = new Line(variation, delay);
						break;
					case "LRL":
					case "RRR":
						auto = new TimedStartRScaleR(variation, delay);
						break;
				}
				break;
			default:
				auto = new LineScore(variation, delay);
				break;
			}
			break;
		}
		
	}
	
	public static void run() {
		////////LEDHandler.autoRun();
		if((goalEnum == Goal.LINE) || (goalEnum == Goal.LINE_SCORE) || (goalEnum == Goal.TIMED_SCALE) || (goalEnum == Goal.TIMED_SWITCH)) {	
			auto.run();
		}
		else {
			if(!Drive.checkSensors()) {
				auto.run();
			}
			else {
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
			
				auto = new Line(variation, delay);
				auto.run();
			}
		}
		
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
		
		mpStartLScaleL.disabled();
		mpStartLSwitchL.disabled();
		mpStartRSwitchR.disabled();
		mpStartRScaleR.disabled();
		
	}

}
