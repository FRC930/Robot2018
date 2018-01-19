/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team930.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
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

	VictorSPX RWheel = new VictorSPX(0);
	VictorSPX LWheel = new VictorSPX(1);

	Solenoid MainSolenoid = new Solenoid(1);

	Joystick Controller = new Joystick(0);

	PowerDistributionPanel PDP = new PowerDistributionPanel();

	boolean bPressed;
	boolean aPressed;
	boolean onOffAButton;
	boolean onOffBButton;

	double currentThreshhold;
	double timeDelay;

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

		aPressed = false;
		bPressed = false;
		onOffAButton = false;
		onOffBButton = false;

		currentThreshhold = 26.5;
		timeDelay = 1.0;
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

		// A Button for Victor
		if (Controller.getRawButton(1) && (!aPressed)) { // motor switch
			aPressed = true;
			onOffAButton = !onOffAButton;
		} else if (!Controller.getRawButton(1) && aPressed) {
			aPressed = false;
		}

		// OnOff for the Victor
		if (onOffAButton) {
			RWheel.set(ControlMode.PercentOutput, 1); // running motor
			// LWheel.set(ControlMode.PercentOutput, -1); // running motor
			if (PDP.getCurrent(11) > currentThreshhold) {
				System.out.println("check one sucessfull");
				Timer.delay(timeDelay);
				if (PDP.getCurrent(11) > currentThreshhold) {
					System.out.println("check two sucessfull");
					// MainSolenoid.set(true);
					RWheel.set(ControlMode.PercentOutput, 0);
					// LWheel.set(ControlMode.PercentOutput,0);

				}
			}
		} else {
			// MainSolenoid.set(false);
			RWheel.set(ControlMode.PercentOutput, 0);
			// LWheel.set(ControlMode.PercentOutput, 0);
		}
		/*
		 * //B Button for Piston if (Controller.getRawButton(2) && (!bPressed))
		 * { bPressed = true; onOffBButton = !onOffBButton; } else if
		 * (!Controller.getRawButton(2) && bPressed) { bPressed = false; }
		 * //OnOff for the Piston if (onOffBButton) { OneLiftyBoi.set(true); }
		 * else { OneLiftyBoi.set(false); }
		 */

	}

	/**
	 * This function is called periodically during test mode.
	 */
	@Override
	public void testPeriodic() {

	}
}
