//Team 930!!

/* State         Pattern                  ID       Strip
 *  Disabled      Rainbow                 1        All strips
 *  Enabled       Tail blue               2        topStrip
 *  In take       Blink green             3        topStrip
 *  Elevator      Height based            4        sideStrips
 *  Out take      Yellow Solid            5        topStrip
 *  Test Mode     Red Solid               6        All Strips (not really going to use test mode)
 *  Ramp down     Tail red                7        All strips
 *  Ramp up       Tail green              8        All strips
 *  Autonomous    Some weird pattern      9        topStrip
 * 
 * "I regret not checking Slack..." -Mark Menning
 */ 
 //Blue Wire = Datapin, Black Wire = Clockpin

//-- Preprocessor Directives --\\

#include <Wire.h>
#include <Adafruit_DotStar.h>
#include <SPI.h>
#include <math.h>

//-- Type and Constant Definitions --\\

#define TOP_STRIP_NUMPIXELS 72
#define TOP_STRIP_CLOCKPIN 9
#define TOP_STRIP_DATAPIN 8

#define RIGHT_STRIP_NUMPIXELS 50
#define RIGHT_STRIP_CLOCKPIN 7
#define RIGHT_STRIP_DATAPIN 6

#define LEFT_STRIP_NUMPIXELS 49
#define LEFT_STRIP_CLOCKPIN 5
#define LEFT_STRIP_DATAPIN 4

#define BRIGHTNESS 30  
#define WAITTIME 50 
#define ELEVATOR_MAX 8500

//-- Object Declarations --\\

Adafruit_DotStar topStrip = Adafruit_DotStar(TOP_STRIP_NUMPIXELS, TOP_STRIP_DATAPIN, TOP_STRIP_CLOCKPIN, DOTSTAR_BRG);
Adafruit_DotStar rightStrip = Adafruit_DotStar(RIGHT_STRIP_NUMPIXELS, RIGHT_STRIP_DATAPIN, RIGHT_STRIP_CLOCKPIN, DOTSTAR_BRG);
Adafruit_DotStar leftStrip = Adafruit_DotStar(LEFT_STRIP_NUMPIXELS, LEFT_STRIP_DATAPIN, LEFT_STRIP_CLOCKPIN, DOTSTAR_BRG);

// -- Variable Declarations --\\

int patternID = 0;
int counter = 0;
int maxMount = 100;

double green = 0,
red = 0,
blue = 0;

double smoothness = 0.06;
double changingBrightness = BRIGHTNESS;
double lowestBrightness = 2;

//-- Initializing Variables and Objects --\\

void setup() {
  Wire.begin(84);                // join i2c bus with address #84
  Wire.onReceive(receiveEvent);  // register event. Connects the receiveEvent function to the .onReceive event.
                                 // when .onReceive is triggered (when the arduino gets data), receiveEvent runs.
  Serial.begin(9600);            // start serial for output

  #if defined(__AVR_ATtiny85__) && (F_CPU == 16000000L)
    clock_prescale_set(clock_div_1); // Enable 16 MHz on Trinket
  #endif

  topStrip.begin(); // Initialize pins for output
  topStrip.show();  // Turn all LEDs off ASAP
}

//-- Main Loop --\\

void loop() {  
  if (1 < Wire.available()) {
    if (patternID < 1) {
      elevatorPattern(-patternID, TOP_STRIP_NUMPIXELS, TOP_STRIP_NUMPIXELS);
    } else if (patternID == 1) {
      disabledPattern();
    } else if (patternID == 2) {
      enabledPattern(5, 15, 20);
    } else if (patternID == 3) {
      inTakePattern();
    } else if (patternID == 9) {
      autonomousPattern();
    } else if (patternID == 5) {
      outTakePattern();
    } else if (patternID == 6) {
      testPattern();
    } else if (patternID == 7) {
      rampDownPattern(5, 15);
    } else if (patternID == 8) {
      rampUpPattern(5, 15);
    }
  } else {
    defaultPattern();
  } 
}

//---------------------------------------- FUNCTION IMPLEMENTATIONS ----------------------------------------\\

//-- Function receiveEvent --\\

void receiveEvent(int howMany) {
  while (1 < Wire.available()) { // loop through all but the last
    char c = Wire.read(); // receive byte as a character
  }
  int x = Wire.read();    // receive byte as an integer
  patternID = x;
}

//-- Lerp Functions, for Smooth Lights (Kyle S.) --\\

void v3Lerp(double &green, double &red, double &blue, double greenGoal, double redGoal, double blueGoal, double t) {
  green = green * (1 - t) + (greenGoal * t);
  red = red * (1 - t) + (redGoal * t);
  blue = blue * (1 - t) + (blueGoal * t);
}

double lerp(double a, double b, double t) {
  return a * (1 - t) + (b * t);
}

//-- Pattern for Red Solid (Mark Menning)--\\

void testPattern() {
  topStrip.setBrightness(BRIGHTNESS);
  for (int i = 0; i < TOP_STRIP_NUMPIXELS; i++) { 
    topStrip.setPixelColor(i, 0, 255, 0 );
  }
  topStrip.show();
  //delay(1);
}

