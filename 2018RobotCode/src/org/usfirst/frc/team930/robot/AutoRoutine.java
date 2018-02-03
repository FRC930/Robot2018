package org.usfirst.frc.team930.robot;

import org.usfirst.frc.team930.robot.AutoHandler.Goal;
import org.usfirst.frc.team930.robot.AutoHandler.StartPositions;

import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Waypoint;

public class AutoRoutine {

	private String variation;
	private StartPositions pos;
	private Routine r;

	private StartPositions posEnum;
	private Goal goalEnum;

	public static Waypoint[] points;

	public AutoRoutine(Enum p, Enum g, String v) {

		variation = v;
		posEnum = (StartPositions) p;
		goalEnum = (Goal) g;

		switch (posEnum) {

		case LEFT:
			switch (goalEnum) {

			case ALWAYS_SCALE:
				r = new Routine1(variation);
				break;
			case ALWAYS_SWITCH:
				r = new Routine2(variation);
				break;
			case PERFERRED_SCALE:
				switch (variation) {

				case "LRL":
					r = new Routine2(variation);
					break;
				case "RLR":
					r = new Routine1(variation);
					break;
				case "LLL":
					r = new Routine1(variation);
					break;
				case "RRR":
					r = new Routine7(variation);
					break;

				}
				break;
			case PERFERRED_SWITCH:
				switch (variation) {

				case "LRL":
					r = new Routine2(variation);
					break;
				case "RLR":
					r = new Routine1(variation);
					break;
				case "LLL":
					r = new Routine2(variation);
					break;
				case "RRR":
					r = new Routine7(variation);
					break;

				}
				break;

			}
		case MIDDLE:
			switch (goalEnum) {

			case ALWAYS_SWITCH:
				r = new Routine4(variation);
				break;
			case LINE:
				r = new Routine7(variation);
				break;

			}
			break;
		case RIGHT:
			switch (goalEnum) {

			case ALWAYS_SCALE:
				r = new Routine5(variation);
				break;
			case ALWAYS_SWITCH:
				r = new Routine6(variation);
				break;
			case PERFERRED_SCALE:
				switch (variation) {

				case "LRL":
					r = new Routine5(variation);
					break;
				case "RLR":
					r = new Routine6(variation);
					break;
				case "LLL":
					r = new Routine7(variation);
					break;
				case "RRR":
					r = new Routine5(variation);
					break;

				}
				break;
			case PERFERRED_SWITCH:
				switch (variation) {

				case "LRL":
					r = new Routine5(variation);
					break;
				case "RLR":
					r = new Routine6(variation);
					break;
				case "LLL":
					r = new Routine7(variation);
					break;
				case "RRR":
					r = new Routine6(variation);
					break;

				}
				break;

			}

		}

	}

	public void run() {

		System.out.println("Running Routine");
		r.run();

	}

}
