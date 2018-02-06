package org.usfirst.frc.team930.robot;

import org.usfirst.frc.team930.robot.AutoHandler.Goal;
import org.usfirst.frc.team930.robot.AutoHandler.StartPositions;

import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Waypoint;

public class AutoRoutine {

	private String variation;
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
				r = new LeftScale(variation);
				break;
			case ALWAYS_SWITCH:
				r = new LeftSwitch(variation);
				break;
			case PERFERRED_SCALE:
				switch (variation) {

				case "LRL":
					r = new LeftSwitch(variation);
					break;
				case "RLR":
					r = new LeftScale(variation);
					break;
				case "LLL":
					r = new LeftScale(variation);
					break;
				case "RRR":
					r = new Line(variation);
					break;

				}
				break;
			case PERFERRED_SWITCH:
				switch (variation) {

				case "LRL":
					r = new LeftSwitch(variation);
					break;
				case "RLR":
					r = new LeftScale(variation);
					break;
				case "LLL":
					r = new LeftSwitch(variation);
					break;
				case "RRR":
					r = new Line(variation);
					break;

				}
				break;

			}
		case MIDDLE:
			switch (goalEnum) {

			case ALWAYS_SWITCH:
				switch (variation) {

				case "LRL":
					r = new MiddleLeftSwitch(variation);
					break;
				case "RLR":
					r = new MiddleRightSwitch(variation);
					break;
				case "LLL":
					r = new MiddleLeftSwitch(variation);
					break;
				case "RRR":
					r = new MiddleRightSwitch(variation);
					break;

				}
				break;
			case LINE:
				r = new Line(variation);
				break;

			}
			break;
		case RIGHT:
			switch (goalEnum) {

			case ALWAYS_SCALE:
				r = new RightScale(variation);
				break;
			case ALWAYS_SWITCH:
				r = new RightSwitch(variation);
				break;
			case PERFERRED_SCALE:
				switch (variation) {

				case "LRL":
					r = new RightScale(variation);
					break;
				case "RLR":
					r = new RightSwitch(variation);
					break;
				case "LLL":
					r = new Line(variation);
					break;
				case "RRR":
					r = new RightScale(variation);
					break;

				}
				break;
			case PERFERRED_SWITCH:
				switch (variation) {

				case "LRL":
					r = new RightScale(variation);
					break;
				case "RLR":
					r = new RightSwitch(variation);
					break;
				case "LLL":
					r = new Line(variation);
					break;
				case "RRR":
					r = new RightSwitch(variation);
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
