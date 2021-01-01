import socket
import sys
import json

ip = "127.0.0.1"
port = int(8888)

s = socket.socket(socket.AF_INET, socket.SOCK_DGRAM, 0)

uscita = 'n'

dizionario = {'studenti':[], 'docenti':[], 'libri':[]}

while uscita == 'n':

    print("Studenti")

    nome = input("Inserisci il nome ==> ")
    cognome = input("Inserisci il cognome ==> ")
    anni = input("Inserisci gli anni ==> ")

    dizionario['studenti'].append({"nome": nome, "cognome": cognome, "anni": anni})  
    
    print("Docenti")
    
    nome = input("Inserisci il nome ==> ")
    cognome = input("Inserisci il cognome ==> ")
    materia = input("Inserisci la materia ==> ")

    dizionario['docenti'].append({"nome": nome, "cognome": cognome, "materia": materia}) 

    print("Libri")

    autore = input("Inserisci autore ==> ")
    titolo = input("Inserisci il titolo ==> ")
    casaeditrice = input("Inserisci casa editrice ==> ")
 
    dizionario['libri'].append({"autore" : autore, "titolo": titolo, "casaeditrice": casaeditrice})  
    
    uscita = input("Vuoi uscire s/n ==> ")
    
    
strJson = json.dumps(dizionario)    
s.sendto(strJson.encode('utf-8'), (ip, port))
print("\n\n Client Invia: ", strJson, "\n\n")
    
s.close()
    
    
    
    
    