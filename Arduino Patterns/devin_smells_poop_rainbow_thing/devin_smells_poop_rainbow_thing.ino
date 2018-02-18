#include <Adafruit_DotStar.h>
#include <SPI.h>

#define TOP_STRIP_NUMPIXELS 100
#define TOP_STRIP_CLOCKPIN 11
#define TOP_STRIP_DATAPIN 10 
#define BRIGHTNESS 20  
#define WAITTIME 50 

Adafruit_DotStar topStrip = Adafruit_DotStar(TOP_STRIP_NUMPIXELS, TOP_STRIP_DATAPIN, TOP_STRIP_CLOCKPIN, DOTSTAR_BRG);

void setup() {
  topStrip.begin();
  topStrip.show(); // Initialize all pixels to 'off'
}
void loop() {
  topStrip.setBrightness(20);
  colorWipe(topStrip.Color(255, 0, 0), 50); // Red
  colorWipe(topStrip.Color(0, 255, 0), 50); // Green
  colorWipe(topStrip.Color(0, 0, 255), 50); // Blue
  colorWipe(topStrip.Color(255, 255, 255), 50); // White RGBW
  
  theaterChase(topStrip.Color(127, 127, 127), 50); // White
  theaterChase(topStrip.Color(127, 0, 0), 50); // Red
  theaterChase(topStrip.Color(0, 0, 127), 50); // Blue
  rainbow(20);
  rainbowCycle(20);
  theaterChaseRainbow(50);
}
// Fill the dots one after the other with a color
void colorWipe(uint32_t c, uint8_t wait) {
  for(uint16_t i = 0; i < topStrip.numPixels(); i++) {
    topStrip.setPixelColor(i, c);

    topStrip.show();

    delay(wait);
  }
}
void rainbow(uint8_t wait) {
  uint16_t i, j;
  for(j = 0; j < 256; j++) {
    for(i = 0; i < topStrip.numPixels(); i++) {
      topStrip.setPixelColor(i, Wheel((i + j) & 255));
    }
    topStrip.show();
    delay(wait);
  }
}
// Slightly different, this makes the rainbow equally distributed throughout
void rainbowCycle(uint8_t wait) {
  uint16_t i, j;
  for(j = 0; j < 256 * 5; j++) { // 5 cycles of all colors on wheel
    for(i = 0; i < topStrip.numPixels(); i++) {
      topStrip.setPixelColor(i, Wheel(((i * 256 / topStrip.numPixels()) + j) & 255));
    }
    topStrip.show();
    delay(wait);
  }
}
//Theatre-style crawling lights.
void theaterChase(uint32_t c, uint8_t wait) {
  for (int j = 0; j < 10; j++) {  //do 10 cycles of chasing
    for (int q = 0; q < 3; q++) {
      for (uint16_t i = 0; i < topStrip.numPixels(); i = i + 3) {
        topStrip.setPixelColor(i+q, c);    //turn every third pixel on
      }
      topStrip.show();
      delay(wait);
      for (uint16_t i = 0; i < topStrip.numPixels(); i = i + 3) {
        topStrip.setPixelColor(i + q, 0);        //turn every third pixel off
      }
    }
  }
}
//Theatre-style crawling lights with rainbow effect
void theaterChaseRainbow(uint8_t wait) {
  for (int j = 0; j < 256; j++) {     // cycle all 256 colors in the wheel
    for (int q = 0; q < 3; q++) {
      for (uint16_t i = 0; i < topStrip.numPixels(); i = i + 3) {
        topStrip.setPixelColor(i + q, Wheel( (i + j) % 255));    //turn every third pixel on
      }
      topStrip.show();
      delay(wait);
      for (uint16_t i = 0; i < topStrip.numPixels(); i = i + 3) {
        topStrip.setPixelColor(i+q, 0);        //turn every third pixel off
      }
    }
  }
}
// Input a value 0 to 255 to get a color value.
// The colours are a transition r - g - b - back to r.
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
void firePattern(int last){
  
}
