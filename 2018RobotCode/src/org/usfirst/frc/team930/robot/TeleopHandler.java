package org.usfirst.frc.team930.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Solenoid;

public class TeleopHandler {
	
	private static final Joystick stick1 = new Joystick(0);
	private static final Joystick stick2 = new Joystick(1);
	private static boolean buttonCheckRB = false;
	private static boolean buttonCheckLB = false;
	private static int counter = 0;
	private static Solenoid rightSolenoid = new Solenoid(0);
	private static boolean solenoid = false;
	
	public static void init() {
		rightSolenoid.set(false);
	}
	
	public static void disabled() {
		rightSolenoid.set(solenoid);
	}
	
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
		
		if(stick1.getRawButton(1))
			solenoid = true;
		
		rightSolenoid.set(solenoid);
		
		System.out.println("Solenoid: " + rightSolenoid.get());
		
		Drive.run(stick1);
		
		if(stick2.getRawAxis(Constants.rightTriggerAxis) > 0.7)																
			Intake.run(States.INTAKING);
		else if(stick2.getRawAxis(Constants.leftTriggerAxis) > 0.7)																
			Intake.run(States.OUTTAKING);
		else
			Intake.run(States.INTAKE_DONE);
		
		
		//Elevator
		if(Math.abs(stick2.getRawAxis(Constants.rightYaxis))>0.15);
			//Elevator.run(stick2.getRawAxis(Constants.rightYaxis));
		else if(counter == 0)
			Elevator.run(States.INTAKE_POSITION);
		else if(counter == 1)
			Elevator.run(States.SWITCH_POSITION);
		else if(counter == 2)
			Elevator.run(States.SCALE_POSITION_L);
		else if(counter == 3)
			Elevator.run(States.SCALE_POSITION_M);
		else if(counter == 4)
			Elevator.run(States.SCALE_POSITION_H);
		
		if(!buttonCheckRB && stick2.getRawButton(Constants.RB)){
			if(counter < 4)
				counter++;
			buttonCheckRB = true;
		}
		else if(buttonCheckRB && !stick2.getRawButton(Constants.RB))
			buttonCheckRB = false;
		
		if(!buttonCheckLB && stick2.getRawButton(Constants.LB)){
			if(counter >1)
				counter--;
			buttonCheckLB = true;
		}
		else if(buttonCheckLB && !stick2.getRawButton(Constants.LB))
			buttonCheckLB = false;
		
		/*if(button1)
			Ramp.run(States.RIGHT_RAMP_DOWN);
		if(button2)
			Ramp.run(States.LEFT_RAMP_DOWN);
		if(button3)
			Ramp.run(States.RIGHT_RAMP_UP);
		if(button4)
			Ramp.run(States.LEFT_RAMP_UP);*/
		
	}
	public static void setRumble(int controller, double intensity){
		if(controller == 1){
			stick1.setRumble(GenericHID.RumbleType.kLeftRumble, intensity);
			stick1.setRumble(GenericHID.RumbleType.kRightRumble, intensity);
		}
		else if(controller == 2){
			stick2.setRumble(GenericHID.RumbleType.kLeftRumble, intensity);
			stick2.setRumble(GenericHID.RumbleType.kRightRumble, intensity);
		}
	}

}
