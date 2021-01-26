import socket
import base64


HOST = '127.0.0.1'  # The server's hostname or IP address
PORT = 65432        # The port used by the server

with socket.socket(socket.AF_INET, socket.SOCK_STREAM) as s:
    s.connect((HOST, PORT))
	
    # Base64 format allows binary data to be represented in a way that looks
    # and acts as plain text ASCII format. 8-bit binary data is encoded
    # into a format that can be represented in 7 bits using only
    # the characters A-Z, a-z, 0-9, + and /. The character = is used for padding.    
	# send an image converting it to a base64 byte string
    
    with open("img.jpg", "rb") as image_file: #open binary file in read mode
	     data = base64.b64encode(image_file.read())
		 
    print(data) 	
    
    s.sendall(data)
   