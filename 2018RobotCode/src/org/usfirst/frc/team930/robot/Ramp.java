package org.usfirst.frc.team930.robot;

import org.usfirst.frc.team930.robot.TeleopHandler.States;

import edu.wpi.first.wpilibj.Solenoid;

public class Ramp {

	static States state;
	private static Solenoid rampL = new Solenoid(0);
	private static Solenoid rampR = new Solenoid(1);
	private static Solenoid raiseR = new Solenoid(2);
	private static Solenoid raiseL = new Solenoid(3);
	
	public static void init(){
		rampL.set(false);
		rampR.set(false);
		raiseR.set(false);
		raiseL.set(false);
	}
	
	public static void run(Enum s){
		
		state = (States) s;
		
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

	private static void leftRampUP() {
		raiseL.set(true);
		
	}

	private static void rightRampUp() {
		raiseR.set(true);
		
	}

	private static void leftRampDown() {
		rampL.set(true);
		
	}

	private static void rightRampDown() {
		rampR.set(true);
		
	}

	
		
	}
	

