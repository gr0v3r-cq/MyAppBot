#define led 10
String string;
char command;
void setup() {
  // put your setup code here, to run once:
  Serial.begin(9600);
  pinMode(led,OUTPUT);
  digitalWrite(led,LOW);
}

void loop() {
  // put your main code here, to run repeatedly:
  if(Serial.available()>0){
    string = "";
    }
  while(Serial.available()>0){
    command = ((byte)Serial.read());
    if(command == ':'){
      break;
      }
    else{
      string +=command;
      Serial.print(string+"\n");
      }
      delay(1);
    }
   if(string == "ad"){
    ledOn();
    }
   if(string == "stop"){
    ledOff();
    }

    if(string == "1"){
      ledOn();
      }
    if(string == "0"){
      ledOff();
      }  
}
void ledOn(){
  digitalWrite(led,HIGH);
  }
void ledOff(){
  digitalWrite(led,LOW);
  }
