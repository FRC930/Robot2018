#include <Adafruit_DotStar.h>
#define DATAPIN 10
#define CLOCKPIN 11
#define NUMPIXELS 100
Adafruit_DotStar strip = Adafruit_DotStar(
  NUMPIXELS, DATAPIN, CLOCKPIN, DOTSTAR_BRG);
void setup() {
strip.begin(); 
  strip.show();
}

void loop() {
 strip.setBrightness(35);
 for (int i=0; i <= NUMPIXELS; i++){
  strip.setPixelColor(i, 0, 0, 255);
 }
 strip.show();
 delay(1);
}
