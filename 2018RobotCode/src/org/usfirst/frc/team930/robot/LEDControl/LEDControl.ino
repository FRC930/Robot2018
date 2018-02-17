//Team 930!!

/* State         Pattern        ID       Strip
 *  Disabled      Red Solid      1        topStrip 
 *  Enabled       Tail blue      2        topStrip
 *  In take       Blink green    3        topStrip
 *  Elevator      Height based   4        sideStrips
 *  Out take      Yellow Solid   5        topStrip
 *  Test Mode     Rainbow        6        topStrip
 *  Ramp down     Tail red       7        topStrip and sideStrip
 *  Ramp up       Tail green     8        topStrip and sideStrip
 *  Autonomous    Blue Solie     9        topStrip
 * 
 * "I regret not checking Slack..." -Mark Menning
 */ 

//-- Preprocessor Directives --\\

#include <Wire.h>
#include <Adafruit_DotStar.h>
#include <SPI.h>

//-- Type and Constant Definitions --\\

#define TOP_STRIP_NUMPIXELS 119
#define TOP_STRIP_CLOCKPIN 11
#define TOP_STRIP_DATAPIN 10   
#define WAITTIME 50 

//-- Object Declarations --\\

Adafruit_DotStar topStrip = Adafruit_DotStar(TOP_STRIP_NUMPIXELS, TOP_STRIP_DATAPIN, TOP_STRIP_CLOCKPIN, DOTSTAR_BRG);

// -- Variable Declarations --\\

int patternID = 0

//-- Initializing Variables and Objects --\\

void setup() {
  Wire.begin(84);                // join i2c bus with address #84
  Wire.onReceive(receiveEvent);  // register event. Connects the receiveEvent function to the .onReceive event.
                                 // when .onReceive is triggered (when the arduino gets data), receiveEvent runs.
  Serial.begin(9600);            // start serial for output

  #if defined(__AVR_ATtiny85__) && (F_CPU == 16000000L)
    clock_prescale_set(clock_div_1); // Enable 16 MHz on Trinket
  #endif

  strip.begin(); // Initialize pins for output
  strip.show();  // Turn all LEDs off ASAP
}

//-- Main Loop --\\

void loop() {
  if (patternID == 1) {
    disabledPattern();
  } else if (patternID == 2) {
    
  }
}

//---------------------------------------- FUNCTION IMPLEMENTATIONS ----------------------------------------\\

//-- Function receiveEvent --\\

void receiveEvent(int howMany) {
  while (Wire.available()) { // loop through all but the last
    patternID = Wire.read();     // receive byte as an integer. Sets patternID to the data received. 
  }
}

//-- Pattern for Disabled --\\

void disabledPattern() {
  topStrip.setBrightness(25);
  for (int i = 0; i < NUMPIXELS; i++) { 
    topStrip.setPixelColor(i, 0, 255, 0 );
  }
  topStrip.show();
  delay(1);
}

//-- Pattern for Enabled --\\
int enabledPatternHead = 0, enabledPatternTail = -10; // Index of first 'on' and 'off' pixels
uint32_t enabledPatternColor = 0xFF0000;                            // 'On' color (starts red)

void enabledPattern() {
  topStrip.setBrightness(25);
  
  topStrip.setPixelColor(enabledPatternHead, enabledPatternColor); // 'On' pixel at head
  topStrip.setPixelColor(enabledPatternTail, 0);     // 'Off' pixel at tail
  topStrip.show();                                   // Refresh strip
  delay(20);                                         // Pause 20 milliseconds (~50 FPS)

  if (++enabledPatternHead >= TOP_STRIP_NUMPIXELS) {         // Increment head index.  Off end of strip?
    enabledPatternHead = 0;                                 //  Yes, reset head index to start
    if ((enabledPatternColor >>= 8) == 0)                                  //  Next color (B->R->G) ... past blue now?
      enabledPatternColor = 0xFF0000;                                     //   Yes, reset to red
  }
  
  if (++enabledPatternTail >= TOP_STRIP_NUMPIXELS) {
    enabledPatternTail = 0; // Increment, reset tail index
  }
}


