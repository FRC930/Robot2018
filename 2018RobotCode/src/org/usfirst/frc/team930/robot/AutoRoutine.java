package org.usfirst.frc.team930.robot;

import org.usfirst.frc.team930.robot.AutoHandler.Goal;
import org.usfirst.frc.team930.robot.AutoHandler.StartPositions;

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
				switch (variation) {

				case "LRL":
					r = new LeftRightScale(variation);
					break;
				case "RLR":
					r = new LeftLeftScale(variation);
					break;
				case "LLL":
					r = new LeftLeftScale(variation);
					break;
				case "RRR":
					r = new LeftRightScale(variation);
					break;

				}
				break;
			case ALWAYS_SWITCH:
				switch (variation) {

				case "LRL":
					r = new LeftLeftSwitch(variation);
					break;
				case "RLR":
					r = new LeftRightSwitch(variation);
					break;
				case "LLL":
					r = new LeftLeftSwitch(variation);
					break;
				case "RRR":
					r = new LeftRightSwitch(variation);
					break;

				}
				break;
			case PERFERRED_SCALE:
				switch (variation) {

				case "LRL":
					r = new LeftLeftSwitch(variation);
					break;
				case "RLR":
					r = new LeftLeftScale(variation);
					break;
				case "LLL":
					r = new LeftLeftScale(variation);
					break;
				case "RRR":
					r = new Line(variation);
					break;

				}
				break;
			case PERFERRED_SWITCH:
				switch (variation) {

				case "LRL":
					r = new LeftLeftSwitch(variation);
					break;
				case "RLR":
					r = new LeftLeftScale(variation);
					break;
				case "LLL":
					r = new LeftLeftSwitch(variation);
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
				switch (variation) {

				case "LRL":
					r = new RightRightScale(variation);
					break;
				case "RLR":
					r = new RightLeftScale(variation);
					break;
				case "LLL":
					r = new RightLeftScale(variation);
					break;
				case "RRR":
					r = new RightRightScale(variation);
					break;

				}
				break;
			case ALWAYS_SWITCH:
				switch (variation) {

				case "LRL":
					r = new RightLeftSwitch(variation);
					break;
				case "RLR":
					r = new RightRightSwitch(variation);
					break;
				case "LLL":
					r = new RightLeftSwitch(variation);
					break;
				case "RRR":
					r = new RightRightSwitch(variation);
					break;

				}
				break;
			case PERFERRED_SCALE:
				switch (variation) {

				case "LRL":
					r = new RightRightScale(variation);
					break;
				case "RLR":
					r = new RightRightSwitch(variation);
					break;
				case "LLL":
					r = new Line(variation);
					break;
				case "RRR":
					r = new RightRightScale(variation);
					break;

				}
				break;
			case PERFERRED_SWITCH:
				switch (variation) {

				case "LRL":
					r = new RightRightScale(variation);
					break;
				case "RLR":
					r = new RightRightSwitch(variation);
					break;
				case "LLL":
					r = new Line(variation);
					break;
				case "RRR":
					r = new RightRightSwitch(variation);
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
