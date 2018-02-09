
//-- Preprocessor Directives --\\
package org.usfirst.frc.team930.robot;

import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import org.usfirst.frc.team930.robot.TeleopHandler.States;
import com.ctre.phoenix.motorcontrol.ControlMode;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

//-- Main Class Header --\\
public class Intake {
	
	//-- Object Declarations --\\
	private static VictorSPX rightIntakeWheel = new VictorSPX(7);	//Victor of right in take wheel
	private static VictorSPX leftIntakeWheel = new VictorSPX(8);	//Victor of left in take wheel
	private static Joystick controller = new Joystick(0);			//Controller 1 (for rumble)
	private static Joystick controller2 = new Joystick(1);			//Controller 2 (for rumble)
	private static Compressor comp = new Compressor(0);				//Compressor object
	private static PowerDistributionPanel PDP = new PowerDistributionPanel(0);	//PDP for getting current

	// -- Variable Declarations --\\
	private static int PDPcounter;		//Integer used to count up loops
	private static States stateEnum;	//States for saving states of in take
	
	//-- Function Declarations and Implementations --\\
	
	//	Function:	updatePDPcounter
	//	Purpose:	Checks the current of channel 11. If the current is above 
	//				a certain threshold, it adds one to the counter
	//	Inputs:		None
	//	Outputs:	None
	public static void updatePDPcounter() {
		if (PDP.getCurrent(11) > Constants.currentThreshhold) { 						
			PDPcounter++; 																
		} else { 																	
			PDPcounter = 0; 															
		}
	}
	
	//------------------------------------------------------------------------------------------- 
	
	//	Function:	returnCubeInside
	//	Purpose:	Checks if there is a cube inside the in take
	//	Inputs:		None
	//	Outputs:	A boolean, returning if there is a cube inside or not
	public static boolean returnCubeInside() {
		boolean cubeInside = false;
		if (PDPcounter >= Constants.PDPcounterLimit) {
			cubeInside = true;
		}
		return cubeInside;
	}
	
	//------------------------------------------------------------------------
	
	//	Function:	inTaking
	//	Purpose:	Runs in code for when we're in taking a cube (RT is down)
	//	Inputs:		None
	//	Outputs:	None
	public static void inTaking() {
		if (returnCubeInside()) { 																									
			rightIntakeWheel.set(ControlMode.PercentOutput, 0);	// Stops motors
			leftIntakeWheel.set(ControlMode.PercentOutput, 0);
			
			controller.setRumble(GenericHID.RumbleType.kLeftRumble, 0.5); //Rumbles
			controller.setRumble(GenericHID.RumbleType.kRightRumble, 0.5);
			controller2.setRumble(GenericHID.RumbleType.kLeftRumble, 0.5); //Rumbles
			controller2.setRumble(GenericHID.RumbleType.kRightRumble, 0.5);
		} else {
			rightIntakeWheel.set(ControlMode.PercentOutput, -Constants.intakeMotorSpeed);	// Turn on motors
			leftIntakeWheel.set(ControlMode.PercentOutput, Constants.intakeMotorSpeed);
			updatePDPcounter();
		}
	}
	
	//------------------------------------------------------------------------
	
	//	Function:	inTakeDone
	//	Purpose:	Runs in code for when we're not in taking (no buttons down)
	//	Inputs:		None
	//	Outputs:	None
	public static void inTakeDone() {
		rightIntakeWheel.set(ControlMode.PercentOutput, 0);	// Stop motors
		leftIntakeWheel.set(ControlMode.PercentOutput, 0);
		PDPcounter = 0;
	}
	
	//------------------------------------------------------------------------
	
	//	Function:	outTaking
	//	Purpose:	Runs in code for when we're out taking (LT down)
	//	Inputs:		None
	//	Outputs:	None
	public static void outTaking() {
		rightIntakeWheel.set(ControlMode.PercentOutput, Constants.intakeMotorSpeed); // Turn right motor
		leftIntakeWheel.set(ControlMode.PercentOutput, -Constants.intakeMotorSpeed); // Turn left motor
		PDPcounter = 0; // Reset counter.
	}	
	
	//------------------------------------------------------------------------
	
	//-- Initializing Variables and Objects --\\
	public static void init() {
		//-- In take Variable Initializations --\\
		PDPcounter = 0;
		comp.setClosedLoopControl(true);	//Sets the compressor on
	}
	
	//-- Main Loop (called in Robot.java) --\\
	public static void run(Enum state) {
		stateEnum = (States) state;	//states used to record the state of the robot
		
		//-- Debug Messages --\\
		System.out.println(PDP.getCurrent(11));
		SmartDashboard.putNumber("PDP Channel 11", PDP.getCurrent(11));
		SmartDashboard.putData("PDP Channel 11 Graph", PDP);
		
		//-- State Checking --\\
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
			default:			//If all fail, do this
				break;
		}
	}
}
