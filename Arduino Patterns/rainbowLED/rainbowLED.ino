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
  kyleFunction();
  //trentonFunction();
  //mixFunction();
  //smoothFunction();
}

int lerp(double a, double b, double t) {
  return int(a * (1 - t) + (b*t));
}

void v3Lerp(double &green, double &red, double &blue, double greenGoal, double redGoal, double blueGoal, double t) {
  green = green * (1 - t) + (greenGoal * t);
  red = red * (1 - t) + (redGoal * t);
  blue = blue * (1 - t) + (blueGoal * t);
}

double green = 0,
red = 0,
blue = 0;
double smoothness = 0.06;
int increments = 0;
int maxMount = 100;

void smoothFunction() {
  strip.setBrightness(15);
  
  for (int i = 0; i < maxMount; i++) {
    v3Lerp(green, red, blue, 0, 255, 0, smoothness);
    for (int i = 0; i < NUMPIXELS; i++) {
      strip.setPixelColor(i, green, red, blue);
    }
    strip.show();
    delay(1);
  }

  for (int i = 0; i < maxMount; i++) {
    v3Lerp(green, red, blue, 255, 0, 0, smoothness);
    for (int i = 0; i < NUMPIXELS; i++) {
      strip.setPixelColor(i, green, red, blue);
    }
    strip.show();
    delay(1);
  }

  for (int i = 0; i < maxMount; i++) {
    v3Lerp(green, red, blue, 0, 0, 255, smoothness);
    for (int i = 0; i < NUMPIXELS; i++) {
      strip.setPixelColor(i, green, red, blue);
    }
    strip.show();
    delay(1);
  }
  
}

void mixFunction() {
  strip.setBrightness(25);

  for (int i = 0; i < NUMPIXELS; i++) {
    strip.setPixelColor(i, green, red, blue);
  }

  if (green == 0 && red < 255 && blue == 0) {
    red = red + increments;
  } else if (green < 255 && red == 255 && blue == 0) {
    green = green + increments;
  } else if (green == 255 && red > 0 && blue == 0) {
    red = red - + increments;
  } else if (green == 255 && red == 0 && blue < 255) {
    blue = blue + increments;
  } else if (green > 0 && red == 0 && blue == 255) {
    green = green - increments;
  } else if (green == 0 && red < 255 && blue == 255) {
    red = red + increments;
  } else if (green == 0 && red == 255 && blue > 0) {
    blue = blue - increments;
  }

  strip.show();
  delay(1);
}

void kyleFunction() {
  strip.setBrightness(35);

  for (int b = 0; b < 6; b++) {
    for (int i = 0; i < NUMPIXELS; i++) { 
      if (i % 6 == 0) {
        strip.setPixelColor(i+b, 0, 255, 0 );
      } else if (i % 5 == 0) {
        strip.setPixelColor(i+b, 128, 255, 0 );
      } else if (i % 4 == 0) {
        strip.setPixelColor(i+b, 255, 255, 0 );
      } else if (i % 3 == 0) {
        strip.setPixelColor(i+b, 255, 0, 0 );
      } else if (i % 2 == 0) {
        strip.setPixelColor(i+b, 0, 0, 255);
      } else if (i % 1 == 0) {
        strip.setPixelColor(i+b, 0, 128, 255);
      }
    }
    strip.show();
    delay(50); 
  }
  
}

void trentonFunction() {
  strip.setBrightness(35);
  for (int i = 0; i < NUMPIXELS; i += 12) { 
      strip.setPixelColor(i, 0, 255, 0 );
      strip.setPixelColor((i + 1), 0, 255, 0);
      strip.show();
      delay(50);
      strip.setPixelColor((i + 2), 120, 255, 0);
      strip.setPixelColor((i + 3), 120, 255, 0);
      strip.show();
      delay(50);
      strip.setPixelColor((i + 4), 255, 255, 0);
      strip.setPixelColor((i + 5), 255, 255, 0);
      strip.show();
      delay(50);
      strip.setPixelColor((i + 6), 198, 65, 7);
      strip.setPixelColor((i + 7), 198, 65, 7);
      strip.show();
      delay(50);
      strip.setPixelColor((i + 8), 0, 0, 255);
      strip.setPixelColor((i + 9), 0, 0, 255);
      strip.show();
      delay(50);
      strip.setPixelColor((i + 10), 0, 208, 255);
      strip.setPixelColor((i + 11), 0, 208, 255);
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

