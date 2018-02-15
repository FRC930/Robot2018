package org.usfirst.frc.team930.robot;

import edu.wpi.first.wpilibj.Timer;

public class NothingAuto extends Routine{
	
	Timer time = new Timer();
	
	public NothingAuto(String v, double d) {
		
		super(v, d);
		time.reset();
		time.start();
		
	}

	public void variation() {
		
		System.out.println("Nothing Auto");
		
	}

}

