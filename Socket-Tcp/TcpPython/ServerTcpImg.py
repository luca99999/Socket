import socket
import base64
from PIL import Image
from io import BytesIO

#pip install Pillow

HOST = '127.0.0.1'  # Standard loopback interface address (localhost)
PORT = 65432        # Port to listen on (non-privileged ports are > 1023)

with socket.socket(socket.AF_INET, socket.SOCK_STREAM) as s:
    s.bind((HOST, PORT))
    s.listen()
    conn, addr = s.accept()
    
    with conn:
        print('Connected by', addr)
        while True:
		    
            data = conn.recv(1000000)
            if not data:
               break
            
            # print base64 encoded data
            
            print(data)
            
            # decode the string containing base64 encoded data and 
            # return the resulting binary data
            im = Image.open(BytesIO(base64.b64decode(data)))
            im.show()
            im.save('img1.jpg', 'JPEG') # create an image
            
           