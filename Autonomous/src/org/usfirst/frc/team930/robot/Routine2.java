package org.usfirst.frc.team930.robot;

public class Routine2 extends Routine {
	
	private static String variation;
	private static int segment = 1;
	
	public Routine2(String v) {
		
		super(v);
		
	}
	
	public void run() {
		
		switch (variation) {
			case "LRL":
				variation1();
				break;
			case "RLR":
				variation2();
				break;
			case "LLL":
				variation3();
				break;
			case "RRR":
				variation4();
				break;
		}
		
	}
	
	public static void variation1() {
		
		switch (segment) {
			case 1:
				if(Actions.act1())
					segment = 2;
				break;
			case 2:
				if(Segments.seg1())
					segment = 3;
				break;
		}
	}
	
	public static void variation2() {
		
		switch (segment) {
			case 1:
				if(Actions.act1())
					segment = 2;
				break;
			case 2:
				if(Segments.seg1())
					segment = 3;
				break;
		}
	}

	public static void variation3() {
		
		switch (segment) {
			case 1:
				if(Actions.act1())
					segment = 2;
				break;
			case 2:
				if(Segments.seg1())
					segment = 3;
				break;
		}
	}

	public static void variation4() {
		
		switch (segment) {
			case 1:
				if(Actions.act1())
					segment = 2;
				break;
			case 2:
				if(Segments.seg1())
					segment = 3;
				break;
		}
	}

}
