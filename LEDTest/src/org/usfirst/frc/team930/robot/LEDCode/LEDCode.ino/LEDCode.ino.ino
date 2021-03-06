// Wire Slave Receiver
// by Nicholas Zambetti <http://www.zambetti.com>

// Demonstrates use of the Wire library
// Receives data as an I2C/TWI slave device
// Refer to the "Wire Master Writer" example for use with this

// Created 29 March 2006

// This example code is in the public domain.

#include <Wire.h>
#include <Adafruit_DotStar.h>
#include <SPI.h>

#define NUMPIXELS 119
#define CLOCKPIN 11
#define DATAPIN 10   
#define WAITTIME 50                                                 
Adafruit_DotStar strip = Adafruit_DotStar(NUMPIXELS, DATAPIN, CLOCKPIN, DOTSTAR_BRG);
  
void setup() {
  Wire.begin(84);                // join i2c bus with address #84
  Wire.onReceive(receiveEvent); // register event
  Serial.begin(9600);           // start serial for output

  #if defined(__AVR_ATtiny85__) && (F_CPU == 16000000L)
    clock_prescale_set(clock_div_1); // Enable 16 MHz on Trinket
  #endif

  strip.begin(); // Initialize pins for output
  strip.show();  // Turn all LEDs off ASAP
}

bool aButtonDown = false;

void loop() {

  if (aButtonDown) {
    strip.setBrightness(25);
    
    for (int i = 0; i < NUMPIXELS; i++) { 
      strip.setPixelColor(i, 255, 0, 0 );
    }
    strip.show();
    delay(WAITTIME);
    
    for (int i = 0; i < NUMPIXELS; i++) { 
      strip.setPixelColor(i, 0, 0, 0);
    }
    strip.show();
    delay(WAITTIME);
  } else {
     for (int i = 0; i < NUMPIXELS; i++) { 
      strip.setPixelColor(i, 0, 0, 0);
     }
     strip.show();
     delay(1);
  }
}

// function that executes whenever data is received from master
// this function is registered as an event, see setup()
void receiveEvent(int howMany) {
  while (Wire.available()) { // loop through all but the last
    int x = Wire.read();    // receive byte as an integer
    Serial.println(x);         // print the integer
    if (x == 50) {
      aButtonDown = true;
    } else {
      aButtonDown = false;
    }
  }
}
