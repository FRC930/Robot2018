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
	//used for driving
	WPI_TalonSRX rightMain = new WPI_TalonSRX(0);  //Declarations for talons
	WPI_TalonSRX leftMain = new WPI_TalonSRX(1);  //These will be the main motor controllers
	VictorSPX rightFollow = new VictorSPX(2);     //Declarations for victors that are
	VictorSPX leftFollow = new VictorSPX(3);   //followers to the talons
	VictorSPX rightFollow2 = new VictorSPX(4);     //Declarations for victors that are
	VictorSPX leftFollow2 = new VictorSPX(5);   //followers to the talons
	
	DifferentialDrive robot = new DifferentialDrive(leftMain, rightMain);  //Declares the driving method control for robot
	
	
	//used for elevator
	WPI_TalonSRX lift1 = new WPI_TalonSRX(6);
	//VictorSPX lift2 = new VictorSPX(1);
	
	
	//used for intake
	VictorSPX RWheel = new VictorSPX(7);
	VictorSPX LWheel = new VictorSPX(8);

	//Solenoid SolenoidRight = new Solenoid(9);
	//Solenoid SolenoidLeft = new Solenoid(10); 
	
	//PowerDistributionPanel PDP = new PowerDistributionPanel();

	double currentThreshhold;
	double timeDelay;
	
	
	Joystick controller = new Joystick(0);
	Joystick controller2 = new Joystick(1);
	
	boolean aPressed, onOffA, bPressed, onOffB, test1;
	double targetPosition, fValue, pValue, iValue, dValue;
	int velocity, acceleration;
	
	@Override
	public void robotInit() {		
		
		//used for elevator
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
		
		//used for driving
		rightFollow.follow(rightMain);   //Sets the victors to follow their 
		leftFollow.follow(leftMain);   //respective talons
		rightFollow2.follow(rightMain);   //Sets the victors to follow their 
		leftFollow2.follow(leftMain);   //respective talons
		robot.setQuickStopThreshold(0.1);

		currentThreshhold = 26.5;
		timeDelay = 1.0;
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
		
		
		//left stick Y axis controller 2 -- elevator up and down
		if(controller2.getRawAxis(1) > 0.2 || controller2.getRawAxis(1) < -0.2)
		{
			lift1.set(ControlMode.PercentOutput, -0.3 * (controller2.getRawAxis(1)));
		}
		else
		{
			lift1.set(ControlMode.PercentOutput, 0);
		}
				
		/*		
		if(controller.getRawAxis(1) < 0.02)
			check = true;
		else                      //Tells the robot when to do a quick turn
			check = false;*/
				
		//robot.curvatureDrive(leftYStick, rightXStick, false);  //sends the values to the drivetrain
		robot.arcadeDrive(leftYStick, -rightXStick);
		

		// Y Button -- reverse the intake
		if (controller2.getRawButton(4)) {
			RWheel.set(ControlMode.PercentOutput, 0.75); // running motor
			LWheel.set(ControlMode.PercentOutput, -0.75); // running motor
			/*if (PDP.getCurrent(11) > currentThreshhold) {
				System.out.println("check one sucessfull");
				Timer.delay(timeDelay);
				if (PDP.getCurrent(11) > currentThreshhold) {
					System.out.println("check two sucessfull");
					//SolenoidRight.set(false);
					//SolenoidLeft.set(false);
					RWheel.set(ControlMode.PercentOutput, 0);
					LWheel.set(ControlMode.PercentOutput,0);

				}
			}*/
		//A button -- intake
		} else if(controller2.getRawButton(1)) {
			RWheel.set(ControlMode.PercentOutput, -0.75); // running motor
			LWheel.set(ControlMode.PercentOutput, 0.75); // running motor
		} else {
			//SolenoidRight.set(true);
			//SolenoidLeft.set(true);
			RWheel.set(ControlMode.PercentOutput, 0);
			LWheel.set(ControlMode.PercentOutput, 0);
		}
		
		
				
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
