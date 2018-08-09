
package org.usfirst.frc.team930.robot;

import org.usfirst.frc.team930.robot.TeleopHandler.RobotStates;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.I2C.Port;

/*
 *  Initializing and controlling LEDs
 */
public class LEDHandler {
	
	//-- Object Declarations --\\
	private static I2C wire;
	
	// -- Variable Declarations --\\
	private static int sendData;
	private static RobotStates robotStateEnum;	//States for the entire robot
	
	//-- Initializing Variables and Objects --\\

	public static void init() {	//runs in Robot.java, for initializing
		//-- In take Variable Initializations --\\
		wire = new I2C(Port.kOnboard, Constants.arduinoAddress);
		sendData = 0;
	}
	
	// Update Elevator (called in Elevator.java)
	
	public static void updateElevator(int elevatorPosition) {
		wire.write(Constants.arduinoAddress, -elevatorPosition);
		//We send a negative number to make sure we do NOT activate other LED patterns.
		//All LED pattern IDs are > 0, so by sending a negative number, only elevator positions
		//will be sent. We will have to re-negate the number in the arduino (make it positive).
	}
	
	// Autonomous Loop (called in AutoHandler.java)
	
	public static void autoRun() {
		wire.write(Constants.arduinoAddress, 9);	//9 is the ID for autonomous
	}
	
	// Main Loop (called in TeleopHandler.java) 

	public static void run(Enum state) {
		
		robotStateEnum = (RobotStates) state;	//states used to record the state of the robot
		
		//-- State Checking --\\
		
		switch (robotStateEnum) {
			case RAMPS_DOWN:
				sendData = 7;
				break;
			case RAMPS_UP:
				sendData = 8;
				break;
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
