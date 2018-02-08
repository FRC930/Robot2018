package org.usfirst.frc.team930.robot;

import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import org.usfirst.frc.team930.robot.TeleopHandler.States;

import com.ctre.phoenix.motorcontrol.ControlMode;
//import com.ctre.phoenix.motorcontrol.can.TalonSRX;
//import com.ctre.phoenix.motorcontrol.can.VictorSPX;

//import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
//import edu.wpi.first.wpilibj.Solenoid;
//import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Intake {

	private static VictorSPX rightIntakeWheel = new VictorSPX(7);
	private static VictorSPX leftIntakeWheel = new VictorSPX(8);
	// private static Solenoid rightSolenoid = new Solenoid(9);
	// private static Solenoid leftSolenoid = new Solenoid(10);
	private static PowerDistributionPanel PDP = new PowerDistributionPanel(0);

	// -- In take Variable Declarations --\\
	private static int PDPcounter;
	private static States stateEnum;
	// The amount of times we need to check before we begin in take.

	public static void init() {

		// -- In take Variable Initializations --\\
		PDPcounter = 0;
	}

	public static void run(Enum state) {
		
		stateEnum = (States) state;

		// -- In take Code Block --\\
		System.out.println(PDP.getCurrent(11));
		SmartDashboard.putNumber("PDP Channel 11", PDP.getCurrent(11));
		SmartDashboard.putData("PDP Channel 11 Graph", PDP);

		switch (stateEnum) {
			case INTAKING:
				inTaking();
				break;
			case INTAKE_DONE:
				inTakeDone();
				break;
			case OUTTAKING:
				outTaking();
				break;
				
			default:
				break;
		}
	}
	
	public static void updatePDPcounter() {
		if (PDP.getCurrent(11) > Constants.currentThreshhold) { 						// If we're above a threshold.
			PDPcounter++; 																// PDPcounter = PDPcounter + 1;
		} else { 																		// Else if we're below it.
			PDPcounter = 0; 															// Reset counter.
		}
	}
	
	public static boolean returnCubeInside() {
		boolean cubeInside = false;
		if (PDPcounter >= Constants.PDPcounterLimit) {
			cubeInside = true;
		}
		return cubeInside;
	}
	
	public static void inTaking() {
			
		updatePDPcounter();

		if (returnCubeInside()) { 															// If the counter is equal to or above the limit.														
			rightIntakeWheel.set(ControlMode.PercentOutput, 0);								// Stops motors
			leftIntakeWheel.set(ControlMode.PercentOutput, 0);
			// controller.setRumble(GenericHID.RumbleType.kLeftRumble, 0.5); //Rumbles
			// controller when cube is held
			// controller.setRumble(GenericHID.RumbleType.kRightRumble, 0.5);
			// rightSolenoid.set(false);
			// leftSolenoid.set(false);
		} else {
			rightIntakeWheel.set(ControlMode.PercentOutput, -Constants.intakeMotorSpeed); 		// Turn on motors
			leftIntakeWheel.set(ControlMode.PercentOutput, Constants.intakeMotorSpeed);
		}
	}
		
	public static void inTakeDone() {
		rightIntakeWheel.set(ControlMode.PercentOutput, 0);										// Stop motors
		leftIntakeWheel.set(ControlMode.PercentOutput, 0);
		PDPcounter = 0; 
	}
	
	public static void outTaking() {
		rightIntakeWheel.set(ControlMode.PercentOutput, Constants.intakeMotorSpeed); // Turn right motor
		leftIntakeWheel.set(ControlMode.PercentOutput, -Constants.intakeMotorSpeed); // Turn left motor
		// controller.setRumble(GenericHID.RumbleType.kLeftRumble, 0); //Turn off rumble
		// when cube leaves
		// controller.setRumble(GenericHID.RumbleType.kLeftRumble, 0);
		// rightSolenoid.set(true); //Open pistons.
		// leftSolenoid.set(true);
		PDPcounter = 0; // Reset counter.
	}	
}
