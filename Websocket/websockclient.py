from gpio import *
from time import *
from realhttp import *
from time import *

serverIP = "127.0.0.1"
serverPort = 8765

client = RealWSClient()

def onWSConnectionChange(type):
	print("connection changed: " + str(type))
	
def onWSReceive(data):
	print("received: " + data)
	customWrite(0, data)

def main():
	#client.onConnectionChange(onWSConnectionChange)
	client.onReceive(onWSReceive)

	client.connect("ws://"+serverIP+":"+str(serverPort))

	count = 0	
	
	while True:
		count += 1
		if client.state() == 3:
	    	client.send(str(count))
    	    sleep(1)

if __name__ == "__main__":
	main()
