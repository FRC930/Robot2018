
//-- Preproccessor Directives --\\
#include <Wire.h>
#include <Adafruit_DotStar.h>
// Because conditional #includes don't work w/Arduino sketches...
#include <SPI.h>         // COMMENT OUT THIS LINE FOR GEMMA OR TRINKET
//#include <avr/power.h> // ENABLE THIS LINE FOR GEMMA OR TRINKET

//-- Type and Constant Declarations --\\
#define NUMPIXELS 120 // Number of LEDs in strip
#define DATAPIN    12
#define CLOCKPIN   13

Adafruit_DotStar strip = Adafruit_DotStar(NUMPIXELS, DATAPIN, CLOCKPIN, DOTSTAR_BRG);

//-- Setup Method --\\
void setup() {
 // pinMode (13, OUTPUT);
  Wire.begin(90);                // join i2c bus with address #90
  Wire.onReceive(receiveEvent); // register event
  Serial.begin(9600);           // start serial for output
  
  #if defined(__AVR_ATtiny85__) && (F_CPU == 16000000L)
    clock_prescale_set(clock_div_1); // Enable 16 MHz on Trinket
  #endif

  strip.begin(); // Initialize pins for output
  strip.show();  // Turn all LEDs off ASAP
}

//-- Main Loop Method --\\
void loop() {
  delay(100);
}

//-- Receive Information Event Method --\\
// function that executes whenever data is received from master
// this function is registered as an event, see setup()
void receiveEvent(int howMany) {
  while ( Wire.available() > 1 ) {
    char c Wire.read();
    Serial.println(c);
  }
  int x = Wire.read();    // receive byte as an integer
  Serial.println(x);         // print the integer
}
