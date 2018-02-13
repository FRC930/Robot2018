package org.usfirst.frc.team930.robot;

public class RightRightScale extends Routine {
	
	public RightRightScale(String v) {
		
		super(v);
		
	}

	public void variation() {
		
		switch (this.autoStep) {
			case 1:
				if(segList.seg1())	{
					this.autoStep = 2;
					System.out.println("Segment 1 Done");
				}
				break;
			case 2:
				if(actList.act1()) {
					this.autoStep = 3;
					System.out.println("Action 1 Done");
				}
				break;
			case 3:
				System.out.println("DONE");
				break;
		}
		
	}

}
