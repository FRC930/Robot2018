package org.usfirst.frc.team930.robot;

public class Constants {
	
	//Joystick
	//****Axis
	public static final int leftXaxis = 0;
	public static final int leftYaxis = 1;
	public static final int leftTriggerAxis = 2;
	public static final int rightTriggerAxis = 3;
	public static final int rightXaxis = 4;
	public static final int rightYaxis = 5;
	
	//****Buttons
	public static final int A = 1;
	public static final int B = 2;
	public static final int X = 3;
	public static final int Y = 4;
	public static final int LB = 5;
	public static final int RB = 6;
	public static final int backButton = 7;
	public static final int startButton = 8;
	public static final int rightJoyButton = 9;
	public static final int leftJoyButton = 10;
	
	// Drive Constants
	public static final int rightMainTalonID = 4;
	public static final int leftMainTalonID = 1;
	public static final int rightFollowVictorID = 2;
	public static final int leftFollowVictorID = 3;
	public static final int rightFollow2VictorID = 4;
	public static final int leftFollow2Victor = 5;
	public static final double deadBand = 0.15;
	
	// In take Constants
	public static final double currentThreshhold = 35.0;	//Thresh hold for the in take PDP current			
	public static final double intakeMotorSpeed = 0.75;		//Speed of in take motors.
	public static final int PDPcounterLimit = 15;			//Limit for loop counter.
	
	// Elevator Constants
	public static final int kSlotIdx = 0;
	public static final int kPIDLoopIdx = 0;
	public static final int kTimeoutMs = 10;
	public static final double intakePosition = 0;
	public static final double switchPosition = 2000;
	public static final double scalePositionLow = 5000;
	public static final double scalePositionMid = 6000;
	public static final double scalePositionHigh = 7000;
}
