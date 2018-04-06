package org.usfirst.frc.team930.robot;

import org.usfirst.frc.team930.robot.TeleopHandler.RampStates;


import edu.wpi.first.wpilibj.Solenoid;

public class Ramp {

	static RampStates state;
	private static Solenoid rampL = new Solenoid(Constants.rampLSolenoidID);
	private static Solenoid rampR = new Solenoid(Constants.rampRSolenoidID);
	private static Solenoid raiseL = new Solenoid(Constants.raiseLSolenoidID);
	private static Solenoid raiseR = new Solenoid(Constants.raiseRSolenoidID);
	
	public static void init(){
		rampL.set(false);
		rampR.set(false);
		raiseR.set(false);
		raiseL.set(false);
	}
	
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

	public static void leftRampUP() {
		raiseL.set(true);
		
	}

	public static void rightRampUp() {
		raiseR.set(true);
		
	}

	public static void leftRampDown() {
		rampL.set(true);
		
	}

	public static void rightRampDown() {
		rampR.set(true);
		
	}

	public static void resetLeftRelease() {
		rampL.set(false);
	}
	
	public static void resetRightRelease() {
		rampR.set(false);
	}
	
	public static void resetLeftLift() {
		raiseL.set(false);
	}
	
	public static void resetRightLift() {
		raiseR.set(false);
	}
		
}
	

