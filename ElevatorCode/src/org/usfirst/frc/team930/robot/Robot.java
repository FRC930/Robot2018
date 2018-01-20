package org.usfirst.frc.team930.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;



public class Robot extends TimedRobot {
	
	WPI_TalonSRX lift1 = new WPI_TalonSRX(0);
	VictorSPX lift2 = new VictorSPX(1);
	Joystick controller = new Joystick(0);
	
	boolean aPressed, onOffA, bPressed, onOffB, test1;
	
	
	private static final String kDefaultAuto = "Default";
	private static final String kCustomAuto = "My Auto";
	private String m_autoSelected;
	private SendableChooser<String> m_chooser = new SendableChooser<>();

	
	@Override
	public void robotInit() {
		m_chooser.addDefault("Default Auto", kDefaultAuto);
		m_chooser.addObject("My Auto", kCustomAuto);
		SmartDashboard.putData("Auto choices", m_chooser);
		
		
		aPressed = false;
		onOffA = false;
		bPressed = false;
		onOffB = false;
		
		lift2.set(ControlMode.Follower, 0);
		
		lift1.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 10);
		lift1.setSensorPhase(true);
		lift1.setInverted(false);
		
		lift1.configNominalOutputForward(0, 10);
		lift1.configNominalOutputReverse(0, 10);
		lift1.configPeakOutputForward(1, 10);
		lift1.configPeakOutputReverse(-1, 10);
		
		// Set PIDF values, velocity, and acceleration
		lift1.config_kF(0, 0.2, 10);
		lift1.config_kP(0, 0.2, 10);
		lift1.config_kI(0, 0, 10);
		lift1.config_kD(0, 0, 10);
		lift1.configMotionCruiseVelocity(15000, 10);	//(int sensorUnitsPer100ms, int timeoutMs)
		lift1.configMotionAcceleration(6000, 10);		//(int sensorUnitsPer100msPerSec, int timeoutMs)
	}

	
	@Override
	public void autonomousInit() {
		m_autoSelected = m_chooser.getSelected();
		// m_autoSelected = SmartDashboard.getString("Auto Selector",
		// 		kDefaultAuto);
		System.out.println("Auto selected: " + m_autoSelected);
	}

	
	@Override
	public void autonomousPeriodic() {
		switch (m_autoSelected) {
			case kCustomAuto:
				// Put custom auto code here
				break;
			case kDefaultAuto:
			default:
				// Put default auto code here
				break;
		}
	}

	
	@Override
	public void teleopPeriodic() {
		
		if(SmartDashboard.getBoolean("Update Values",false)) {
			lift1.config_kF(0, SmartDashboard.getNumber("F Value", 0.2), 10);
			lift1.config_kP(0, SmartDashboard.getNumber("P Value", 0.2), 10);
			lift1.config_kI(0, SmartDashboard.getNumber("I Value", 0.0), 10);
			lift1.config_kD(0, SmartDashboard.getNumber("D Value", 0.0), 10);
			lift1.configMotionCruiseVelocity((int) SmartDashboard.getNumber("Cruise Velocity", 15000), 10);
			lift1.configMotionAcceleration((int)SmartDashboard.getNumber("Acceleration", 6000), 10);
			System.out.println("Updated Values");
			test1 = SmartDashboard.putBoolean("Update Values", false);
		}
		
		//a pressed -- elevator up
		if(controller.getRawButton(1) && (!aPressed))
		{
			aPressed = true;
			onOffA = !onOffA;
			System.out.println("\nMotion Magic:\n");
		} else if((!controller.getRawButton(1)) && aPressed) {
			aPressed = false;
			//System.out.println("\nPercent Output:\n");
		}
		
		if (onOffA) {
			double targetPos = 4096.0;
			//double targetPos = controller.getRawAxis(1) * 4096 * 10.0; /* 4096 ticks/rev * 10 Rotations in either direction */
			lift1.set(ControlMode.MotionMagic, targetPos);
		} else {
			lift1.set(ControlMode.PercentOutput, 0);
		}
		
		//b pressed -- elevator down
		if(controller.getRawButton(2) && (!bPressed))
		{
			bPressed = true;
			onOffB = !onOffB;
			System.out.println("\nMotion Magic:\n");
		} else if((!controller.getRawButton(2)) && bPressed) {
			bPressed = false;
			//System.out.println("\nPercent Output:\n");
		}
		
		if (onOffB) {
			double targetPos = -4096.0;
			//double targetPos = controller.getRawAxis(1) * 4096 * 10.0; /* 4096 ticks/rev * 10 Rotations in either direction */
			lift1.set(ControlMode.MotionMagic, targetPos);
		} else {
			lift1.set(ControlMode.PercentOutput, 0);
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
	}


	@Override
	public void testPeriodic() {
	}
}
