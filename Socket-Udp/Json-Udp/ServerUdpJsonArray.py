import socket
import sys
import json

s = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)

server_address = ("127.0.0.1", 8888)

s.bind(server_address)

while True:
    print("Server in ascolto")
    data, address = s.recvfrom(4096)
    
    print("\n\n Server ha ricevuto: ", data.decode('utf-8'), "\n\n")
    
    dizionario = json.loads(data.decode('utf-8'))
    # il risultato è memorizzato in un dizionario
    
    print("studenti")
    
    for i in dizionario['studenti']:
        print("Server ==> nome: ", i['nome'])
        print("Server ==> cognome: ", i['cognome'])
        print("Server ==> anni: ", i['anni'])
        
    print("docenti")
        
    for i in dizionario['docenti']:
        print("Server ==> nome: ", i['nome'])
        print("Server ==> cognome: ", i['cognome'])
        print("Server ==> materia: ", i['materia'])
        
    for i in dizionario['libri']:
        print("Server ==> autore: ", i['autore'])
        print("Server ==> titolo: ", i['titolo'])
        print("Server ==> casaeditrice: ", i['casaeditrice'])
    
   