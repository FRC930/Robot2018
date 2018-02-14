
package org.usfirst.frc.team930.robot;

import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;

public class Robot extends IterativeRobot {
	private static I2C commPort = new I2C (I2C.Port.kOnboard, 8);
	private static Joystick controller = new Joystick(0);
	private static byte sendData [];
	
	@Override
	public void robotInit() {
		sendData = new byte [1];
	}

	@Override
	public void autonomousInit() {
	}

	@Override
	public void autonomousPeriodic() {
	}

	@Override
	public void teleopPeriodic() {
		if (controller.getRawButton(1) ) {
			sendData [0] = 72; 
		} else {
			sendData [0] = 76;
		}
		
		commPort.transaction(sendData, 1, null, 0) ;
	}
	
	@Override
	public void testPeriodic() {
	}
}
