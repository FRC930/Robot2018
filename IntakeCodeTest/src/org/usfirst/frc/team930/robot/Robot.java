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
	PowerDistributionPanel PDP = new PowerDistributionPanel();
	
	//-- Intake Variable Declarations --\\
	double currentThreshhold,	//Threshhold for the current of channel 11 (in AMPs).
	timeDelay, 					//Delay time to check the PDP AMP rate and for out take.
	intakeMotorSpeed;			//Speed of intake motors.
	boolean holdingCube;		//Check for cube.

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
		
		//-- Intake Variable Initializations --\\
		holdingCube = false;
		currentThreshhold = 20;
		timeDelay = 0.5;
		intakeMotorSpeed = 0.75;
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
		
		//-- Intake Code Block --\\
		System.out.println(PDP.getCurrent(11));
		SmartDashboard.putNumber("PDP Channel 11", PDP.getCurrent(11));
		SmartDashboard.putData("PDP Channel 11 Graph", PDP);
		
		if(controller.getRawButton(6)) {	//If the Right Shoulder Button is down.
			if (!holdingCube) {															//If we're not holding a cube.
				rightIntakeWheel.set(ControlMode.PercentOutput, -intakeMotorSpeed); 	//Turn right motor backwards.
				leftIntakeWheel.set(ControlMode.PercentOutput, intakeMotorSpeed); 		//Turn left motor forwards.
				if(PDP.getCurrent(11) > currentThreshhold) {							//If the PDP current on channel 11 is over currentThreshhold of amps.
					Timer.delay(timeDelay);												//Wait
					if(PDP.getCurrent(11) > currentThreshhold) {						//If the PDP current on channel 11 is STILL over currentThreshhold of amps.
						holdingCube = true;												//Holding cube is true.	
						rightIntakeWheel.set(ControlMode.PercentOutput, 0);				//Stop motors
						leftIntakeWheel.set(ControlMode.PercentOutput, 0);
						//rightSolenoid.set(false);										//Close pistons.
						//leftSolenoid.set(false);
					}
				}
			}
		} else {																		//If A button is not down.
			rightIntakeWheel.set(ControlMode.PercentOutput, 0);							//Stop motors
			leftIntakeWheel.set(ControlMode.PercentOutput, 0);
		}
		
		if (controller.getRawButton(5) && holdingCube) {								//If Left Shoulder Button is down and we have a cube.											
			rightIntakeWheel.set(ControlMode.PercentOutput, intakeMotorSpeed); 			//Turn right motor forwards.
			leftIntakeWheel.set(ControlMode.PercentOutput, -intakeMotorSpeed);			//Turn left motor backwards.
			//rightSolenoid.set(true);													//Open pistons.
			//leftSolenoid.set(true);
			Timer.delay(timeDelay);														//Wait for cube to leave.
			holdingCube = false;														//No longer holding cube.
		
		}
	}

	/**
	 * This function is called periodically during test mode.
	 */
	@Override
	public void testPeriodic() {

	}
}
