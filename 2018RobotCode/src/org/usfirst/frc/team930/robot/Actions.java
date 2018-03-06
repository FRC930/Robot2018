package org.usfirst.frc.team930.robot;

import org.usfirst.frc.team930.robot.TeleopHandler.ElevatorStates;

public class Actions {
	
	private int path = 1;
	private int point = 1;
	
	public boolean switchPosition() {
		
		boolean isFinished = false;
		switch (this.path) {
		case 1:
			Elevator.run(ElevatorStates.SWITCH_POSITION);
			Elevator.run(0);
			
			isFinished = true;
			
			break;
		/*case 2:
			System.out.println("Running Action 1 Case 2");
			System.out.println("Count: " + point);
			
			if(MotionProfile.isLastPoint()) {
				this.point = 0;
				this.path = 2;
				isFinished = true;
			}
			
			this.point++;
			
			break;*/
		}
		
		return isFinished;
		
	}
	
	public boolean scaleMPosition() {
		
		boolean isFinished = false;
		switch (this.path) {
		case 1:
			Elevator.run(ElevatorStates.SCALE_POSITION_M);
			Elevator.run(0);
			
			isFinished = true;
			
			break;
		/*case 2:
			System.out.println("Running Action 1 Case 2");
			System.out.println("Count: " + point);
			
			if(MotionProfile.isLastPoint()) {
				this.point = 0;
				this.path = 2;
				isFinished = true;
			}
			
			this.point++;
			
			break;*/
		}
		
		return isFinished;
		
	}
	
	public boolean slowOuttake() {
		
		boolean isFinished = false;
		switch (this.path) {
		case 1:
			Intake.run(TeleopHandler.IntakeStates.SLOW_OUTTAKING);
			
			isFinished = true;
			
			break;
		/*case 2:
			System.out.println("Running Action 1 Case 2");
			System.out.println("Count: " + point);
			
			if(MotionProfile.isLastPoint()) {
				this.point = 0;
				this.path = 2;
				isFinished = true;
			}
			
			this.point++;
			
			break;*/
		}
		
		return isFinished;
		
	}
	
	public boolean wristUp() {
		
		boolean isFinished = false;
		switch (this.path) {
		case 1:
			Intake.run(TeleopHandler.IntakeStates.LIFTER_DOWN);
			
			isFinished = true;
			
			break;
		/*case 2:
			System.out.println("Running Action 1 Case 2");
			System.out.println("Count: " + point);
			
			if(MotionProfile.isLastPoint()) {
				this.point = 0;
				this.path = 2;
				isFinished = true;
			}
			
			this.point++;
			
			break;*/
		}
		
		return isFinished;
		
	}
	
	public boolean stopIntake() {
		
		boolean isFinished = false;
		switch (this.path) {
		case 1:
			Intake.run(TeleopHandler.IntakeStates.INTAKE_DONE);
			
			isFinished = true;
			
			break;
		/*case 2:
			System.out.println("Running Action 1 Case 2");
			System.out.println("Count: " + point);
			
			if(MotionProfile.isLastPoint()) {
				this.point = 0;
				this.path = 2;
				isFinished = true;
			}
			
			this.point++;
			
			break;*/
		}
		
		return isFinished;
		
	}

}
