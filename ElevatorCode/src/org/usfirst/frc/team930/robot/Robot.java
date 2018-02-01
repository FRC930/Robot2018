package org.usfirst.frc.team930.robot;


import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.StatusFrameEnhanced;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;



public class Robot extends TimedRobot {
	//-- Joystick Object Declarations and Instantiations --\\
	Joystick controller = new Joystick(0);
	Joystick controller2 = new Joystick(1);
	
	
	//-- Driving Object Declarations and Instantiations --\\
	WPI_TalonSRX rightMain = new WPI_TalonSRX(0);  //Declarations for talons
	WPI_TalonSRX leftMain = new WPI_TalonSRX(1);  //These will be the main motor controllers
	VictorSPX rightFollow = new VictorSPX(2);     //Declarations for victors that are
	VictorSPX leftFollow = new VictorSPX(3);   //followers to the talons
	VictorSPX rightFollow2 = new VictorSPX(4);     //Declarations for victors that are
	VictorSPX leftFollow2 = new VictorSPX(5);   //followers to the talons
	DifferentialDrive robot = new DifferentialDrive(leftMain, rightMain);  //Declares the driving method control for robot
	
	
	//-- Elevator Object Declarations and Instantiations --\\
	WPI_TalonSRX lift1 = new WPI_TalonSRX(6);
	//VictorSPX lift2 = new VictorSPX(1);
	
	
	//-- Intake Object Declarations and Instantiations--\\
	VictorSPX rightIntakeWheel = new VictorSPX(7);
	VictorSPX leftIntakeWheel = new VictorSPX(8);
	Solenoid rightSolenoid = new Solenoid(9);
	Solenoid leftSolenoid = new Solenoid(10);  
	PowerDistributionPanel PDP = new PowerDistributionPanel();

	
	//-- Intake Variable Declarations --\\
	double currentThreshhold,	//Threshhold for the current of channel 11 (in AMPs).
	intakeMotorSpeed;			//Speed of intake motors.
	boolean holdingCube;		//Check for cube.
	
	
	boolean aPressed, onOffA, bPressed, onOffB, test1;
	double targetPosition, fValue, pValue, iValue, dValue;
	int velocity, acceleration;
	
	@Override
	public void robotInit() {		
		// Motion Magic Initialization
		/* first choose the sensor */
		lift1.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, Constants1.kPIDLoopIdx, Constants1.kTimeoutMs);
		lift1.setSensorPhase(false);
		lift1.setInverted(false);

		/* Set relevant frame periods to be at least as fast as periodic rate */
		lift1.setStatusFramePeriod(StatusFrameEnhanced.Status_13_Base_PIDF0, 10, Constants1.kTimeoutMs);
		lift1.setStatusFramePeriod(StatusFrameEnhanced.Status_10_MotionMagic, 10, Constants1.kTimeoutMs);

		/* set the peak and nominal outputs */
		lift1.configNominalOutputForward(0, Constants1.kTimeoutMs);
		lift1.configNominalOutputReverse(0, Constants1.kTimeoutMs);
		lift1.configPeakOutputForward(1, Constants1.kTimeoutMs);
		lift1.configPeakOutputReverse(-1, Constants1.kTimeoutMs);
		
		lift1.configForwardSoftLimitThreshold(7000, Constants1.kTimeoutMs);
		lift1.configReverseSoftLimitThreshold(0, Constants1.kTimeoutMs);

		/* set closed loop gains in slot0 - see documentation */
		lift1.selectProfileSlot(Constants1.kSlotIdx, Constants1.kPIDLoopIdx);
		lift1.config_kF(0, 1.89, Constants1.kTimeoutMs);
		lift1.config_kP(0, 0.5, Constants1.kTimeoutMs);
		lift1.config_kI(0, 0, Constants1.kTimeoutMs);
		lift1.config_kD(0, 10, Constants1.kTimeoutMs);
		/* set acceleration and vcruise velocity - see documentation */
		lift1.configMotionCruiseVelocity(800, Constants1.kTimeoutMs);
		lift1.configMotionAcceleration(800, Constants1.kTimeoutMs);
		/* zero the sensor */
		lift1.setSelectedSensorPosition(0, Constants1.kPIDLoopIdx, Constants1.kTimeoutMs);
		
		//-------------------------------
		
		aPressed = false;
		onOffA = false;
		bPressed = false;
		onOffB = false;
		
