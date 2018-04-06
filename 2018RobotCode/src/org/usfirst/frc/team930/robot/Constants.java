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
	public static final int leftJoyButton = 9;
	public static final int rightJoyButton = 10;
	//---Button Board
	public static final int btnRightRampUp = 6;
	public static final int btnRightRampDown = 4;
	public static final int btnLeftRampUp = 3;
	public static final int btnLeftRampDown = 1; 
	
	// Drive Constants
	public static final int rightMainTalonID = 1; 
	public static final int rightFollowVictorID = 2;
	public static final int rightFollow2VictorID = 3;
	public static final int leftMainTalonID = 4;  
	public static final int leftFollowVictorID = 5;
	public static final int leftFollow2Victor = 6;
	public static final double driveDeadBand = 0.005;
	public static final double elevatorDeadBand = 0.01;
	
	// Intake Constants
	public static final int rightIntakeWheelVictorID  = 9;
	public static final int leftIntakeWheelVictorID = 10;
	public static final int lifterForwardSolenoidID = 0;
	//public static final int lifterReverseSolenoidID = 5;
	public static final int gripperSolenoidID = 1;
	public static final double currentThreshhold = 30.0; //30
	public static final double intakeMotorSpeed = 0.75;
	public static final double slowIntakeMotorSpeed = 0.3;
	//public static final double autoIntakeMotorSpeed = 0.2;
	public static final int PDPcounterLimit = 10;
	
	// Elevator Constants
	public static final int liftTalonID = 7;
	public static final int lift2TalonID = 8;
	public static final int kSlotIdx = 0;
	public static final int kPIDLoopIdx = 0;
	public static final int kTimeoutMs = 10;

	public static final double intakePosition = 0;
	public static final double switchPosition = 1800;
	public static final double scalePositionLow = 5000;
	public static final double scalePositionMid = 6800;//7600
	public static final double scalePositionHigh = 8000;
	public static final double exchangePosition = 250;
	public static final double portalPosition = 2500;
	public static final double autoSwitch = 3000;

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
	public static final int cameraResWidth = 320;
	public static final int cameraResHeight = 240;
	public static final int cameraFPS = 10;
	public static final int cameraDeviceID = 0;
	
	// ----- Auto Constants																																	lol
	
	// Elevator Delay Times
	public static final double ETime1 = 1;
	public static final double ETime2 = 2;
	public static final double ETime3 = 3;
	public static final double ETime4 = 4;
	public static final double ETime5 = 5;
	public static final double ETime6 = 0.75;
	
	// Intake Delay Times
	public static final double ITime1 = 1;
	public static final double ITime2 = 2;
	public static final double ITime3 = 3;
	public static final double ITime4 = 4;
	public static final double ITime5 = 5;
	
	// Outtake Delay Times
	public static final double OTime1 = 1;
	public static final double OTime2 = 1.5;
	public static final double OTime3 = 1.75;
	public static final double OTime4 = 2;
	
	// Stop Outtake Delay Times
	public static final double STime1 = 1;
	public static final double STime2 = 2;
	
	// Timed Auto
	public static final double LMiddleDrive1 = 1.25;
	public static final double LMiddleTurn1 = 0.75;
	public static final double LMiddleDrive2 = 1.25;
	public static final double LMiddleTurn2 = 1.0;
	public static final double LMiddleDrive3 = 1.125;
	public static final double LMiddleOuttake = 0.7;
	
	public static final double RMiddleDrive1 = 0.525;
	public static final double RMiddleTurn1 = 0.75;
	public static final double RMiddleDrive2 = 1.2;
	public static final double RMiddleTurn2 = 0.75;
	public static final double RMiddleDrive3 = 1.075;
	public static final double RMiddleOuttake = 0.7;
	
	public static final double ScaleDrive1 = 4;
	public static final double ScaleOuttake = 1;
	
	// Auto PID Values
	public static final double LeftP = 0.9;
	public static final double RightP = 0.9;
	public static final double LeftV = 0.289;
	public static final double RightV = 0.289;
	public static final double LeftA = 0.06;
	public static final double RightA = 0.06;
	public static final double gyroPID = -0.038;
	
}
