
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
			case INTAKING:
				sendData = 3;
				break;
			case OUTTAKING:
				sendData = 5;
				break;
			case ENABLED:		
				sendData = 2;
				break;
			case DISABLED:	
				sendData = 1;
				break;
			default:			
				break;
		}
		
		wire.write(Constants.arduinoAddress, sendData);	
	}
}
