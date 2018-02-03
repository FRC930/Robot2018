package org.usfirst.frc.team930.robot;

public class Constants {
	
	// Drive Constants
	
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
	public static final double scalePosition = 7000;
}
