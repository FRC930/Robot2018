package org.usfirst.frc.team930.robot;

import edu.wpi.first.wpilibj.Timer;

public class LineScore extends Routine{
	
	Timer time = new Timer();
	
	public LineScore(String v, double d) {
		
		super(v, d);
		time.reset();
		time.start();
		
	}

	public void variation() {
		
		System.out.println("Line & Score");
		System.out.println("Elevator Up");
		Elevator.run(TeleopHandler.ElevatorStates.SWITCH_POSITION);
		if(time.get()<5)
			Drive.runAt(0.25, 0.25);
		else {
			Drive.runAt(0, 0);
			System.out.println("Outtaking Cube");
			Intake.run(TeleopHandler.IntakeStates.OUTTAKING);
		}
			
	}

}
