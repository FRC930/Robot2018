package org.usfirst.frc.team930.robot;

import edu.wpi.first.wpilibj.Timer;

public class TimedMiddleLeft extends Routine{
	
	Timer time = new Timer();
	private TimeDelay drive1 = new TimeDelay();
	private TimeDelay turn1 = new TimeDelay();
	private TimeDelay drive2 = new TimeDelay();
	private TimeDelay turn2 = new TimeDelay();
	private TimeDelay drive3 = new TimeDelay();
	private TimeDelay outtake = new TimeDelay();
	
	public TimedMiddleLeft(String v, double d) {
		
		super(v, d);
		drive1.set(Constants.LMiddleDrive1);
		turn1.set(Constants.LMiddleTurn1);
		drive2.set(Constants.LMiddleDrive2);
		turn2.set(Constants.LMiddleTurn2);
		drive3.set(Constants.LMiddleDrive3);
		outtake.set(Constants.LMiddleOuttake);
		time.reset();
		time.start();
		
	}

	public void variation() {
		
		actList.intake();
		
		if(time.get() <= Constants.LMiddleDrive1) {
			Drive.runAt(0.5, 0.5);
		}
		else if((time.get() > Constants.LMiddleDrive1) && (time.get() <= Constants.LMiddleDrive1 + Constants.LMiddleTurn1)) {
			Drive.runAt(-0.5, 0.5);
		}
		else if((time.get() > Constants.LMiddleDrive1 + Constants.LMiddleTurn1) && (time.get() <= Constants.LMiddleDrive1 + Constants.LMiddleTurn1 + Constants.LMiddleDrive2)) {
			Drive.runAt(0.5, 0.5);
		}
		else if((time.get() > Constants.LMiddleDrive1 + Constants.LMiddleTurn1 + Constants.LMiddleDrive2) && (time.get() <= Constants.LMiddleDrive1 + Constants.LMiddleTurn1 + Constants.LMiddleDrive2 + Constants.LMiddleTurn2)) {
			Drive.runAt(0.5, -0.5);
			Elevator.setTargetPos(TeleopHandler.ElevatorStates.AUTO_SWITCH);
			Elevator.run(0);
		}
		else if((time.get() > Constants.LMiddleDrive1 + Constants.LMiddleTurn1 + Constants.LMiddleDrive2 + Constants.LMiddleTurn2) && (time.get() <= Constants.LMiddleDrive1 + Constants.LMiddleTurn1 + Constants.LMiddleDrive2 + Constants.LMiddleTurn2 + Constants.LMiddleDrive3)) {
			Drive.runAt(0.5, 0.5);
		}
		else if((time.get() > Constants.LMiddleDrive1 + Constants.LMiddleTurn1 + Constants.LMiddleDrive2 + Constants.LMiddleTurn2 + Constants.LMiddleDrive3) && (time.get() <= Constants.LMiddleDrive1 + Constants.LMiddleTurn1 + Constants.LMiddleDrive2 + Constants.LMiddleTurn2 + Constants.LMiddleDrive3 + Constants.LMiddleOuttake)){
			Drive.runAt(0, 0);
		}
		else {
			Intake.run(TeleopHandler.IntakeStates.SLOW_OUTTAKING);
		}
		
	}

}
