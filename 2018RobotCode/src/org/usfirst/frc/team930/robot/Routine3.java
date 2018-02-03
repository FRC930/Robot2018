package org.usfirst.frc.team930.robot;

public class Routine3 extends Routine{
	
	public Routine3(String v) {
		
		super(v);
		
	}
	
	public void variation1() {
		
		switch (this.segment) {
			case 1:
				if(segList.seg1())	{
					this.segment = 2;
					System.out.println("Segment 1 Done");
				}
				break;
			case 2:
				if(actList.act1()) {
					this.segment = 3;
					System.out.println("Action 1 Done");
				}
				break;
			case 3:
				System.out.println("DONE");
				break;
		}
		
	}
	
	public void variation2() {
		
		switch (this.segment) {
			case 1:
				if(segList.seg1())	{
					this.segment = 2;
					System.out.println("Segment 1 Done");
				}
				break;
			case 2:
				if(actList.act1()) {
					this.segment = 3;
					System.out.println("Action 1 Done");
				}
				break;
			case 3:
				break;
		}
		
	}

	public void variation3() {
		
		switch (this.segment) {
			case 1:
				if(segList.seg1())	{
					this.segment = 2;
					System.out.println("Segment 1 Done");
				}
				break;
			case 2:
				if(actList.act1()) {
					this.segment = 3;
					System.out.println("Action 1 Done");
				}
				break;
			case 3:
				break;
		}
		
	}

	public void variation4() {
		
		switch (this.segment) {
			case 1:
				if(segList.seg1())	{
					this.segment = 2;
					System.out.println("Segment 1 Done");
				}
				break;
			case 2:
				if(actList.act1()) {
					this.segment = 3;
					System.out.println("Action 1 Done");
				}
				break;
			case 3:
				break;
		}
		
	}

}

