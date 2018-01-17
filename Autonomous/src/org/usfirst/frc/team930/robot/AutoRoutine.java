package org.usfirst.frc.team930.robot;

public class AutoRoutine {

	private String variation;
	private Routine1 routine1;
	private Routine2 routine2;
	
	enum Routines {
		
		 ROUTINE1,
		 ROUTINE2
		 
	}
	
	private Routines routineEnum;
	
	public AutoRoutine(int r, String v) {
		
		variation = v;
		if(r == 1) {
			routine1 = new Routine1(variation);
			routineEnum = Routines.ROUTINE1;
		}
		else if(r == 2) {
			routine2 = new Routine2(variation);
			routineEnum = Routines.ROUTINE2;
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
