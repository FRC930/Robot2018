package org.usfirst.frc.team930.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj.Joystick;

public class TeleopHandler {
	
	private static final Joystick stick1 = new Joystick(0);
	private static final Joystick stick2 = new Joystick(1);
	
	enum States {
		
		// Intake states
		INTAKING,
		INTAKE_DONE,
		OUTTAKING,
		// Elevator states
		INTAKE_POSITION,
		SWITCH_POSITION,
		SCALE_POSITION_L,
		SCALE_POSITION_M,
		SCALE_POSITION_H
		 
	}
	
	public static void run() {
		
		Drive.run(stick1);
		
		//if(stick1.getRawAxis(3) > 0.7 || stick2.getRawAxis(3) > 0.7) {																	
			// Intake.run(States.INTAKING);
		
	}

}
