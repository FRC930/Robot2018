/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team930.robot;

import com.ctre.CANTalon;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.properties file in the
 * project.
 */
public class Robot extends TimedRobot {

	//-- Intake Object Declarations and Instantiations--\\
	VictorSPX rightIntakeWheel = new VictorSPX(7);
	VictorSPX leftIntakeWheel = new VictorSPX(8);
	//Solenoid rightSolenoid = new Solenoid(9);
	//Solenoid leftSolenoid = new Solenoid(10); 
	Joystick controller = new Joystick(0);
	PowerDistributionPanel PDP = new PowerDistributionPanel(0);
	
	//-- Intake Variable Declarations --\\
	double currentThreshhold,	//Threshhold for the current of channel 11 (in AMPs).
	intakeMotorSpeed;			//Speed of intake motors.
	boolean holdingCube;		//Check for cube.
	int PDPcounter,				//Counter for PDP checks.
	PDPcounterLimit;			//The amount of times we need to check before we begin in take.	

	private static final String kDefaultAuto = "Default";
	private static final String kCustomAuto = "My Auto";
	private String m_autoSelected;
	private SendableChooser<String> m_chooser = new SendableChooser<>();

	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {
		m_chooser.addDefault("Default Auto", kDefaultAuto);
		m_chooser.addObject("My Auto", kCustomAuto);
		SmartDashboard.putData("Auto choices", m_chooser);
		
		//-- In take Variable Initializations --\\
		holdingCube = false;
		currentThreshhold = 25.0;
		intakeMotorSpeed = 0.75;
		PDPcounter = 0;
		PDPcounterLimit = 4;
	}

	@Override
	public void autonomousInit() {
		m_autoSelected = m_chooser.getSelected();
		// m_autoSelected = SmartDashboard.getString("Auto Selector",
		// kDefaultAuto);
		System.out.println("Auto selected: " + m_autoSelected);
	}

	/**
	 * This function is called periodically during autonomous.
	 */
	@Override
	public void autonomousPeriodic() {
		switch (m_autoSelected) {
		case kCustomAuto:
			// Put custom auto code here
			break;
		case kDefaultAuto:
		default:
			// Put default auto code here
			break;
		}
	}

	/**
	 * This function is called periodically during operator control.
	 */
	@Override
	public void teleopPeriodic() {
		
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

	/**
	 * This function is called periodically during test mode.
	 */
	@Override
	public void testPeriodic() {

	}
}
