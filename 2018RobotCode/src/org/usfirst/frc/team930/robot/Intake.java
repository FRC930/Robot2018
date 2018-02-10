
//-- Preprocessor Directives --\\
package org.usfirst.frc.team930.robot;

import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import org.usfirst.frc.team930.robot.TeleopHandler.IntakeStates;

import com.ctre.phoenix.motorcontrol.ControlMode;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

//-- Main Class Header --\\
public class Intake {
	
	//-- Object Declarations --\\
	private static VictorSPX rightIntakeWheel = new VictorSPX(7);	//Victor of right in take wheel
	private static VictorSPX leftIntakeWheel = new VictorSPX(8);	//Victor of left in take wheel
	private static DoubleSolenoid lifter = new DoubleSolenoid(4,5);
	private static Solenoid gripper = new Solenoid(6);
	// -- Variable Declarations --\\
	private static int PDPcounter;		//Integer used to count up loops
	private static IntakeStates stateEnum;	//States for saving states of in take
	
	//-- Function Declarations and Implementations --\\
	
	//	Function:	updatePDPcounter
	//	Purpose:	Checks the current of the intake motors If the current is above 
	//				a certain threshold, it adds one to the counter
	//	Inputs:		None
	//	Outputs:	None
	private static void updatePDPcounter() {
		if (Utilities.pdp.getCurrent(Constants.pdpIntakePort) > Constants.currentThreshhold) { 						
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
	private static boolean returnCubeInside() {
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
	private static void inTaking() {
		if (returnCubeInside()) { 																									
			rightIntakeWheel.set(ControlMode.PercentOutput, 0);	// Stops motors
			leftIntakeWheel.set(ControlMode.PercentOutput, 0);
			setIntakeGrip(true);
			TeleopHandler.setRumble(2,0.5);
		} else {
			rightIntakeWheel.set(ControlMode.PercentOutput, -Constants.intakeMotorSpeed);	// Turn on motors
			leftIntakeWheel.set(ControlMode.PercentOutput, Constants.intakeMotorSpeed);
			updatePDPcounter();
			setIntakeGrip(true);
		}
	}
	
	//------------------------------------------------------------------------
	
	//	Function:	inTakeDone
	//	Purpose:	Runs in code for when we're not in taking (no buttons down)
	//	Inputs:		None
	//	Outputs:	None
	private static void inTakeDone() {
		rightIntakeWheel.set(ControlMode.PercentOutput, 0);	// Stop motors
		leftIntakeWheel.set(ControlMode.PercentOutput, 0);
		PDPcounter = 0;
		setIntakeGrip(true);
	}
	
	//------------------------------------------------------------------------
	
	//	Function:	outTaking
	//	Purpose:	Runs in code for when we're out taking (LT down)
	//	Inputs:		None
	//	Outputs:	None
	private static void outTaking() {
		rightIntakeWheel.set(ControlMode.PercentOutput, Constants.intakeMotorSpeed); // Turn right motor
		leftIntakeWheel.set(ControlMode.PercentOutput, -Constants.intakeMotorSpeed); // Turn left motor
		PDPcounter = 0; // Reset counter.
		setIntakeGrip(true);
	}	
	
	//------------------------------------------------------------------------
	
	private static void setIntakeGrip(boolean grip){
		gripper.set(grip);
	}
	
	private static void setIntakeLifter(Value stage){
		lifter.set(stage);
	}
	//-- Initializing Variables and Objects --\\
	public static void init() {
		//-- In take Variable Initializations --\\
		PDPcounter = 0;
		Utilities.comp.setClosedLoopControl(true);	//Sets the compressor on
		setIntakeGrip(false);
		setIntakeLifter(Value.kReverse);
	}
	
	//-- Main Loop (called in Robot.java) --\\
	public static void run(Enum state) {
		stateEnum = (IntakeStates) state;	//states used to record the state of the robot
		
		//-- Debug Messages --\\
		System.out.println(Utilities.pdp.getCurrent(Constants.pdpIntakePort));
		SmartDashboard.putNumber("PDP Intake Port Reading", Utilities.pdp.getCurrent(Constants.pdpIntakePort));
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
			case LIFTER_UP:
				setIntakeLifter(Value.kReverse);
			case LIFTER_DOWN:
				setIntakeLifter(Value.kForward);
			default:			//If all fail, do this
				break;
		}
	}
}
