
//-- Preprocessor Directives --\\
package org.usfirst.frc.team930.robot;

import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import org.usfirst.frc.team930.robot.TeleopHandler.IntakeStates;

import com.ctre.phoenix.motorcontrol.ControlMode;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

//-- Main Class Header --\\
public class Intake {
	
	//-- Object Declarations --\\

	private static VictorSPX rightIntakeWheel = new VictorSPX(Constants.rightIntakeWheelVictorID);	//Victor of right in take wheel
	private static VictorSPX leftIntakeWheel = new VictorSPX(Constants.leftIntakeWheelVictorID);	//Victor of left in take wheel
	private static Solenoid lifter = new Solenoid(Constants.lifterForwardSolenoidID);
	private static Solenoid gripper = new Solenoid(Constants.gripperSolenoidID);

	// -- Variable Declarations --\\

	private static int PDPcounter;		//Integer used to count up loops
	private static IntakeStates stateEnum;	//States for saving states of in take
	
	//-- Function Declarations and Implementations --\\
	
	private static void updatePDPcounter() {	//updates the pdp counter
		if (Utilities.getPDPCurrent() > Constants.currentThreshhold) { 						
			PDPcounter++; 																
		} else { 																	
			PDPcounter = 0; 															
		}
	}
	
	//------------------------------------------------------------------------------------------- 
	
	private static boolean returnCubeInside() {		//check to see if a cube is inside or not
		boolean cubeInside = false;
		if (PDPcounter >= Constants.PDPcounterLimit) {
			cubeInside = true;
		}
		return cubeInside;
	}
	
	//------------------------------------------------------------------------------------------- 

	private static void inTaking() {		//method used for when the wheels in take
		if (returnCubeInside()) { 																									
			rightIntakeWheel.set(ControlMode.PercentOutput, 0);	// Stops motors
			leftIntakeWheel.set(ControlMode.PercentOutput, 0);
			setIntakeGrip(true);
			TeleopHandler.setRumble(2,0.5);
		} else {
			setIntakeLifter(false);
			rightIntakeWheel.set(ControlMode.PercentOutput, -Constants.intakeMotorSpeed);	// Turn on motors // Positive
			leftIntakeWheel.set(ControlMode.PercentOutput, Constants.intakeMotorSpeed);						  // Negative
			updatePDPcounter();
			setIntakeGrip(false);
		}
	}
	
	//------------------------------------------------------------------------------------------- 
	
	private static void inTakeDone() {		//method runs when no inputs are done
		setIntakeLifter(true);
		rightIntakeWheel.set(ControlMode.PercentOutput, 0);	// Stop motors
		leftIntakeWheel.set(ControlMode.PercentOutput, 0);
		PDPcounter = 0;
		setIntakeGrip(true);
		TeleopHandler.setRumble(2, 0);
	}
	
	//------------------------------------------------------------------------------------------- 
	
	private static void outTaking() {		//method runs when out taking cube
		rightIntakeWheel.set(ControlMode.PercentOutput, Constants.intakeMotorSpeed); // Turn right motor // Negative
		leftIntakeWheel.set(ControlMode.PercentOutput, -Constants.intakeMotorSpeed); // Turn left motor // Positive
		PDPcounter = 0; // Reset counter.
		setIntakeGrip(true);
	}
	
	private static void slowOutTaking() {		//method runs when out taking cube
		setIntakeGrip(false);
		rightIntakeWheel.set(ControlMode.PercentOutput, Constants.slowIntakeMotorSpeed); // Turn right motor // Negative
		leftIntakeWheel.set(ControlMode.PercentOutput, -Constants.slowIntakeMotorSpeed); // Turn left motor // Positive
		PDPcounter = 0; // Reset counter.
		setIntakeGrip(true);
	}
	
	//------------------------------------------------------------------------------------------- 
	
	public static void setIntakeGrip(boolean grip) {	//method to set the compressors
		gripper.set(grip);
	}
	
	//------------------------------------------------------------------------------------------- 

	private static void setIntakeLifter(boolean stage) {	//method used to set the intake's lifer mechanism
		lifter.set(stage);
	}

	//-- Initializing Variables and Objects --\\

	public static void init() {	//runs in Robot.java, for initializing
		//-- In take Variable Initializations --\\

		PDPcounter = 0;
		setIntakeGrip(false);
		setIntakeLifter(false);
	}
	
	//-- Main Loop (called in Robot.java) --\\

	public static void run(Enum state) {
		stateEnum = (IntakeStates) state;	//states used to record the state of the robot
		
		//-- Debug Messages --\\

		SmartDashboard.putNumber("PDP Intake Port Reading", Utilities.getPDPCurrent());
		SmartDashboard.putData("PDP Intake Port Graph", Utilities.pdp);
		
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
			case SLOW_OUTTAKING:
				slowOutTaking();
				break;
			case LIFTER_UP:
				setIntakeLifter(true);
				break;
			case LIFTER_DOWN:
				setIntakeLifter(false);
				break;
			default:			//If all fail, do this
				break;
		}
	}
}
