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
ML_MOODS_SEQUENCE=["ANGRY", "DISGUST", "FEAR", "HAPPY", "SAD", "SURPRISE", "NEUTRAL"]
def getMapping(md):
   data, meta = arff.loadarff(open('./meoji.arff','r'))
   df = pd.DataFrame(data)
   data=df.values
   my_points=np.where(data[:,7]==float(2))
   #print(my_points)
   a2=(len(my_points[0].tolist())-1)*random.random()
   a2=round(a2)
   #print(a2)
   #print(data[my_points[0].tolist()[a2]] )
   return data[my_points[0].tolist()[a2]][0:7]
   
   
