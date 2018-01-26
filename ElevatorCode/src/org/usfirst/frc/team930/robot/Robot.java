package org.usfirst.frc.team930.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.StatusFrameEnhanced;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
//import com.ctre.phoenix.motorcontrol.can.VictorSPX;
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
	timeDelay, 					//Delay time to check the PDP AMP rate and for out take.
	intakeMotorSpeed;			//Speed of intake motors.
	boolean holdingCube;		//Check for cube.
	
	
	boolean aPressed, onOffA, bPressed, onOffB, test1;
	double targetPosition, fValue, pValue, iValue, dValue;
	int velocity, acceleration;
	
	@Override
	public void robotInit() {		
		
		//-- Variable initializations used for elevator --\\
		// Set PIDF values, velocity, acceleration, and target position
		fValue = 0.2;
		pValue = 0.2;
		iValue = 0.0;
		dValue = 0.0;
		velocity = 6000;
		acceleration = 15000;
		targetPosition = 9000.0;
		// ------------------------------------------------------------
		
		
		aPressed = false;
		onOffA = false;
		bPressed = false;
		onOffB = false;
		
		//lift2.set(ControlMode.Follower, 0);
		/*
		lift1.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 10);
		lift1.setSensorPhase(true);
		lift1.setInverted(false);
		
		lift1.setStatusFramePeriod(StatusFrameEnhanced.Status_13_Base_PIDF0, 10, 10);
		lift1.setStatusFramePeriod(StatusFrameEnhanced.Status_10_MotionMagic, 10, 10);
		
		lift1.configNominalOutputForward(0, 10);
		lift1.configNominalOutputReverse(0, 10);
		lift1.configPeakOutputForward(1, 10);
		lift1.configPeakOutputReverse(-1, 10);
		
		lift1.selectProfileSlot(0, 0);
		lift1.config_kF(0, fValue, 10);
		lift1.config_kP(0, pValue, 10);
		lift1.config_kI(0, iValue, 10);
		lift1.config_kD(0, dValue, 10);
		lift1.configMotionCruiseVelocity(velocity, 10);
		lift1.configMotionAcceleration(acceleration, 10);
		lift1.setSelectedSensorPosition(0, 0, 10);
		*/
		
		//-- Used for driving --\\
		rightFollow.follow(rightMain);   //Sets the victors to follow their 
		leftFollow.follow(leftMain);   //respective talons
		rightFollow2.follow(rightMain);   //Sets the victors to follow their 
		leftFollow2.follow(leftMain);   //respective talons
		robot.setQuickStopThreshold(0.1);

		
		//-- Intake Variable Initializations --\\
		holdingCube = false;
		currentThreshhold = 26.5;
		timeDelay = 0.5;
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
		
		
		//-- Basic Elevator Code for Testing --\\
		//left stick Y axis controller 2 -- elevator up and down at 30 percent speed
		if(controller2.getRawAxis(1) > 0.2 || controller2.getRawAxis(1) < -0.2) {
			lift1.set(ControlMode.PercentOutput, -0.3 * (controller2.getRawAxis(1)));
		}
		else {
			lift1.set(ControlMode.PercentOutput, 0);
		}
				
		/*		
		if(controller.getRawAxis(1) < 0.02)
			check = true;
		else                      //Tells the robot when to do a quick turn
			check = false;*/
				
		//robot.curvatureDrive(leftYStick, rightXStick, false);  //sends the values to the drivetrain
		robot.arcadeDrive(leftYStick, -rightXStick);
		
		
		//-- Basic Intake Code for Testing --\\
		// Y Button -- reverse the intake
		if (controller2.getRawButton(4)) {
			rightIntakeWheel.set(ControlMode.PercentOutput, 0.75); // running motor
			leftIntakeWheel.set(ControlMode.PercentOutput, -0.75); // running motor
		//A button -- intake
		} else if(controller2.getRawButton(1)) {
			rightIntakeWheel.set(ControlMode.PercentOutput, -0.75); // running motor
			leftIntakeWheel.set(ControlMode.PercentOutput, 0.75); // running motor
			System.out.println(PDP.getCurrent(11));
		} else {
			//SolenoidRight.set(true);
			//SolenoidLeft.set(true);
			rightIntakeWheel.set(ControlMode.PercentOutput, 0);
			leftIntakeWheel.set(ControlMode.PercentOutput, 0);
		}
		
		
		//-- Intake Code Block --\\
		/*if(controller.getRawButton(1)) {												//If the A button is down.
			if (holdingCube == false) {													//If we're not holding a cube.
				rightIntakeWheel.set(ControlMode.PercentOutput, -intakeMotorSpeed); 	//Turn right motor backwards.
				leftIntakeWheel.set(ControlMode.PercentOutput, intakeMotorSpeed); 		//Turn left motor forwards.
				if(PDP.getCurrent(11) > currentThreshhold) {							//If the PDP current on channel 11 is over currentThreshhold of amps.
					Timer.delay(timeDelay);												//Wait
					if(PDP.getCurrent(11) > currentThreshhold) {						//If the PDP current on channel 11 is STILL over currentThreshhold of amps.
						holdingCube = true;												//Holding cube is true.	
						rightIntakeWheel.set(ControlMode.PercentOutput, 0);				//Stop motors
						leftIntakeWheel.set(ControlMode.PercentOutput, 0);
						rightSolenoid.set(false);										//Close pistons.
						leftSolenoid.set(false);
					}
				}
			} else {																	//Else if holding cube.
				rightIntakeWheel.set(ControlMode.PercentOutput, intakeMotorSpeed); 		//Turn right motor forwards.
				leftIntakeWheel.set(ControlMode.PercentOutput, -intakeMotorSpeed);		//Turn left motor backwards.
				rightSolenoid.set(true);												//Open pistons.
				leftSolenoid.set(true);
				Timer.delay(timeDelay);													//Wait for cube to leave.
				holdingCube = false;													//No longer holding cube.
			}
		} else {																		//If A button is not down.
			rightIntakeWheel.set(ControlMode.PercentOutput, 0);							//Stop motors
			leftIntakeWheel.set(ControlMode.PercentOutput, 0);
		}*/
		
		
				
		/*
		//a pressed -- elevator up
		if(controller.getRawButton(1) && (!aPressed))
		{
			aPressed = true;
			onOffA = !onOffA;
		} else if((!controller.getRawButton(1)) && aPressed) {
			aPressed = false;
		}
		
		if (onOffA) {
			double targetPos = targetPosition;
			lift1.set(ControlMode.MotionMagic, targetPos);
			onOffA = false;
		} else {
			lift1.set(ControlMode.PercentOutput, 0);
		}
		
		//b pressed -- elevator down
		if(controller.getRawButton(2) && (!bPressed))
		{
			bPressed = true;
			onOffB = !onOffB;
		} else if((!controller.getRawButton(2)) && bPressed) {
			bPressed = false;
		}
		
		if (onOffB) {
			double targetPos = targetPosition * -1.0;
			lift1.set(ControlMode.MotionMagic, targetPos);
			onOffB = false;
		} else {
			lift1.set(ControlMode.PercentOutput, 0);
		}
		*/
		
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
	}


	@Override
	public void testPeriodic() {
	}
}
