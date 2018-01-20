package org.usfirst.frc.team930.robot;

import org.usfirst.frc.team930.robot.Robot.Routines;

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
		
		switch (routineEnum) {
			case ROUTINE1:
				System.out.println("Running Routine 1");
				routine.run();
				break;
			case ROUTINE2:
				routine.run();
				break;
		}
		
	}

}
