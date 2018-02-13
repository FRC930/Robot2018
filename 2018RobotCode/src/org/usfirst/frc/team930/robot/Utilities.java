package org.usfirst.frc.team930.robot;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.PowerDistributionPanel;

public class Utilities {
	
	public static Compressor comp = new Compressor(0);
	public static PowerDistributionPanel pdp = new PowerDistributionPanel(0);
	
	public static void turnOnCompressor() {
		
		comp.setClosedLoopControl(true);
		
	}
	
	public static void getPDPCurrent() {
		
		pdp.getCurrent(Constants.pdpIntakePort);
		
	}

}
