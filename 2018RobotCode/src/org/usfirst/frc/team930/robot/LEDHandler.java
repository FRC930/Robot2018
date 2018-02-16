
//-- Preprocessor Directives --\\
package org.usfirst.frc.team930.robot;

import org.usfirst.frc.team930.robot.TeleopHandler.IntakeStates;

//-- Main Class Header --\\
public class LEDHandler {
	
	//-- Object Declarations --\\

	// -- Variable Declarations --\\
	
	private static IntakeStates intakeStateEnum;	//States for the entire robot
	
	//-- Function Declarations and Implementations --\\

	//-- Initializing Variables and Objects --\\

	public static void init() {	//runs in Robot.java, for initializing
		//-- In take Variable Initializations --\\

		
	}
	
	// Main Loop (called in Robot.java) 

	public static void run(Enum state) {
		intakeStateEnum = (IntakeStates) state;	//states used to record the state of the robot
		
		//-- State Checking --\\
		/*
		switch (stateEnum) {
			case INTAKING:		//If the right trigger is down
				inTaking();
				break;
			case INTAKE_DONE:	//If no trigger buttons are down
				inTakeDone();
				break;
			case OUTTAKING:		//If the left trigger is down
				outTaking();
				break;
			case LIFTER_UP:
				setIntakeLifter(Value.kReverse);
			case LIFTER_DOWN:
				setIntakeLifter(Value.kForward);
			default:			//If all fail, do this
				break;
		}
		*/
	}
}
