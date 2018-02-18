#include <Wire.h>
#include <Adafruit_DotStar.h>
#include <SPI.h>

#define NUMPIXELS 100
#define CLOCKPIN 11
#define DATAPIN 10   
Adafruit_DotStar strip = Adafruit_DotStar(NUMPIXELS, DATAPIN, CLOCKPIN, DOTSTAR_BRG);

void setup() {
  Serial.begin(9600);
  strip.begin();
  strip.show();
}

int sine = 0;
int range = 10;
int shift = 50;
int lengtha = 10;

void loop() {
  strip.setBrightness(35);

  sine++;
  
  strip.setPixelColor(int(sin(sine/6) * range) + shift, 0, 255, 0);
  for (int i = 1; i < lengtha; i++) {
    strip.setPixelColor(int(sin(sine/6) * range) + shift - i, 0, 255, 0);
    strip.setPixelColor(int(sin(sine/6) * range) + shift + i, 0, 255, 0);
  }
  strip.show();
  
  delay(1);
  
  
  strip.setPixelColor(int(sin(sine/6) * range) + shift, 0, 0, 0);
  for (int i = 1; i < lengtha; i++) {
    strip.setPixelColor(int(sin(sine/6) * range) + shift - i, 0, 0, 0);
    strip.setPixelColor(int(sin(sine/6) * range) + shift + i, 0, 0, 0);
  }
  strip.show();
  delay(1);
}



