import socket
import threading
import json

ip   = "127.0.0.1"
porta = 12345

#  TCP/IP protocol, stream = socket TCP
# stream = ordered sequence of bytes

server = socket.socket(socket.AF_INET, socket.SOCK_STREAM)

server.setsockopt(socket.SOL_SOCKET, socket.SO_REUSEADDR, 1)

# Connects ip address and port number with socket
server.bind((ip, porta))

# Accepts 10 connections from clients
server.listen(10)

print("Server in ascolto IP: ", ip , " Porta: ", porta)

# thread that manages the single client
def gestore_client(client):

    data = client.recv(1024)
    
    print("\n\n Server ha ricevuto: ", data.decode('utf-8'), "\n\n")
    
    dizionario = json.loads(data.decode('utf-8'))
        
    for i in dizionario['studenti']:
        print("Server ==> nome: ", i['nome'])
        print("Server ==> cognome: ", i['cognome'])
        print("Server ==> anni: ", i['anni'])
        
    for i in dizionario['docenti']:
        print("Server ==> nome: ", i['nome'])
        print("Server ==> cognome: ", i['cognome'])
        print("Server ==> materia: ", i['materia'])
    
    client.close()

# server
while True:

    # receives the client socket and its address 
    client,addr = server.accept()
    
    # the single client is connected to a thread
    # the reference to the function executing the thread and its socket is passed
    clientThread = threading.Thread(target=gestore_client,args=(client,))
    clientThread.start()



