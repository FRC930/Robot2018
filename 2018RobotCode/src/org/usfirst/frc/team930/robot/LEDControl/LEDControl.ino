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
#define BRIGHTNESS 30  
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

void enabledPattern() {
  topStrip.setBrightness(BRIGHTNESS);
  
  topStrip.setPixelColor(enabledPatternHead, 0, 0, 255); // 'On' pixel at head
  topStrip.setPixelColor(enabledPatternTail, 0);     // 'Off' pixel at tail

  topStrip.setPixelColor(enabledPatternHead + 20, 0, 0, 255); // 'On' pixel at head
  topStrip.setPixelColor(enabledPatternTail + 20, 0);     // 'Off' pixel at tail
  
  topStrip.show();                                   // Refresh topStrip
  delay(1);                                         // Pause 10 milliseconds (~25 FPS)

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
  for (int b = 0; b < 6; b++) {
    for (int i = 0; i < TOP_STRIP_NUMPIXELS; i++) { 
      if (i % 6 == 0) {
        topStrip.setPixelColor(i+b, 0, 255, 0 );
      } else if (i % 5 == 0) {
        topStrip.setPixelColor(i+b, 128, 255, 0 );
      } else if (i % 4 == 0) {
        topStrip.setPixelColor(i+b, 255, 255, 0 );
      } else if (i % 3 == 0) {
        topStrip.setPixelColor(i+b, 255, 0, 0 );
      } else if (i % 2 == 0) {
        topStrip.setPixelColor(i+b, 0, 0, 255);
      } else if (i % 1 == 0) {
        topStrip.setPixelColor(i+b, 0, 128, 255);
      }
    }
    topStrip.show();
    delay(50); 
  }
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

