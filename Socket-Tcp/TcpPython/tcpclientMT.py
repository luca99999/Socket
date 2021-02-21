import socket
import sys
import json

serverTcp = "127.0.0.1"
porta = 12345

uscita = 'n'

dizionario = {'studenti':[], 'docenti':[]}

try:
    s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    s.connect((serverTcp, porta))
	
    while uscita == 'n':

       print("studenti ")
       nome = input("Inserisci il nome ==> ")
       cognome = input("Inserisci il cognome ==> ")
       anni = input("Inserisci gli anni ==> ")

       dizionario['studenti'].append({"nome": nome, "cognome": cognome, "anni": anni})  
     
       print("docenti ")
       nome = input("Inserisci il nome ==> ")
       cognome = input("Inserisci il cognome ==> ")
       materia = input("Inserisci la materia ==> ")

       dizionario['docenti'].append({"nome": nome, "cognome": cognome, "materia": materia})  
    
       uscita = input("Vuoi uscire s/n => ")
    
    
    strJson = json.dumps(dizionario)    
    s.send(strJson.encode('utf-8'))
    print("\n\n Client Invia: ", strJson, "\n\n")
    
    	
except socket.errno as e:
    print("Errore Socket ", e)
finally:
    s.close()
    
