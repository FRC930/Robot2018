package org.usfirst.frc.team930.robot;

import org.usfirst.frc.team930.robot.TeleopHandler.States;

import edu.wpi.first.wpilibj.Solenoid;

public class Ramp {

	States state;
	Solenoid rampL = new Solenoid(0);
	Solenoid rampR = new Solenoid(1);
	Solenoid raiseR = new Solenoid(2);
	Solenoid raiseL = new Solenoid(3);
	
	public void run(Enum s){
		
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

	private void leftRampUP() {
		raiseL.set(true);
		
	}

	private void rightRampUp() {
		raiseR.set(true);
		
	}

	private void leftRampDown() {
		rampL.set(true);
		
	}

	private void rightRampDown() {
		rampR.set(true);
		
	}

	
		
	}
	

