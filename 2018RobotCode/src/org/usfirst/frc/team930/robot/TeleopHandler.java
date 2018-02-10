package org.usfirst.frc.team930.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Timer;

public class TeleopHandler {
	
	private static final Joystick stick1 = new Joystick(0);
	private static final Joystick stick2 = new Joystick(1);
	private static boolean buttonCheckRB = false;
	private static boolean buttonCheckLB = false;
	private static int elevatorCounter = 0;
	private static boolean switchBool = false;
	
	public static void init() {
	
	}
	
	public static void disabled() {
		
	}
	
	enum IntakeStates{
		INTAKING,
		INTAKE_DONE,
		OUTTAKING,
		LIFTER_UP,
		LIFTER_DOWN
	}
	enum ElevatorStates{
		INTAKE_POSITION,
		SWITCH_POSITION,
		SCALE_POSITION_L,
		SCALE_POSITION_M,
		SCALE_POSITION_H
	}
	enum RampStates{
		RIGHT_RAMP_DOWN,
		LEFT_RAMP_DOWN,
		RIGHT_RAMP_UP,
		LEFT_RAMP_UP
	}
	
	public static void run() {
		
		Drive.run(stick1.getRawAxis(Constants.rightXaxis), stick1.getRawAxis(Constants.leftYaxis));
		
		if(stick2.getRawAxis(Constants.rightTriggerAxis) > 0.7)																
			Intake.run(IntakeStates.INTAKING);
		else if(stick2.getRawAxis(Constants.leftTriggerAxis) > 0.7)																
			Intake.run(IntakeStates.OUTTAKING);
		else
			Intake.run(IntakeStates.INTAKE_DONE);
		
		
		//Elevator
Elevator.run(stick2.getRawAxis(Constants.rightYaxis));
		
		if (stick2.getRawButton(Constants.RB) && (!buttonCheckRB)) {
			buttonCheckRB = true;
			switchBool = true;
			if(elevatorCounter < 4 && elevatorCounter >= 0){
				elevatorCounter++;
				setRumble(2, 0.5);
				Timer.delay(0.05 + (0.05 * elevatorCounter));
				setRumble(2, 0.0);
			}
		} else if ((!stick2.getRawButton(Constants.RB)) && buttonCheckRB) {
			buttonCheckRB = false;
		}

		if (stick2.getRawButton(Constants.LB) && (!buttonCheckLB)) {
			buttonCheckLB = true;
			switchBool = true;
			if(elevatorCounter <= 4 && elevatorCounter > 0){
				elevatorCounter--;
				setRumble(2, 0.5);
				Timer.delay(0.05 + (0.05 * elevatorCounter));
				setRumble(2, 0.0);
			}
		} else if ((!stick2.getRawButton(Constants.LB)) && buttonCheckLB) {
			buttonCheckLB = false;
		}
		
		if (switchBool){
			switch(elevatorCounter){
			case 0:
				Elevator.run(ElevatorStates.INTAKE_POSITION);
				switchBool = false;
				break;
			case 1:
				Elevator.run(ElevatorStates.SWITCH_POSITION);
				switchBool = false;
				break;
			case 2:
				Elevator.run(ElevatorStates.SCALE_POSITION_L);
				switchBool = false;
				break;
			case 3:
				Elevator.run(ElevatorStates.SCALE_POSITION_M);
				switchBool = false;
				break;
			case 4:
				Elevator.run(ElevatorStates.SCALE_POSITION_H);
				switchBool = false;
				break;
			}
		}
		
		/*if(button1)
			Ramp.run(RampStates.RIGHT_RAMP_DOWN);
		if(button2)
			Ramp.run(RampStates.LEFT_RAMP_DOWN);
		if(button3)
			Ramp.run(RampStates.RIGHT_RAMP_UP);
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
