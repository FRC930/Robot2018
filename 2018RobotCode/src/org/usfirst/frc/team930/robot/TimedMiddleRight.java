package org.usfirst.frc.team930.robot;

import edu.wpi.first.wpilibj.Timer;

public class TimedMiddleRight extends Routine{
	
	Timer time = new Timer();
	private TimeDelay drive1 = new TimeDelay();
	private TimeDelay turn1 = new TimeDelay();
	private TimeDelay drive2 = new TimeDelay();
	private TimeDelay turn2 = new TimeDelay();
	private TimeDelay drive3 = new TimeDelay();
	private TimeDelay outtake = new TimeDelay();
	
	public TimedMiddleRight(String v, double d) {
		
		super(v, d);
		drive1.set(Constants.RMiddleDrive1);
		turn1.set(Constants.RMiddleTurn1);
		drive2.set(Constants.RMiddleDrive2);
		turn2.set(Constants.RMiddleTurn2);
		drive3.set(Constants.RMiddleDrive3);
		outtake.set(Constants.RMiddleOuttake);
		time.reset();
		time.start();
		
	}

	public void variation() {
		
		actList.intake();
		
		if(time.get() <= Constants.RMiddleDrive1) {
			Drive.runAt(0.5, 0.5);
		}
		else if((time.get() > Constants.RMiddleDrive1) && (time.get() <= Constants.RMiddleDrive1 + Constants.RMiddleTurn1)) {
			Drive.runAt(0.5, -0.5);
		}
		else if((time.get() > Constants.RMiddleDrive1 + Constants.RMiddleTurn1) && (time.get() <= Constants.RMiddleDrive1 + Constants.RMiddleTurn1 + Constants.RMiddleDrive2)) {
			Drive.runAt(0.5, 0.5);
		}
		else if((time.get() > Constants.RMiddleDrive1 + Constants.RMiddleTurn1 + Constants.RMiddleDrive2) && (time.get() <= Constants.RMiddleDrive1 + Constants.RMiddleTurn1 + Constants.RMiddleDrive2 + Constants.RMiddleTurn2)) {
			Drive.runAt(-0.5, 0.5);
			Elevator.setTargetPos(TeleopHandler.ElevatorStates.AUTO_SWITCH);
			Elevator.run(0);
		}
		else if((time.get() > Constants.RMiddleDrive1 + Constants.RMiddleTurn1 + Constants.RMiddleDrive2 + Constants.RMiddleTurn2) && (time.get() <= Constants.RMiddleDrive1 + Constants.RMiddleTurn1 + Constants.RMiddleDrive2 + Constants.RMiddleTurn2 + Constants.RMiddleDrive3)) {
			Drive.runAt(0.5, 0.5);
		}
		else if((time.get() > Constants.RMiddleDrive1 + Constants.RMiddleTurn1 + Constants.RMiddleDrive2 + Constants.RMiddleTurn2 + Constants.RMiddleDrive3) && (time.get() <= Constants.RMiddleDrive1 + Constants.RMiddleTurn1 + Constants.RMiddleDrive2 + Constants.RMiddleTurn2 + Constants.RMiddleDrive3 + Constants.RMiddleOuttake)) {
			Drive.runAt(0, 0);
		}
		else {
			Intake.run(TeleopHandler.IntakeStates.SLOW_OUTTAKING);
		}
		
	}

}
