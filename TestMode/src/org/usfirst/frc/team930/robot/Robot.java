package org.usfirst.frc.team930.robot;


import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Robot extends TimedRobot {
	
	boolean empty;
	boolean buttonsConst[] = new boolean[5];
	boolean buttons[] = new boolean[5];
	String functionNames[] = new String[5];
	String functionOutcome[] = new String[5];

	@Override
	public void robotInit() {
		
		for (int x = 0; x < 5; x++) {
			buttonsConst[x] = false;
		}
		functionNames[0] = "Left Drive Encoder";
		functionNames[1] = "Right Drive Encoder";
		functionNames[2] = "Elevator Encoder";
		
		empty = SmartDashboard.putBoolean("Test", false);
		empty = SmartDashboard.putBooleanArray("Buttons", buttonsConst);
		empty = SmartDashboard.putStringArray("Functions", functionNames);
		empty = SmartDashboard.putStringArray("Function Outcome", functionOutcome);
		
	}

	@Override
	public void autonomousInit() {
	}

	@Override
	public void autonomousPeriodic() {
	}

	@Override
	public void teleopPeriodic() {
	}
	
	@Override
	public void testPeriodic() {
		buttons = SmartDashboard.getBooleanArray("Buttons", buttonsConst);
		
		if(buttons[0]) {
			empty = SmartDashboard.putBooleanArray("Buttons", buttonsConst);
			boolean test = true;
			// add code to test left drive encoder
			if(test) {
				functionOutcome[0] = "Passed";
			} else {
				functionOutcome[0] = "Failed";
			}
			empty = SmartDashboard.putStringArray("Function Outcome", functionOutcome);
		}
		
		if(buttons[1]) {
			empty = SmartDashboard.putBooleanArray("Buttons", buttonsConst);
			boolean test = true;
			// add code to test right drive encoder
			if(test) {
				functionOutcome[1] = "Passed";
			} else {
				functionOutcome[1] = "Failed";
			}
			empty = SmartDashboard.putStringArray("Function Outcome", functionOutcome);
		}
		
		if(buttons[2]) {
			empty = SmartDashboard.putBooleanArray("Buttons", buttonsConst);
			boolean test = true;
			// add code to test elevator encoder
			if(test) {
				functionOutcome[2] = "Passed";
			} else {
				functionOutcome[2] = "Failed";
			}
			empty = SmartDashboard.putStringArray("Function Outcome", functionOutcome);
		}
	}
}
