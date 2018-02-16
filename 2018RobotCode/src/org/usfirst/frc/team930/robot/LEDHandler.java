
//-- Preprocessor Directives --\\
package org.usfirst.frc.team930.robot;

import org.usfirst.frc.team930.robot.TeleopHandler.IntakeStates;
import org.usfirst.frc.team930.robot.TeleopHandler.RobotStates;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.I2C.Port;

//-- Main Class Header --\\
public class LEDHandler {
	
	//-- Object Declarations --\\
	private static I2C wire;
	
	// -- Variable Declarations --\\
	private static int sendData;
	private static RobotStates robotStateEnum;	//States for the entire robot
	
	//-- Function Declarations and Implementations --\\

	
	//-- Initializing Variables and Objects --\\

	public static void init() {	//runs in Robot.java, for initializing
		//-- In take Variable Initializations --\\
		wire = new I2C(Port.kOnboard, Constants.arduinoAddress);
		sendData = 0;
	}
	
	// Main Loop (called in Robot.java) 

	public static void run(Enum state) {
		
		robotStateEnum = (RobotStates) state;	//states used to record the state of the robot
		
		//-- State Checking --\\
		
		switch (robotStateEnum) {
			case ENABLED:		
				
				break;
			case DISABLED:	
				
				break;
			default:			
				break;
		}
		/*
		 * if (controller.getRawButton(1)) {
			System.out.println("NO STINKY ROBOTS");
			sendData = 50;
			//registerAddress, data
		} else {
			sendData = 120;
		}
		wire.write(address, sendData);	
		 */
		
	}
}
