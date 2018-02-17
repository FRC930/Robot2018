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

void loop() {
  strip.setBrightness(35);
  for (int i = 0; i < NUMPIXELS; i += 12) { 
      strip.setPixelColor(i, 0, 255, 0 );
      strip.setPixelColor((i + 1), 0, 255, 0);
      strip.show();
      delay(50);
      strip.setPixelColor((i + 2), 0, 255, 110);
      strip.setPixelColor((i + 3), 0, 255, 110);
      strip.show();
      delay(50);
      strip.setPixelColor((i + 4), 0, 255, 255);
      strip.setPixelColor((i + 5), 0, 255, 255);
      strip.show();
      delay(50);
      strip.setPixelColor((i + 6), 7, 65, 198);
      strip.setPixelColor((i + 7), 7, 65, 198);
      strip.show();
      delay(50);
      strip.setPixelColor((i + 8), 255, 0, 0);
      strip.setPixelColor((i + 9), 255, 0, 0);
      strip.show();
      delay(50);
      strip.setPixelColor((i + 10), 255, 208, 0);
      strip.setPixelColor((i + 11), 255, 208, 0);
      strip.show();
      delay(50);
    }


    delay(2000);
    for (int i = 0; i < NUMPIXELS; i++) { 
      strip.setPixelColor(i, 0, 0, 0);
     }
    strip.show();
    delay(100);
}