//-- Pattern for Blue Tail (Dominick E.)--\\

void enabledPattern(int theLength, int theGap, int timeDelay) {
  topStrip.setBrightness(BRIGHTNESS);

  counter++;

  for (int b = 0; b < (TOP_STRIP_NUMPIXELS * theLength); b += (theLength * 4)) {
    
    for (int i = 0; i < theLength; i++) {
      topStrip.setPixelColor(counter + i + b, 0, 0, 255);
      topStrip.setPixelColor(counter - 1 + b, 0, 0, 0);                            
    }     
    for (int i = 0; i < theLength; i++) {
      topStrip.setPixelColor(counter + i - b, 0, 0, 255);
      topStrip.setPixelColor(counter - 1 - b, 0, 0, 0);                            
    } 

    for (int i = 0; i < theLength; i++) {
      topStrip.setPixelColor(counter + i + b + 10, 100, 255, 0);
      topStrip.setPixelColor(counter - 1 + b + 10, 0, 0, 0);                            
    } 
    for (int i = 0; i < theLength; i++) {
      topStrip.setPixelColor(counter + i - b + 10, 100, 255, 0);
      topStrip.setPixelColor(counter - 1 - b + 10, 0, 0, 0);                            
    }
  }

  topStrip.show(); 
  delay(timeDelay);

  if (counter >= (theLength * 4)) {
    counter = 0;
  }
}

//-- Pattern for Rainbow (Devin M.) --\\

void disabledPattern() {
  topStrip.setBrightness(BRIGHTNESS);

  uint16_t i, j;
  for(j = 0; j < 256 * 5; j++) { // 5 cycles of all colors on wheel
    for(i = 0; i < topStrip.numPixels(); i++) {
      topStrip.setPixelColor(i, Wheel(((i * 256 / topStrip.numPixels()) + j) & 255));
    }
    topStrip.show();
    delay(0);
  }
}

uint32_t Wheel(byte WheelPos) {
  WheelPos = 255 - WheelPos;
  if(WheelPos < 85) {
    return topStrip.Color(255 - WheelPos * 3, 0, WheelPos * 3);
  }
  if(WheelPos < 170) {
    WheelPos -= 85;
    return topStrip.Color(0, WheelPos * 3, 255 - WheelPos * 3);
  }
  WheelPos -= 170;
  return topStrip.Color(WheelPos * 3, 255 - WheelPos * 3, 0);
}

//-- Pattern for Blinking Green (Dominick E.) --\\

void inTakePattern() {
  topStrip.setBrightness(BRIGHTNESS);

  for (int i = 0; i < TOP_STRIP_NUMPIXELS; i++) {
    topStrip.setPixelColor(i, 255, 0, 0);
  }
    
  counter++;
  if (counter < 50) {
    changingBrightness = lerp(changingBrightness, BRIGHTNESS, 0.12);
  } else if (counter >= 50 && counter < 100) {
    changingBrightness = lerp(changingBrightness, 0, 0.15);
  } else {
    counter = 0;
  }
  
  topStrip.setBrightness(changingBrightness);
  topStrip.show();
  delay(1); 
}

//-- Pattern for Solid Yellow (Mark Menning) --\\

void outTakePattern() {
  topStrip.setBrightness(BRIGHTNESS);

  for (int i = 0; i < TOP_STRIP_NUMPIXELS; i++) { 
    topStrip.setPixelColor(i, 255, 255, 0);
  }
  
  topStrip.show();
}

//-- Pattern for Elevator (Kyle S.) --\\

void elevatorPattern(int elevatorPos, int strip1Pixels, int strip2Pixels) {
  topStrip.setBrightness(BRIGHTNESS);
  double stripRatio;
  int pixelHeight1,
  pixelHeight2;
  
  stripRatio = double(elevatorPos) / double(ELEVATOR_MAX);
  
  pixelHeight1 = round(stripRatio * strip1Pixels);
  pixelHeight2 = round(stripRatio * strip2Pixels);
  
  for (int i = 0; i < strip1Pixels; i++) {
    topStrip.setPixelColor(i, 0, 0, 255);
  }
  
  for (int i = 0; i < 5; i++) {
    topStrip.setPixelColor(pixelHeight1 + i, 50, 255, 0);
  }
  
  topStrip.show();
}

//-- Pattern for Default Mode (Kyle S.) --\\

