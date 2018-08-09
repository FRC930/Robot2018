
//-- Preprocessor Directives --\\
package org.usfirst.frc.team930.robot;

import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import org.usfirst.frc.team930.robot.TeleopHandler.IntakeStates;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/*
 * Initializing intake motors and controlling intake
 */
public class Intake {
	
	// Object Declarations
	public static VictorSPX rightIntakeWheel = new VictorSPX(Constants.rightIntakeWheelVictorID);	// Victor of right intake wheel
	public static VictorSPX leftIntakeWheel = new VictorSPX(Constants.leftIntakeWheelVictorID);	// Victor of left intake wheel
	private static Solenoid lifter = new Solenoid(Constants.lifterForwardSolenoidID);
	private static Solenoid gripper = new Solenoid(Constants.gripperSolenoidID);

	// Variable Declarations
	private static int PDPcounter;		//Integer used to count up loops
	private static IntakeStates stateEnum;	//States for saving states of intake
	
	// Function Declarations and Implementations
	private static void updatePDPcounter() {	//updates the pdp counter
		if (Utilities.getPDPCurrent() > Constants.currentThreshhold) { 						
			PDPcounter++;												
		} else { 																	
			PDPcounter = 0; 															
		}
	}
	
	//------------------------------------------------------------------------------------------- 
	
	/* 
	 * Check to see if a cube is inside or not
	 */
	private static boolean returnCubeInside() {
		boolean cubeInside = false;
		if (PDPcounter >= Constants.PDPcounterLimit) {
			cubeInside = true;
		}
		return cubeInside;
	}
	
	//------------------------------------------------------------------------------------------- 

	/*
	 * Method used for when the wheels intake
	 */
	private static void inTaking() {
		
		setIntakeLifter(false);
		rightIntakeWheel.set(ControlMode.PercentOutput, -Constants.intakeMotorSpeed);	// Turn on motors // Negative
		leftIntakeWheel.set(ControlMode.PercentOutput, -Constants.intakeMotorSpeed);					  // Positive
		setIntakeGrip(false);
	}
	
	//------------------------------------------------------------------------------------------- 
	
	/*
	 * Method runs when done intaking
	 */
	private static void inTakeDone() {		
		setIntakeLifter(true);
		rightIntakeWheel.set(ControlMode.PercentOutput, -Constants.slowIntakeSpeed);	// Stop motors
		leftIntakeWheel.set(ControlMode.PercentOutput, -Constants.slowIntakeSpeed);
		PDPcounter = 0;
		setIntakeGrip(true);
		TeleopHandler.setRumble(2, 0);
	}
	
	//------------------------------------------------------------------------------------------- 
	
	/*
	 * Method runs when outtaking cube
	 */
	private static void outTaking() {		
		rightIntakeWheel.set(ControlMode.PercentOutput, Constants.intakeMotorSpeed); // Turn right motor // Positive
		leftIntakeWheel.set(ControlMode.PercentOutput, Constants.intakeMotorSpeed); // Turn left motor // Negative
		PDPcounter = 0; // Reset counter.
		setIntakeGrip(true);
	}
	
	/*
	 * Method runs when slow outtaking cube
	 */
	private static void slowOutTaking() {		
		setIntakeGrip(false);
		rightIntakeWheel.set(ControlMode.PercentOutput, Constants.slowOuttakeSpeed); // Turn right motor // Positive
		leftIntakeWheel.set(ControlMode.PercentOutput, Constants.slowOuttakeSpeed); // Turn left motor // Negative
		PDPcounter = 0; // Reset counter.
	}
	
	//------------------------------------------------------------------------------------------- 
	
	/*
	 * Method to set the intake solenoid for compression
	 */
	public static void setIntakeGrip(boolean grip) {	
		gripper.set(grip);
	}
	
	//------------------------------------------------------------------------------------------- 
	
	/*
	 * Method used to set the intake's lifter mechanism
	 */
	private static void setIntakeLifter(boolean stage) {	
		lifter.set(stage);
		
		if(stage) {
			rightIntakeWheel.set(ControlMode.PercentOutput, 0);	// Stop motors
			leftIntakeWheel.set(ControlMode.PercentOutput, 0);
		}
	}
	
	/*
	 * Stops motors
	 */
	public static void stopMotors() {
		rightIntakeWheel.set(ControlMode.PercentOutput, 0);	
		leftIntakeWheel.set(ControlMode.PercentOutput, 0);
	}

	/*
	 * Runs in Robot.java, for initializing objects and variables
	 */
	public static void init() {	
		
		// Intake Variable Initializations
		PDPcounter = 0;
		setIntakeGrip(false);
		setIntakeLifter(false);
		
		rightIntakeWheel.setNeutralMode(NeutralMode.Brake);
		leftIntakeWheel.setNeutralMode(NeutralMode.Brake);
										   //Practice
		leftIntakeWheel.setInverted(false);//true
		rightIntakeWheel.setInverted(true);//false
	}
	
	/*
	 * Main loop called in Robot.java, states used to record the state of the robot
	 */
	public static void run(Enum state) {
		stateEnum = (IntakeStates) state;	
		
		// State Checking
		switch (stateEnum) {
			case INTAKING:
				inTaking();
				break;
			case INTAKE_DONE:
				inTakeDone();
				break;
			case OUTTAKING:
				outTaking() ;
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
