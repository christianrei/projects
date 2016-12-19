#include <SPI.h>
#include <Dhcp.h>
#include <Dns.h>
#include <Ethernet.h>
#include <EthernetClient.h>
#include <Temboo.h>
#include "TembooAccount.h" // Contains Temboo account information

byte ethernetMACAddress[] = ETHERNET_SHIELD_MAC;

EthernetClient client;
int numRuns = 1;   // Execution count, so this doesn't run forever
int maxRuns = 10;   // Maximum number of times the Choreo should be executed
 
int ledPin = 13;                // choose the pin for the LED
int inputPin = 2;               // choose the input pin (for PIR sensor)
int pirState = LOW;             // we start, assuming no motion detected
int val = 0;                    // variable for reading the pin status
 
void setup() {
  pinMode(ledPin, OUTPUT);      // LED is the output
  pinMode(inputPin, INPUT);     // The sensor is the input
 
  Serial.begin(9600);

  // ADDED
  delay(4000);
  while(!Serial);

  Serial.print("DHCP:");
  if (Ethernet.begin(ethernetMACAddress) == 0) {
    Serial.println("FAIL");
    for(;;)
      ;
  }

  Serial.println("OK");
  delay(5000);

  Serial.println("Setup complete.\n");
}
 
void loop(){
  //if(numRuns <= maxRuns) {
    val = digitalRead(inputPin);  // read input value
    if (val == HIGH) {            // check if the input is HIGH
      digitalWrite(ledPin, HIGH);  // turn LED ON
      if (pirState == LOW) {
        Serial.println("Motion detected!");
        pirState = HIGH;
      }
      TembooChoreo SendSMSChoreo(client);
  
      // Invoke the Temboo client
      SendSMSChoreo.begin();
  
      // Set Temboo account credentials
      SendSMSChoreo.setAccountName(TEMBOO_ACCOUNT);
      SendSMSChoreo.setAppKeyName(TEMBOO_APP_KEY_NAME);
      SendSMSChoreo.setAppKey(TEMBOO_APP_KEY);
  
      // Set Choreo inputs
      String AuthTokenValue = autheticationCode;
      SendSMSChoreo.addInput("AuthToken", AuthTokenValue);
      String ToValue = recievingNumber;
      SendSMSChoreo.addInput("To", ToValue);
      String FromValue = sendingNumber;
      SendSMSChoreo.addInput("From", FromValue);
      String BodyValue = "Intruder Detected!";
      SendSMSChoreo.addInput("Body", BodyValue);
      String AccountSIDValue = accountValue;
      SendSMSChoreo.addInput("AccountSID", AccountSIDValue);
  
      // Identify the Choreo to run
      SendSMSChoreo.setChoreo("/Library/Twilio/SMSMessages/SendSMS");
  
      // Run the Choreo; when results are available, print them to serial
      SendSMSChoreo.run();
  
      while(SendSMSChoreo.available()) {
        char c = SendSMSChoreo.read();
        Serial.print(c);
      }
      SendSMSChoreo.close();
      delay(10000); //DELAY HERE
    } else {
      digitalWrite(ledPin, LOW); // turn LED OFF
      if (pirState == HIGH){
        // we have just turned off
        Serial.println("Motion ended!");
        // We only want to print on the output change, not state
        pirState = LOW;
      }
    }
  //}
  Serial.println("\nWaiting...\n");
  //delay(15000); // wait 15 seconds between SendSMS calls
}
