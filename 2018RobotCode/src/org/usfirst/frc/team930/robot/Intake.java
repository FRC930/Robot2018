package org.usfirst.frc.team930.robot;

import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Intake {
	
	private static VictorSPX rightIntakeWheel = new VictorSPX(7);
	private static VictorSPX leftIntakeWheel = new VictorSPX(8);
	//private static Solenoid rightSolenoid = new Solenoid(9);
	//private static Solenoid leftSolenoid = new Solenoid(10); 
	private static Joystick controller = new Joystick(0);
	private static PowerDistributionPanel PDP = new PowerDistributionPanel(0);
	
	//-- In take Variable Declarations --\\
	private static double currentThreshhold;			//Speed of in take motors.
	private static double intakeMotorSpeed;
	private static boolean holdingCube;		//Check for cube.
	private static int PDPcounter;			//The amount of times we need to check before we begin in take.	
	private static int PDPcounterLimit;

	public static void init() {
			
		//-- In take Variable Initializations --\\
		holdingCube = false;
		currentThreshhold = 35.0;
		intakeMotorSpeed = 0.75;
		PDPcounter = 0;
		PDPcounterLimit = 15;
	}
	
	public void run() {
		
		//-- In take Code Block --\\
		System.out.println(PDP.getCurrent(11));
		SmartDashboard.putNumber("PDP Channel 11", PDP.getCurrent(11));
		SmartDashboard.putData("PDP Channel 11 Graph", PDP);
		
		if (!holdingCube) {															//If we're not holding a cube.
			if(controller.getRawAxis(3) > 0.7) {									//If the RT button is down																		
				rightIntakeWheel.set(ControlMode.PercentOutput, -intakeMotorSpeed); //Turn on motors
				leftIntakeWheel.set(ControlMode.PercentOutput, intakeMotorSpeed); 		
				if (PDP.getCurrent(11) > currentThreshhold) {						//If we're above a threshold.				
					PDPcounter++;													//PDPcounter = PDPcounter + 1;
				} else {															//Else if we're below it.
					PDPcounter = 0;													//Reset counter.
				}
			} else {																//If the RT button is up
				rightIntakeWheel.set(ControlMode.PercentOutput, 0);					//Stop motors						
				leftIntakeWheel.set(ControlMode.PercentOutput, 0);
				PDPcounter = 0;														//Reset counter.
			}
		}
		
		if (PDPcounter >= PDPcounterLimit) {										//If the counter is equal to or above the limit.
			System.out.println("Kenneth moo's");	//Checks if it passes for user.
			holdingCube = true;														//We are holding a cube.								
			rightIntakeWheel.set(ControlMode.PercentOutput, 0);						//Stops motors		
			leftIntakeWheel.set(ControlMode.PercentOutput, 0);
			//rightSolenoid.set(false);										
			//leftSolenoid.set(false);
			PDPcounter = 0;															//Reset counter
		}
		
		if (controller.getRawAxis(2) > 0.7 && holdingCube) {						//If Left Shoulder Button is down and we have a cube.											
			rightIntakeWheel.set(ControlMode.PercentOutput, intakeMotorSpeed); 		//Turn right motor forwards.
			leftIntakeWheel.set(ControlMode.PercentOutput, -intakeMotorSpeed);		//Turn left motor backwards.
			//rightSolenoid.set(true);												//Open pistons.
			//leftSolenoid.set(true);
			Timer.delay(0.4);														//Wait for cube to leave.
			holdingCube = false;													//No longer holding cube.
			PDPcounter = 0;															//Reset counter.
		}
	}
}
