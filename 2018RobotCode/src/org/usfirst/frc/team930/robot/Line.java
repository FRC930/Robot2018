package org.usfirst.frc.team930.robot;

import edu.wpi.first.wpilibj.Timer;

public class Line extends Routine{
	
	Timer time = new Timer();
	
	public Line(String v, double d) {
		
		super(v, d);
		time.reset();
		time.start();
		
	}

	public void variation() {
		
		System.out.println("Line");
		System.out.println("Elevator Up");
		Elevator.run(TeleopHandler.ElevatorStates.SWITCH_POSITION);
		if(time.get() <= 5)
			Drive.runAt(0.25, 0.25);
		else {
			Drive.runAt(0, 0);
		}
	}

}