void defaultPattern() {
  //topStrip.setBrightness(BRIGHTNESS);
  
  for (int i = 0; i < maxMount; i++) {
    v3Lerp(green, red, blue, 0, 255, 0, smoothness);
    for (int i = 0; i < TOP_STRIP_NUMPIXELS; i++) {
      topStrip.setPixelColor(i, green, red, blue);
    }

    if (i < 50) {
      changingBrightness = lerp(changingBrightness, BRIGHTNESS, 0.12);
    } else {
      changingBrightness = lerp(changingBrightness, lowestBrightness, 0.12);
    }
    topStrip.setBrightness(changingBrightness);
    
    topStrip.show();
    delay(1);
  }

  for (int i = 0; i < maxMount; i++) {
    v3Lerp(green, red, blue, 255, 0, 0, smoothness);
    for (int i = 0; i < TOP_STRIP_NUMPIXELS; i++) {
      topStrip.setPixelColor(i, green, red, blue);
    }

    if (i < 50) {
      changingBrightness = lerp(changingBrightness, BRIGHTNESS, 0.12);
    } else {
      changingBrightness = lerp(changingBrightness, lowestBrightness, 0.12);
    }
    topStrip.setBrightness(changingBrightness);
    
    topStrip.show();
    delay(1);
  }

  for (int i = 0; i < maxMount; i++) {
    v3Lerp(green, red, blue, 0, 0, 255, smoothness);
    for (int i = 0; i < TOP_STRIP_NUMPIXELS; i++) {
      topStrip.setPixelColor(i, green, red, blue);
    }

    if (i < 50) {
      changingBrightness = lerp(changingBrightness, BRIGHTNESS, 0.12);
    } else {
      changingBrightness = lerp(changingBrightness, lowestBrightness, 0.12);
    }
    topStrip.setBrightness(changingBrightness);
    
    topStrip.show();
    delay(1);
  }
}

//-- Pattern for Autonomous (Kyle S.) --\\

void  autonomousPattern() {
  topStrip.setBrightness(10);

  int whiteness = 0;
  int daSpeed = 1;
  
  for (int i = 0; i < TOP_STRIP_NUMPIXELS; i++) {
    topStrip.setPixelColor(i, whiteness, whiteness, whiteness);
  } 
  for (int i = 0; i < TOP_STRIP_NUMPIXELS; i++) {
    for (int z = 0; z < 15; z++) {
      topStrip.setPixelColor(i + z, 100, 255, 0); 
      topStrip.setPixelColor(TOP_STRIP_NUMPIXELS - i - z, 0, 0, 255); 
  
      if ((i + z) == (TOP_STRIP_NUMPIXELS - i - z)) {
        topStrip.setPixelColor(TOP_STRIP_NUMPIXELS - i - z, 0, 0, 0); 
      }
    }
    topStrip.setPixelColor(i - 1, whiteness, whiteness, whiteness);
    topStrip.setPixelColor(TOP_STRIP_NUMPIXELS - i + 1, whiteness, whiteness, whiteness);
    topStrip.show();
    delay(daSpeed);
  }

  for (int i = 0; i < TOP_STRIP_NUMPIXELS; i++) {
    topStrip.setPixelColor(i, whiteness, whiteness, whiteness);
  } 
  for (int i = 0; i < TOP_STRIP_NUMPIXELS; i++) {
    for (int z = 0; z < 15; z++) {
      topStrip.setPixelColor(i + z, 0, 0, 255); 
      topStrip.setPixelColor(TOP_STRIP_NUMPIXELS - i - z, 100, 255, 0); 
  
      if ((i + z) == (TOP_STRIP_NUMPIXELS - i - z)) {
        topStrip.setPixelColor(TOP_STRIP_NUMPIXELS - i - z, 0, 0, 0); 
      }
    }
    topStrip.setPixelColor(i - 1, whiteness, whiteness, whiteness);
    topStrip.setPixelColor(TOP_STRIP_NUMPIXELS - i + 1, whiteness, whiteness, whiteness);
    topStrip.show();
    delay(daSpeed);
  }
}

//-- Pattern for Ramps Up --\\

void rampUpPattern(int theLength, int timeDelay) {
  topStrip.setBrightness(BRIGHTNESS);
  counter--;
  for (int b = 0; b < (TOP_STRIP_NUMPIXELS * theLength); b += (theLength * 2)) {
    for (int i = 0; i < theLength; i++) {
      topStrip.setPixelColor(counter - i + b, 255, 0, 0);
      topStrip.setPixelColor(counter + 1 + b, 0, 0, 0);                            
    }     
    for (int i = 0; i < theLength; i++) {
      topStrip.setPixelColor(counter - i - b, 255, 0, 0);
      topStrip.setPixelColor(counter + 1 - b, 0, 0, 0);                            
    } 
  }
  topStrip.show(); 
  delay(timeDelay);
  if (counter < 0) {
    counter = TOP_STRIP_NUMPIXELS;
  }
}

//-- Pattern for Ramps Down --\\

void rampDownPattern(int theLength, int timeDelay) {
  topStrip.setBrightness(BRIGHTNESS);
  counter++;
  for (int b = 0; b < (TOP_STRIP_NUMPIXELS * theLength); b += (theLength * 2)) {
    for (int i = 0; i < theLength; i++) {
      topStrip.setPixelColor(counter + i + b, 0, 255, 0);
      topStrip.setPixelColor(counter - 1 + b, 0, 0, 0);                            
    }     
    for (int i = 0; i < theLength; i++) {
      topStrip.setPixelColor(counter + i - b, 0, 255, 0);
      topStrip.setPixelColor(counter - 1 - b, 0, 0, 0);                            
    } 
  }
  topStrip.show(); 
  delay(timeDelay);
  if (counter >= (theLength * 4)) {
    counter = 0;
  }
}

 


