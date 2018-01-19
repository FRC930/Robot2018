package org.usfirst.frc.team930.robot;

import org.usfirst.frc.team930.robot.Robot.Routines;

public class AutoRoutine {

	private String variation;
	private Routine1 routine1;
	private Routine2 routine2;
	
	private Routines routineEnum;
	
	public AutoRoutine(Enum r, String v) {
		
		variation = v;
		routineEnum = (Routines) r;
		if(routineEnum == Routines.ROUTINE1) {
			routine1 = new Routine1(variation);
		}
		else if(routineEnum == Routines.ROUTINE2) {
			routine2 = new Routine2(variation);
		}
		
	}
	
	public void run() {
		
		switch (routineEnum) {
			case ROUTINE1:
				routine1.run();
				break;
			case ROUTINE2:
				routine2.run();
				break;
		}
		
	}

}
