
#include <SoftwareSerial.h>
#include <Servo.h>

#define pinled 12

Servo servo1;//definimos los 5 servos
Servo servo2;
Servo servo3;
//Servo servo4;
//Servo servo5;

int valser1;//creamos una variable para guardar la posicion de cada servo
int valser2;
int valser3;
//int valser4;
//int valser5;

SoftwareSerial BT (10, 11); // RX | TX
  
char command; //aqui guardamos el caracter que llega por bluetooth para introducirlo en string
String string;
//boolean ledon = false; //indica el estado del led
  
  
  void setup()
  {
    BT.begin(9600);
    pinMode(pinled, OUTPUT);
    servo1.attach(7);//definimos el pin al que se conecta el cable de control de cada servo
    servo2.attach(6);
    servo3.attach(5);
    //servo4.attach(4);
   // servo5.attach(3);
    //hacemos que el robot se ponga en posicion inicial
    servo1.write(90);//centrado 
    delay(100);
    servo2.write(90);//inclinacion 45 grados
    delay(100);
    servo3.write(0);//extensor recogido
    delay(100);
    //servo4.write(90);//muñeca recta
    //delay(100);
    //servo5.write(0);//gripper abierto
    //delay(100);
  }

  void loop()
  {
   //tomamos valores actuales de servos y apagamos led, solo estará encendido cuando haya transferencia de datos
    ledOff();
    valser1=servo1.read();
    valser2=servo2.read();
    valser3=servo3.read();
    //valser4=servo4.read();
    //valser5=servo5.read();
     //con la siguiente secuencia de instrucciones capturamos los mensajes de la app
    if (BT.available() > 0) //sera true si hay informacion que recibir
    {string = "";}
    
    while(BT.available() > 0)
    {
        command = ((byte)BT.read());
        
        if(command == ':')
        {
          break;
        }
        
        else
        {
          string += command;
        }
        
        delay(1);
    }
    // una vez recibida la información y guardada en un string, la utilizamos
    if(string == "10")
    {
        ledOn();
        if (valser1<175){valser1=valser1+5;}// el primer motor va al contrario para que al dar + vaya a la derecha y al dar menos a la izq
        
    }
    if(string == "11")
    {
        ledOn();
        if (valser1>5){valser1=valser1-5;}
    }
    if(string == "20")
    {
        ledOn();
        if (valser2>5){valser2=valser2-5;}
    }
    if(string == "21")
    {
        ledOn();
        if (valser2<175){valser2=valser2+5;}
    }
    if(string == "30")
    {
        ledOn();
        if (valser3>5){valser3=valser3-5;}
    }
    if(string == "31")
    {
        ledOn();
        if (valser3<175){valser3=valser3+5;}
    }
    if(string == "40")
    {
        ledOn();
        //if (valser4>5){valser4=valser4-5;}
    }
    if(string == "41")
    {
        ledOn();
        //if (valser4<175){valser4=valser4+5;}
    }
    if(string == "50")
    {
        ledOn();
        //if (valser5>5){valser5=valser5-5;}
    }
    if(string == "51")
    {
        ledOn();
       // if (valser5<175){valser5=valser5+5;}
    }
     {string = "";}//ponemos esto por que si no hace la ultima instruccion todo el rato
     
    //servo5.write(valser5);
    //delay(50);
    //servo4.write(valser4);
    //delay(50);
    
    servo3.write(valser3);
    delay(50);
    servo2.write(valser2);
    delay(50);
    servo1.write(valser1);
    delay(50);
 }
 
void ledOn()
   {
      digitalWrite(pinled, HIGH);
      delay(10);
    }
 
 void ledOff()
 {
      digitalWrite(pinled, LOW);
      delay(10);
 }
