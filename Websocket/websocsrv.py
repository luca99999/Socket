import asyncio
import websockets


# Coroutines are functions that on the instruction await 
# they release the flow of control back to the event loop.

async def invia(websocket, path):
    while True:
       dati = await websocket.recv()
       print(f"Ricevuto:  {dati}")

       messaggio = f"Risp Srv {dati}"
       # await is a point where a context switch can happen
       await websocket.send(messaggio)
   

start_server = websockets.serve(invia, "localhost", 8765)


# event loop: manages and distributes the execution of different coroutines. 

asyncio.get_event_loop().run_until_complete(start_server)
asyncio.get_event_loop().run_forever()