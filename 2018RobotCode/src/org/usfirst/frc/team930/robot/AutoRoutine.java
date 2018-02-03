package org.usfirst.frc.team930.robot;

import org.usfirst.frc.team930.robot.AutoHandler.Routines;

import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Waypoint;

public class AutoRoutine {

	private String variation;
	private Routine routine;
	
	private Routines routineEnum;
	
	public static Waypoint[] points;
	
	public AutoRoutine(Enum r, String v) {
		
		variation = v;
		routineEnum = (Routines) r;
		if(routineEnum == Routines.ROUTINE1) {
			routine = new Routine1(variation);
			
			points = new Waypoint[] {
					 new Waypoint(0, 0, Pathfinder.d2r(0)),      
					 new Waypoint(3,0,0),
					 new Waypoint(5,-2,Pathfinder.d2r(270)),
				};
			
		}
		else if(routineEnum == Routines.ROUTINE2) {
			routine = new Routine2(variation);
			
			points = new Waypoint[] {
					new Waypoint(0, 0, Pathfinder.d2r(0)),     
				    new Waypoint(2,0,0),
				    new Waypoint(5,-2,Pathfinder.d2r(270)),
				};
			
		}
		
	}
	
	public void run() {
		
		System.out.println("Running Routine");
		routine.run();
		
	}

}
