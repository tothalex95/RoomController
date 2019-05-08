#include <dht.h>

dht DHT;

#define DHT11_PIN 5

void setup()
{
  Serial.begin(115200);
}

void loop()
{
  // READ DATA
  int chk = DHT.read11(DHT11_PIN);
  String output=String(DHT.humidity)+","+String(DHT.temperature);
  Serial.println(output);

  delay(10000);
}