package org.usfirst.frc.team930.robot;

import edu.wpi.first.wpilibj.Notifier;
import edu.wpi.first.wpilibj.Timer;

/*
 * Moves to the left side of the scale when you start on the left
 */
public class StartLDoubleScaleR extends Routine {
	
	private Timer time = new Timer();
	private TimeDelay delayElev = new TimeDelay();
	private TimeDelay delayOuttake = new TimeDelay();
	private TimeDelay delayStopIntake = new TimeDelay();
	public static Notifier n;
	
	/*
	 * Initializes MP path, Notifier, variation, and individual time delays for robot functions
	 */
	public StartLDoubleScaleR(String v, double d) {
		
		super(v, d);
		delayElev.set(Constants.ETime3);
		delayOuttake.set(Constants.ITime3);
		delayStopIntake.set(Constants.OTime1);
		
		n = new Notifier (AutoHandler.mpStartLScaleR);
		AutoHandler.mpStartLScaleR.startPath();
		
		time.start();
		
		
	}
	
	/*
	 * Strings together MP path with actions
	 */
	public void variation() {
		
		switch (this.autoStep) {
		case 1: // Lifts wrist, starts intaking and starts MP path
			System.out.println("Running case 1");
			actList.wristUp();
			actList.intake();
			n.startPeriodic(0.02);
			this.autoStep = 2;
			break;
		case 2: // Lift elevator after time delay
			System.out.println("Running case 2");
			if(delayElev.execute(time.get()))	{
				this.autoStep = 3;
				actList.scaleMPosition();
				System.out.println("*****Transition to Case 2");
			}
			break;
		case 3: // Slow outtake of cube after time delay 
			System.out.println("Running case 2");
			if(delayOuttake.execute(time.get()))	{
				this.autoStep = 4;
				actList.slowOuttake();
				System.out.println("*****Transition to Case 2");
			}
			break;
		case 4: // Checking if MP path is done
			System.out.println("Running case 3");
			if(segList.segStartLScaleR()) {
				this.autoStep = 5;
				n.stop();
				System.out.println("*****Transition to Case 4");
			}
			break;
		case 5: // Stops intake and drivetrain
			if(delayStopIntake.execute(time.get()))
				actList.stopIntake();
			Drive.runAt(0, 0);
			break;
		}
		
	}

}
