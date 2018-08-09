package org.usfirst.frc.team930.robot;

import edu.wpi.first.wpilibj.Notifier;
import edu.wpi.first.wpilibj.Timer;

/*
 * Moves to the left side of the scale when you start on the left
 */
public class StartLDoubleScaleL extends Routine {
	
	private Timer time = new Timer();
	private TimeDelay delayElev = new TimeDelay();
	private TimeDelay delayOuttake = new TimeDelay();
	private TimeDelay delayStopIntake = new TimeDelay();
	public static Notifier n;
	
	/*
	 * Initializes MP path, Notifier, variation, and individual time delays for robot functions
	 */
	public StartLDoubleScaleL(String v, double d) {
		
		super(v, d);
		delayElev.set(Constants.ETime7);
		delayOuttake.set(Constants.OTime4);
		delayStopIntake.set(Constants.STime2);
		
		n = new Notifier (AutoHandler.mpStartLScaleL);
		AutoHandler.mpStartLScaleL.startPath();
		
		time.start();
		
		
	}
	/*
	 * Strings together MP path with actions
	 */
	public void variation() {
		
		switch (this.autoStep) {
		case 1: // Lifts wrist, starts intaking and starts MP path
			System.out.println("LEFT DOUBLE SCALE");
			System.out.println("START");
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
				System.out.println("*****Transition to Case 3");
			}
			break;
		case 3: // Slow outtake of cube after time delay 
			System.out.println("Running case 3");
			if(delayOuttake.execute(time.get()))	{
				this.autoStep = 4;
				actList.slowOuttake();
				System.out.println("*****Transition to Case 4");
			}
			break;
		case 4: // Checking if MP path is done
			System.out.println("Running case 4");
			if(segList.segStartLScaleL()) {
				this.autoStep = 5;
				n.stop();
				System.out.println("*****Transition to Case 5");
			}
			break;
		case 5: // Stops intake and drivetrain
			System.out.println("Running case 5");
			if(delayStopIntake.execute(time.get()))
				actList.stopIntake();
			Drive.runAt(0, 0);
			System.out.println("FINISHED PATH");
			break;
		}
		
	}

}
