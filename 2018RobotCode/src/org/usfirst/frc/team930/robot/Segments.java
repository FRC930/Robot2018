package org.usfirst.frc.team930.robot;

public class Segments {
	
	public boolean segStartLScaleL() {
		
		boolean isFinished = false;
		
		if(AutoHandler.mpStartLScaleL.isLastPoint()) {
			isFinished = true;
		}
		
		return isFinished;
		
	}
	
	public boolean segStartLScaleR() {
		
		boolean isFinished = false;
		
		if(AutoHandler.mpStartLScaleR.isLastPoint()) {
			isFinished = true;
		}
		
		return isFinished;
		
	}
	
	public boolean segStartLSwitchL() {
		
		boolean isFinished = false;
		
		if(AutoHandler.mpStartLSwitchL.isLastPoint()) {
			isFinished = true;
		}
		
		return isFinished;
		
	}
	
	public boolean segStartMSwitchL() {
		
		boolean isFinished = false;
		
		if(AutoHandler.mpStartMSwitchL.isLastPoint()) {
			isFinished = true;
		}
		
		return isFinished;
		
	}
	
	public boolean segStartMSwitchR() {
		
		boolean isFinished = false;
		
		if(AutoHandler.mpStartMSwitchR.isLastPoint()) {
			isFinished = true;
		}
		
		return isFinished;
		
	}
	
	public boolean segStartRScaleL() {
		
		boolean isFinished = false;
		
		if(AutoHandler.mpStartRScaleL.isLastPoint()) {
			isFinished = true;
		}
		
		return isFinished;
		
	}
	
	public boolean segStartRScaleR() {
		
		boolean isFinished = false;
		
		if(AutoHandler.mpStartRScaleR.isLastPoint()) {
			isFinished = true;
		}
		
		return isFinished;
		
	}
	
	public boolean segStartRSwitchR() {
		
		boolean isFinished = false;
		
		if(AutoHandler.mpStartRSwitchR.isLastPoint()) {
			isFinished = true;
		}
		
		return isFinished;
		
	}
	
	public boolean segGyroTurn() {
		
		boolean isFinished = false;
		
		if(AutoHandler.myAutoGT.isFinished()) {
			isFinished = true;
		}
		
		return isFinished;
		
	}

}
