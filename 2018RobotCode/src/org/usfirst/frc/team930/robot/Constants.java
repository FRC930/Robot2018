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
	//---Button Board
	public static final int btnRightRampUp = 12;
	public static final int btnRightRampDown = 2;
	public static final int btnLeftRampUp = 1;
	public static final int btnLeftRampDown = 7; 
	
	// Drive Constants
	public static final int rightMainTalonID = 1; 
	public static final int rightFollowVictorID = 2;
	public static final int rightFollow2VictorID = 3;
	public static final int leftMainTalonID = 4;  
	public static final int leftFollowVictorID = 5;
	public static final int leftFollow2Victor = 6;
	public static final double deadBand = 0.05;
	
	// In take Constants
	public static final int rightIntakeWheelVictorID  = 9;
	public static final int leftIntakeWheelVictorID = 10;
	public static final int lifterForwardSolenoidID = 0;
	//public static final int lifterReverseSolenoidID = 5;
	public static final int gripperSolenoidID = 1;
	public static final double currentThreshhold = 22.0;				
	public static final double intakeMotorSpeed = 0.75;		
	public static final int PDPcounterLimit = 2;			
	
	// Elevator Constants
	public static final int liftTalonID = 7;
	public static final int lift2TalonID = 8;
	public static final int kSlotIdx = 0;
	public static final int kPIDLoopIdx = 0;
	public static final int kTimeoutMs = 10;

	public static final double intakePosition = 0;
	public static final double switchPosition = 3000;
	public static final double scalePositionLow = 6750;
	public static final double scalePositionMid = 7750;
	public static final double scalePositionHigh = 8500;

	public static final int targetMultiplier = -400;
	public static final int counterLimit = 3;
	
	//Ramp Constants
	public static final int rampLSolenoidID = 4;
	public static final int rampRSolenoidID = 5;
	public static final int raiseLSolenoidID = 6;
	public static final int raiseRSolenoidID = 7;
	
	//PDP Ports
	public static final int pdpIntakePort = 11;
	
	//Arduino Addresses
	public static final int arduinoAddress = 84;
	
	// Camera Constants
	public static final int cameraResWidth = 640;
	public static final int cameraResHeight = 480;
	public static final int cameraDeviceID = 0;
}
