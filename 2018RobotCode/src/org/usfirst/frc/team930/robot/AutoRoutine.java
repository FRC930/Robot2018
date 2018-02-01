package org.usfirst.frc.team930.robot;

import org.usfirst.frc.team930.robot.AutoHandler.Routines;

public class AutoRoutine {

	private String variation;
	private Routine routine;
	
	private Routines routineEnum;
	
	public AutoRoutine(Enum r, String v) {
		
		variation = v;
		routineEnum = (Routines) r;
		if(routineEnum == Routines.ROUTINE1) {
			routine = new Routine1(variation);
		}
		else if(routineEnum == Routines.ROUTINE2) {
			routine = new Routine2(variation);
		}
		
	}
	
	public void run() {
		
		System.out.println("Running Routine");
		routine.run();
		
	}

}