		//-- Used for driving --\\
		rightFollow.follow(rightMain);   //Sets the victors to follow their 
		leftFollow.follow(leftMain);   //respective talons
		rightFollow2.follow(rightMain);   //Sets the victors to follow their 
		leftFollow2.follow(leftMain);   //respective talons
		robot.setQuickStopThreshold(0.1);

		
		//-- In take Variable Initializations --\\
		holdingCube = false;
		currentThreshhold = 25.0;
		intakeMotorSpeed = 0.75;
	}

	
	@Override
	public void autonomousInit() {
	}

	
	@Override
	public void autonomousPeriodic() {
	}

	
	@Override
	public void teleopPeriodic() {
		//boolean check;  //Value to do the quick turn or not 
		
		double rightXStick = controller.getRawAxis(4); //Right joystick X axis
		double leftYStick = controller.getRawAxis(1); //Left joystick Y axis
		
		robot.setDeadband(0.1);  //Sets the deadband for the controller values
		
				
		/*		
		if(controller.getRawAxis(1) < 0.02)
			check = true;
		else                      //Tells the robot when to do a quick turn
			check = false;*/
				
		//robot.curvatureDrive(leftYStick, rightXStick, false);  //sends the values to the drivetrain
		robot.arcadeDrive(leftYStick, -rightXStick);
		
		
		//-- Basic Manual In take Code for Testing --\\
		
		if(controller.getRawAxis(3) > 0.7) {									//If the RT button is down																		
			rightIntakeWheel.set(ControlMode.PercentOutput, -intakeMotorSpeed); //Turn on motors
			leftIntakeWheel.set(ControlMode.PercentOutput, intakeMotorSpeed); 		
		} else {																//If the RT button is up
			rightIntakeWheel.set(ControlMode.PercentOutput, 0);					//Stop motors						
			leftIntakeWheel.set(ControlMode.PercentOutput, 0);
		}
		
		if (controller.getRawAxis(2) > 0.7) {									//If Left Shoulder Button is down and we have a cube.											
			rightIntakeWheel.set(ControlMode.PercentOutput, intakeMotorSpeed); 	//Turn right motor forwards.
			leftIntakeWheel.set(ControlMode.PercentOutput, -intakeMotorSpeed);	//Turn left motor backwards.											
		} else {																//If the LT button is up
			rightIntakeWheel.set(ControlMode.PercentOutput, 0);					//Stop motors						
			leftIntakeWheel.set(ControlMode.PercentOutput, 0);
		}
		
		
		/* a pressed -- elevator up
		if(controller.getRawButton(1) && (!aPressed))
		{
			aPressed = true;
			onOffA = !onOffA;
		} else if((!controller.getRawButton(1)) && aPressed) {
			aPressed = false;
		}
		
		if(onOffA) {
			lift1.set(ControlMode.PercentOutput, 1);
		} else {
			lift1.set(ControlMode.PercentOutput, 0);
		}
		
		
		// b pressed -- elevator down
		if(controller.getRawButton(2) && (!bPressed))
		{
			bPressed = true;
			onOffB = !onOffB;
		} else if((!controller.getRawButton(2)) && bPressed) {
			bPressed = false;
		}
				
		if(onOffB) {
			lift1.set(ControlMode.PercentOutput, -1);
		} else {
			lift1.set(ControlMode.PercentOutput, 0);
		}*/
		
		if (controller2.getRawButton(1)) {									//A pressed -- elevator down
			lift1.set(ControlMode.MotionMagic, Constants1.intakePosition);
		} else if (controller2.getRawButton(2)) {							//B pressed -- elevator to switch position
			lift1.set(ControlMode.MotionMagic, Constants1.switchPosition);
		} else if (controller2.getRawButton(3)) {							//X pressed -- elevator to scale position
			lift1.set(ControlMode.MotionMagic, Constants1.scalePosition);
		} else {
			if(controller2.getRawAxis(5) > 0.2 || controller2.getRawAxis(5) < -0.2) {
				lift1.set(ControlMode.PercentOutput, -0.3 * (controller2.getRawAxis(5)));
			}
			else {
				lift1.set(ControlMode.PercentOutput, 0);
			}
		}
		
		/*
		if (controller2.getRawButton(6)) {
			// Motion Magic - 4096 ticks/rev * 10 Rotations in either direction 
			lift1.set(ControlMode.MotionMagic, 7000);
		} else if (controller2.getRawButton(5)) {
			lift1.set(ControlMode.MotionMagic, 10);
		} else {
			if(controller2.getRawAxis(5) > 0.2 || controller2.getRawAxis(5) < -0.2) {
				lift1.set(ControlMode.PercentOutput, -0.3 * (controller2.getRawAxis(5)));
			}
			else {
				lift1.set(ControlMode.PercentOutput, 0);
			}
		}*/
	}

	public class Constants1 {
		public static final int kSlotIdx = 0;
		public static final int kPIDLoopIdx = 0;
		public static final int kTimeoutMs = 10;
		public static final double intakePosition = 0;
		public static final double switchPosition = 2000;
		public static final double scalePosition = 7000;
	}
	
	@Override
	public void testPeriodic() {
	}
}
