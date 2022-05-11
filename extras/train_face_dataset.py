from scipy.io import arff
from sklearn.svm import SVC
import pandas as pd
import numpy as np
from sklearn.svm import SVC
from sklearn.metrics import classification_report, confusion_matrix
import filehelper

#data, meta = arff.loadarff(open('../audio/test_melspectogram.arff', 'rb'))
with open('./meoji.arff','r') as f:
    data, meta = arff.loadarff(f)
    print(data)
    df = pd.DataFrame(data)
    data=df.values
    X_train=data[:,0:7]
    y_train=data[:,7]
    svclassifier = SVC(gamma='auto')
    print(X_train.shape)
    print(y_train.shape)
    print(np.unique(y_train))
    svclassifier.fit(X_train, y_train)
    y_pred = svclassifier.predict(X_train)
    confusion_matrix(y_pred, y_train)
    print()
    print("-----------------Accuracy result--------------------")
    print()
    print(classification_report(y_train, y_pred))
    filehelper.save_object(svclassifier,"./svm2.model")