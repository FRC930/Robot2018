package org.usfirst.frc.team930.robot;

import edu.wpi.first.wpilibj.TimedRobot;
//import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
//import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.Joystick;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

public class Robot extends TimedRobot {
	/*private static final String kDefaultAuto = "Default";
	private static final String kCustomAuto = "My Auto";
	private String m_autoSelected;
	private SendableChooser<String> m_chooser = new SendableChooser<>(); */
	
	TalonSRX motor1 = new TalonSRX(0);
	Joystick controller = new Joystick(0);
	
	@Override
	public void robotInit() {
		/*m_chooser.addDefault("Default Auto", kDefaultAuto);
		m_chooser.addObject("My Auto", kCustomAuto);
		SmartDashboard.putData("Auto choices", m_chooser);*/
		
		motor1.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 10);
		motor1.setSensorPhase(true);
		motor1.setInverted(false);
		
		motor1.configNominalOutputForward(0, 10);
		motor1.configNominalOutputReverse(0, 10);
		motor1.configPeakOutputForward(1, 10);
		motor1.configPeakOutputReverse(-1, 10);
		
		// Set PIDF values, velocity, and acceleration
		motor1.config_kF(0, 0.2, 10);
		motor1.config_kP(0, 0.2, 10);
		motor1.config_kI(0, 0, 10);
		motor1.config_kD(0, 0, 10);
		motor1.configMotionCruiseVelocity(15000, 10);
		motor1.configMotionAcceleration(6000, 10);
	}

	
	@Override
	public void autonomousInit() {
		/*m_autoSelected = m_chooser.getSelected();
		// m_autoSelected = SmartDashboard.getString("Auto Selector",
		// 		kDefaultAuto);
		System.out.println("Auto selected: " + m_autoSelected);*/
		
		
	}

	
	@Override
	public void autonomousPeriodic() {
		/*switch (m_autoSelected) {
			case kCustomAuto:
				// Put custom auto code here
				break;
			case kDefaultAuto:
			default:
				// Put default auto code here
				break;
		}*/
		
		
	}

	
	@Override
	public void teleopPeriodic() {
		System.out.println(motor1.getLastError());
		
		if (controller.getRawButton(1)) {
			double targetPos = controller.getRawAxis(1) * 4096 * 10.0; /* 4096 ticks/rev * 10 Rotations in either direction */
			motor1.set(ControlMode.MotionMagic, targetPos);
		} else {
			if (controller.getRawAxis(1) > 0.2 || controller.getRawAxis(1) < -0.2) {
				motor1.set(ControlMode.PercentOutput, controller.getRawAxis(1));
			} else {
				motor1.set(ControlMode.PercentOutput, 0);
			}
		}
	}

	
	@Override
	public void testPeriodic() {
	}
}
