from socketComm import *
from mtRoboLib import *

s = Server(12345, 1245567, 1245563, Server.protocol.UDP)
r = MTRobo()

while(True):
	s.start()
	while(s.isConnected()):
		event = s.receive()
		print(event)
		if(event==0):
			r.click(0)
		elif(event==1): # Mouse Move
			x = s.receive()
			y = s.receive()
			r.moveMouse(x, y)
		elif(event==2):
			r.click(1)
		elif(event==3):
			r.mousePress(0)
		elif(event==4):
			r.mouseRelease()
		elif(event==5):
			dy = s.receive()
			r.hScroll(dy)
# 		elif(event==6):
# 			dx = s.receive()
# 			r.vScroll(dx)
		elif(event==7):
			r.zoomOut();
		elif(event==8):
			r.zoomIn();

