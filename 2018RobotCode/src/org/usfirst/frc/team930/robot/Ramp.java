package org.usfirst.frc.team930.robot;

import org.usfirst.frc.team930.robot.TeleopHandler.RampStates;


import edu.wpi.first.wpilibj.Solenoid;

/*
 * Initializing ramp solenoids and controlling ramp functions
 */
public class Ramp {

	static RampStates state;
	private static Solenoid rampL = new Solenoid(Constants.rampLSolenoidID);
	private static Solenoid rampR = new Solenoid(Constants.rampRSolenoidID);
	private static Solenoid raiseL = new Solenoid(Constants.raiseLSolenoidID);
	private static Solenoid raiseR = new Solenoid(Constants.raiseRSolenoidID);
	
	// Setting solenoid values
	public static void init(){
		rampL.set(false);
		rampR.set(false);
		raiseR.set(false);
		raiseL.set(false);
	}
	
	/*
	 *  Main method in Robot.java that controls the state of the ramps
	 */
	public static void run(Enum s){
		
		state = (RampStates) s;
		
		switch(state){
		
			case RIGHT_RAMP_DOWN:
				rightRampDown();
				break;
			case LEFT_RAMP_DOWN:
				leftRampDown();
				break;
			case RIGHT_RAMP_UP:
				rightRampUp();
				break;
			case LEFT_RAMP_UP:
				leftRampUP();
				break;
				
		}
		
	}
	
	// Lifts left ramp
	public static void leftRampUP() {
		raiseL.set(true);
		
	}
	
	// Lifts right ramp
	public static void rightRampUp() {
		raiseR.set(true);
		
	}
	
	// Releases left ramp
	public static void leftRampDown() {
		rampL.set(true);
		
	}
	
	// Releases right ramp
	public static void rightRampDown() {
		rampR.set(true);
		
	}
	
	// Resets left release solenoids
	public static void resetLeftRelease() {
		rampL.set(false);
	}
	
	// Resets right release solenoids
	public static void resetRightRelease() {
		rampR.set(false);
	}
	
	// Resets left lift solenoids
	public static void resetLeftLift() {
		raiseL.set(false);
	}
	
	// Resets right lift solenoids
	public static void resetRightLift() {
		raiseR.set(false);
	}
		
}
	

