import socket
import sys
import cv2
import numpy as np
from keras.models import load_model
import sys
faceCascade = cv2.CascadeClassifier('C:/python35/keras_model_face/haarcascade_frontalface_alt2.xml')
model = load_model('C:/python35/keras_model_face/model_5-49-0.62.hdf5')
HOST = ''	# Symbolic name, meaning all available interfaces
PORT = 7812	# Arbitrary non-privileged port

s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
print('Socket created')

#Bind socket to local host and port
try:
	s.bind((HOST, PORT))
except socket.error as msg:
	print('Bind failed. Error Code : ' + str(msg[0]) + ' Message ' + msg[1])
	sys.exit()
	
print('Socket bind complete')

#Start listening on socket
s.listen(10)
print( 'Socket now listening')

#now keep talking with the client
while 1:
    #wait to accept a connection - blocking call
	conn, addr = s.accept()
	print( 'Connected with ' + addr[0] + ':' + str(addr[1]))
	try:
		data = conn.recv(1024).decode()
		print(data)
		image_path=data.strip()
		exists = os. path. isfile(image_path)
		if(exists):
			filename =image_path
			face_crop = cv2.imread(filename)
			face_crop = cv2.resize(face_crop,(48,48))
			face_crop = cv2.cvtColor(face_crop, cv2.COLOR_BGR2GRAY)
			face_crop = face_crop.astype('float32')/255
			face_crop = np.asarray(face_crop)
			face_crop = face_crop.reshape(1, 1,face_crop.shape[0],face_crop.shape[1])
			result = target[np.argmax(model.predict(face_crop))]
			r=result
			conn.send(r.encode())
	except:
		print("An exception occurred") 
	conn.close()
s.close()