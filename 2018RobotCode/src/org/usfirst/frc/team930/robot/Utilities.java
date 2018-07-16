package org.usfirst.frc.team930.robot;

import org.opencv.core.Mat;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import jaci.pathfinder.Trajectory;
import jaci.pathfinder.Waypoint;

public class Utilities {
	
	public static Compressor comp = new Compressor(0);
	public static PowerDistributionPanel pdp = new PowerDistributionPanel(0);
	public static Mat source = new Mat();
	public static Mat output = new Mat();
	
	public static String hash(Waypoint[] waypoints, Trajectory.Config config) {
        String hash = "";
        for(Waypoint w: waypoints) {
            hash += ((Double.hashCode(w.x) ^ Double.hashCode(w.y)) ^ Double.hashCode(w.angle)) & 0xfffffff;
        }
        
        hash += Double.hashCode(config.dt) ^ Double.hashCode(config.max_acceleration) ^ Double.hashCode(config.max_jerk) ^ Double.hashCode(config.max_velocity) ^ Double.hashCode(config.fit.ordinal());
        
        return hash;
    }
	
	public static void setCompressor(boolean set) {
		
		comp.setClosedLoopControl(set);
		
	}
	
	public static double getPDPCurrent() {
		
		return pdp.getCurrent(Constants.pdpIntakePort);
		
	}
	
	public static void startCapture() {
		new Thread(() -> {
			UsbCamera camera = CameraServer.getInstance().startAutomaticCapture();
			camera.setResolution(Constants.cameraResWidth, Constants.cameraResHeight);
			camera.setFPS(Constants.cameraFPS);
		}).start();
	}

}
