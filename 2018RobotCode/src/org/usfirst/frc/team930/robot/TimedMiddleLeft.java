package org.usfirst.frc.team930.robot;

import edu.wpi.first.wpilibj.Timer;

/*
 * Timed auto routine to score in left switch starting in the middle position
 */
public class TimedMiddleLeft extends Routine {
	
	Timer time = new Timer();
	private TimeDelay drive1 = new TimeDelay();
	private TimeDelay turn1 = new TimeDelay();
	private TimeDelay drive2 = new TimeDelay();
	private TimeDelay turn2 = new TimeDelay();
	private TimeDelay drive3 = new TimeDelay();
	private TimeDelay outtake = new TimeDelay();
	
	// Initializes variation, initial time delay for auto routine, and specific time delays for functions
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

	// Using timed stages to score in the left switch
	public void variation() {
		
		// Continuously intaking
		actList.intake();
		
		// Drive forward
		if(time.get() <= Constants.LMiddleDrive1) {
			Drive.runAt(0.5, 0.5);
		}
		
		// Turn left
		else if((time.get() > Constants.LMiddleDrive1) && (time.get() <= Constants.LMiddleDrive1 + Constants.LMiddleTurn1)) {
			Drive.runAt(-0.5, 0.5);
		}
		
		// Drive forward
		else if((time.get() > Constants.LMiddleDrive1 + Constants.LMiddleTurn1) && (time.get() <= Constants.LMiddleDrive1 + Constants.LMiddleTurn1 + Constants.LMiddleDrive2)) {
			Drive.runAt(0.5, 0.5);
		}
		
		// Turn right and lift elevator
		else if((time.get() > Constants.LMiddleDrive1 + Constants.LMiddleTurn1 + Constants.LMiddleDrive2) && (time.get() <= Constants.LMiddleDrive1 + Constants.LMiddleTurn1 + Constants.LMiddleDrive2 + Constants.LMiddleTurn2)) {
			Drive.runAt(0.5, -0.5);
			Elevator.setTargetPos(TeleopHandler.ElevatorStates.AUTO_SWITCH);
			Elevator.run(0);
		}
		
		// Drive forward
		else if((time.get() > Constants.LMiddleDrive1 + Constants.LMiddleTurn1 + Constants.LMiddleDrive2 + Constants.LMiddleTurn2) && (time.get() <= Constants.LMiddleDrive1 + Constants.LMiddleTurn1 + Constants.LMiddleDrive2 + Constants.LMiddleTurn2 + Constants.LMiddleDrive3)) {
			Drive.runAt(0.5, 0.5);
		}
		
		// Stop drive motors
		else if((time.get() > Constants.LMiddleDrive1 + Constants.LMiddleTurn1 + Constants.LMiddleDrive2 + Constants.LMiddleTurn2 + Constants.LMiddleDrive3) && (time.get() <= Constants.LMiddleDrive1 + Constants.LMiddleTurn1 + Constants.LMiddleDrive2 + Constants.LMiddleTurn2 + Constants.LMiddleDrive3 + Constants.LMiddleOuttake)){
			Drive.runAt(0, 0);
		}
		
		// Outtake cube
		else {
			Intake.run(TeleopHandler.IntakeStates.SLOW_OUTTAKING);
		}
		
	}

}
