//Team 930!!

/* State         Pattern        ID       topStrip
 *  Disabled      Red Solid      1        toptopStrip 
 *  Enabled       Tail blue      2        toptopStrip
 *  In take       Blink green    3        toptopStrip
 *  Elevator      Height based   4        sidetopStrips
 *  Out take      Yellow Solid   5        toptopStrip
 *  Test Mode     Rainbow        6        toptopStrip
 *  Ramp down     Tail red       7        toptopStrip and sidetopStrip
 *  Ramp up       Tail green     8        toptopStrip and sidetopStrip
 *  Autonomous    Blue Solie     9        toptopStrip
 * 
 * "I regret not checking Slack..." -Mark Menning
 */ 

//-- Preprocessor Directives --\\

#include <Wire.h>
#include <Adafruit_DotStar.h>
#include <SPI.h>

//-- Type and Constant Definitions --\\

#define TOP_STRIP_NUMPIXELS 100
#define TOP_STRIP_CLOCKPIN 11
#define TOP_STRIP_DATAPIN 10 
#define BRIGHTNESS 20  
#define WAITTIME 50 

//-- Object Declarations --\\

Adafruit_DotStar topStrip = Adafruit_DotStar(TOP_STRIP_NUMPIXELS, TOP_STRIP_DATAPIN, TOP_STRIP_CLOCKPIN, DOTSTAR_BRG);

// -- Variable Declarations --\\

int patternID = 0;
int enabledPatternHead = 0;
int enabledPatternTail = -10; // Index of first 'on' and 'off' pixels

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
  if (patternID == 1) {
    disabledPattern();
  } else if (patternID == 2) {
    enabledPattern();
  } else if (patternID == 3) {
    inTakePattern();
  } else if (patternID == 5) {
    outTakePattern();
  } else if (patternID == 6) {
    testPattern();
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

//-- Pattern for Red Solide (Mark Menning)--\\

void testPattern() {

  topStrip.setBrightness(BRIGHTNESS);
  for (int i = 0; i < TOP_STRIP_NUMPIXELS; i++) { 
    topStrip.setPixelColor(i, 0, 255, 0 );
  }
  topStrip.show();
  //delay(1);
}

//-- Pattern for Blue Tail (Dominick E.)--\\

void enabledPattern(int theLength) {
  topStrip.setBrightness(BRIGHTNESS);

  /*
  topStrip.setPixelColor(enabledPatternHead, 0, 0, 255); // 'On' pixel at head
  topStrip.setPixelColor(enabledPatternTail, 0);     // 'Off' pixel at tail
  */

  for (int b = 0; b <= TOP_STRIP_NUMPIXELS; b++) {
    for (int i = 0; i < theLength; i++) {
      topStrip.setPixelColor(b + i, 0, 0, 255);
      topStrip.setPixelColor(b - 1, 0, 0, 0);
      topStrip.show();                                   
      delay(0);
    }
  }
  
                                           // Pause 10 milliseconds (~25 FPS)

  if (++enabledPatternHead >= TOP_STRIP_NUMPIXELS) {         // Increment head index.  Off end of topStrip?
    enabledPatternHead = 0;                                 //  Yes, reset head index to start
  }
  
  if (++enabledPatternTail >= TOP_STRIP_NUMPIXELS) {
    enabledPatternTail = 0; // Increment, reset tail index
  }
}

//-- Pattern for Rainbow (Trenton B.) --\\

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
    topStrip.setPixelColor(i,255,0,0);
  }
  topStrip.show();
  delay(100);
  
  for (int i = 0; i < TOP_STRIP_NUMPIXELS; i++) { 
    topStrip.setPixelColor(i,0,0,0);
  }
  topStrip.show();
  delay(100);
}

//-- Pattern for Solid Yellow (Mark Menning) --\\

void outTakePattern() {
  topStrip.setBrightness(BRIGHTNESS);
  
  for (int i = 0; i < TOP_STRIP_NUMPIXELS; i++) { 
    topStrip.setPixelColor(i, 255, 255, 0);
  }
  topStrip.show();
}

