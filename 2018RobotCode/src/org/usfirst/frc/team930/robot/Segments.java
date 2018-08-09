package org.usfirst.frc.team930.robot;

/*
 *  Methods that check to make sure motion profile path is done
 */
public class Segments {
	
	/*
	 * Returns true if StartLScaleL motion profile is finished
	 */
	public boolean segStartLScaleL() {
		
		boolean isFinished = false;
		
		if(AutoHandler.mpStartLScaleL.isLastPoint()) {
			isFinished = true;
		}
		
		return isFinished;
		
	}
	
	/*
	 * Returns true if StartLScaleR motion profile is finished
	 */
	public boolean segStartLScaleR() {
		
		boolean isFinished = false;
		
		if(AutoHandler.mpStartLScaleR.isLastPoint()) {
			isFinished = true;
		}
		
		return isFinished;
		
	}
	
	/*
	 * Returns true if StartLSwitchL motion profile is finished
	 */
	public boolean segStartLSwitchL() {
		
		boolean isFinished = false;
		
		if(AutoHandler.mpStartLSwitchL.isLastPoint()) {
			isFinished = true;
		}
		
		return isFinished;
		
	}
	
	/*
	 * Returns true if StartMSwitchL motion profile is finished
	 */
	public boolean segStartMSwitchL() {
		
		boolean isFinished = false;
		
		if(AutoHandler.mpStartMSwitchL.isLastPoint()) {
			isFinished = true;
		}
		
		return isFinished;
		
	}
	
	/*
	 * Returns true if StartMSwitchR motion profile is finished
	 */
	public boolean segStartMSwitchR() {
		
		boolean isFinished = false;
		
		if(AutoHandler.mpStartMSwitchR.isLastPoint()) {
			isFinished = true;
		}
		
		return isFinished;
		
	}
	
	/*
	 * Returns true if StartRScaleL motion profile is finished
	 */
	public boolean segStartRScaleL() {
		
		boolean isFinished = false;
		
		if(AutoHandler.mpStartRScaleL.isLastPoint()) {
			isFinished = true;
		}
		
		return isFinished;
		
	}
	
	/*
	 * Returns true if StartRScaleR motion profile is finished
	 */
	public boolean segStartRScaleR() {
		
		boolean isFinished = false;
		
		if(AutoHandler.mpStartRScaleR.isLastPoint()) {
			isFinished = true;
		}
		
		return isFinished;
		
	}
	
	/*
	 * Returns true if StartRSwitchR motion profile is finished
	 */
	public boolean segStartRSwitchR() {
		
		boolean isFinished = false;
		
		if(AutoHandler.mpStartRSwitchR.isLastPoint()) {
			isFinished = true;
		}
		
		return isFinished;
		
	}
	
	/*
	 * Returns true if GyroTurn motion profile is finished
	 */
	public boolean segGyroTurn() {
		
		boolean isFinished = false;
		
		if(AutoHandler.myAutoGT.isFinished()) {
			isFinished = true;
		}
		
		return isFinished;
		
	}

}
