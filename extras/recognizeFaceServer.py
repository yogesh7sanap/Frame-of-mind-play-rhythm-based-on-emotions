import sys

sys.path.append('C:\\Python35\\Lib\site-packages')


import os
from glob import glob

import socket
import sys

import sys
from scipy.io import arff
from sklearn.svm import SVC
import pandas as pd
import numpy as np
from sklearn.svm import SVC
from sklearn.metrics import classification_report, confusion_matrix
import filehelper
import librosa
import random
from include_classes import *
HOST = ''  # Symbolic name, meaning all available interfaces
PORT = 7813  # Arbitrary non-privileged port
s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
print('Socket created')
svclassifier=filehelper.read_object("./svm.model")
image_path="8.386282583,13.36120944,12.26942423,12.66666667,40.11111111,12.40227661,37.94747751"

lst_int = [float(x) for x in image_path.split(",")]
reshape2d=np.reshape(lst_int,(1,-1))
#reshape2d=np.reshape(getMapping(2),(1,-1))
data = svclassifier.predict(reshape2d)
print(data)


# Bind socket to local host and port
try:
    s.bind((HOST, PORT))
except socket.error as msg:
    print('Bind failed. Error Code : ' + str(msg[0]) + ' Message ' + msg[1])
    sys.exit()

print('Socket bind complete')

# Start listening on socket
s.listen(10)
print('Socket now listening')

# now keep talking with the client
while 1:
    # wait to accept a connection - blocking call
    conn, addr = s.accept()
    print('Connected with ' + addr[0] + ':' + str(addr[1]))
    try:
      data = conn.recv(2048).decode()
      image_path = data.strip()
      #data=getMapping(float(image_path));
      lst_int = [float(x.strip()) for x in image_path.split(",")]
      ooutput=lst_int[7]
      lst_int=lst_int[0:7]
      print(lst_int)
      output = svclassifier.predict(np.reshape(lst_int,(1,-1)))
      print(ooutput)
      r = str(ooutput) 
      print(r)
      conn.send(r.encode())
    except Exception as e:
        print("An exception occurred")
        print(e.message)
    conn.close()
s.close()