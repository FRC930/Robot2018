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
		SCALE_POSITION_H,
		//Ramp States
		RIGHT_RAMP_DOWN,
		LEFT_RAMP_DOWN,
		RIGHT_RAMP_UP,
		LEFT_RAMP_UP
		 
	}
	
	public static void run() {
		
		Drive.run(stick1);
		
		if(stick1.getRawAxis(Constants.rightTriggerAxis) > 0.7 || stick2.getRawAxis(Constants.rightTriggerAxis) > 0.7)																
			Intake.run(States.INTAKING, stick1, stick2);
		else if(stick1.getRawAxis(Constants.leftTriggerAxis) > 0.7 || stick2.getRawAxis(Constants.leftTriggerAxis) > 0.7)																
			Intake.run(States.OUTTAKING, stick1, stick2);
		else
			Intake.run(States.INTAKE_DONE, stick1, stick2);
		
		
		
		if(stick2.getRawAxis(Constants.leftYaxis) < -0.2)														
			Elevator.run(States.SCALE_POSITION_H);
		else if(stick2.getRawAxis(Constants.leftYaxis) > 0.2 && Elevator.lift1.getSelectedSensorPosition(0) > 0)														
			Elevator.run(States.INTAKE_POSITION);
		else if(stick2.getRawButton(Constants.A))														
			Elevator.run(States.SWITCH_POSITION);
		else if(stick2.getRawButton(Constants.B))														
			Elevator.run(States.SCALE_POSITION_M);
		else if(stick2.getRawButton(Constants.X))														
			Elevator.run(States.SCALE_POSITION_L);
		else
			Elevator.run(States.INTAKE_POSITION);
		
		/*if(button1)
			Ramp.run(States.RIGHT_RAMP_DOWN);
		if(button2)
			Ramp.run(States.LEFT_RAMP_DOWN);
		if(button3)
			Ramp.run(States.RIGHT_RAMP_UP);
		if(button4)
			Ramp.run(States.LEFT_RAMP_UP);*/
		
	}

}
