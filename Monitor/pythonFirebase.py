import time
import serial
from firebase import firebase

ser = serial.Serial('/dev/ttyUSB0', 115200)
firebase=firebase.FirebaseApplication('https://roomcontroller-94911.firebaseio.com/')

while 1:
        if(ser.in_waiting >0):
                line = ser.readline()
                print(line)
                result = line.split(',')
                temp=float(result[1])
                humidity=float(result[0])
                time.sleep(5)
                #firebase= firebase.FirebaseApplication('https://roommonitor-69420.firebaseio.com/')
                firebaseResult = firebase.post('measurements', {'measurement':{'humidity':humidity, 'temperature':temp}})
                print(firebaseResult)
                result = firebase.get('/servoPositions', None)
                try:
                        for x in result.values():
                                print x['servoPosition']
                                #ser.write(x['servoPosition'])
                        for x in result:
                                firebase.delete('/servoPositions', x)
                except:
                        print

