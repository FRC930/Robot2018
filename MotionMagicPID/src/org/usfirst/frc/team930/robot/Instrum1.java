package org.usfirst.frc.team930.robot;


import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Instrum1 {

	private static int _loops = 0;
	private static int _timesInMotionMagic = 0;
	
	public static void Process(TalonSRX tal, StringBuilder sb)
	{
		/* smart dash plots */
		SmartDashboard.putNumber("SensorVel", tal.getSelectedSensorVelocity(Constants2.kPIDLoopIdx));
		SmartDashboard.putNumber("SensorPos", tal.getSelectedSensorPosition(Constants2.kPIDLoopIdx));
		SmartDashboard.putNumber("MotorOutputPercent", tal.getMotorOutputPercent());
		SmartDashboard.putNumber("ClosedLoopError", tal.getClosedLoopError(Constants2.kPIDLoopIdx));
		//SmartDashboard.putNumber("ClosedLoopTarget", tal.getClosedLoopTarget(Constants.kPIDLoopIdx));
		
		/* check if we are motion-magic-ing */
		if (tal.getControlMode() == ControlMode.MotionMagic) {
			++_timesInMotionMagic;
		} else {
			_timesInMotionMagic = 0;
		}
		if (_timesInMotionMagic > 10) {
			/* print the Active Trajectory Point Motion Magic is servoing towards */
    		SmartDashboard.putNumber("ActTrajVelocity", tal.getActiveTrajectoryVelocity());
    		SmartDashboard.putNumber("ActTrajPosition", tal.getActiveTrajectoryPosition());
    		SmartDashboard.putNumber("ActTrajHeading", tal.getActiveTrajectoryHeading());
		}
		/* periodically print to console */
		if (++_loops >= 10) {
			_loops = 0;
			System.out.println(sb.toString());
		}
		/* clear line cache */
		sb.setLength(0);
	}
}
