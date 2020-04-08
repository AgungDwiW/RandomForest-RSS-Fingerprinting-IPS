#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
Created on Mon Dec  2 20:52:50 2019

@author: temperantia
"""

import csv
import random
def loadDataset(name):
    data = []
    with open(name, newline='') as f:
        reader = csv.reader(f)
        reader = list(reader)
    data = reader.copy()
    return data

data = loadDataset("dataset.csv")

"""
preprocess data
"""

y_all = [i[0] for i in data]
x_all = [i[1:] for i in data]
import pickle
bssid_token = pickle.load(open("BSSID_pleno", 'rb'))
bssid_token = {
        'c4:a3:66:ba:07:be' : 0,
        'fc:ec:da:47:11:17' : 1,
        'fc:ec:da:47:15:3b' :2
        }
x_true = []
for j in range(len(x_all)):
    x_temp = [0 for i in range(len(bssid_token))]
    for i in range(0,len(x_all[j]),2):
        if x_all[j][i] in bssid_token.keys():
            x_temp[bssid_token[x_all[j][i]]] = x_all[j][i+1]
    x_true.append(x_temp)
x_all = x_true            

picked_all = []
x_temp = []
y_temp = []
for a in range(0,len(x_all), 20):
    minim = a
    maxim = a + 20
    temp = x_all[minim:maxim]
    x_temp.append(temp)
    temp = y_all[minim:maxim]
    y_temp.append(temp)

"""
create train set and test set
"""

x_train = []
y_train = []
x_test = []
y_test= []
for a in range(len(x_temp)):
    x_now = x_temp[a]
    y_now = y_temp[a]
    for a in range (7):
        picked = random.randint(0, len(x_now)-1)
        x_test.append(x_now.pop(picked))
        y_test.append(y_now.pop(picked))
    for i in x_now:
        x_train.append(i)
    for i in y_now:
        y_train.append(i)

"""
create random forest model
"""

from sklearn.ensemble import RandomForestClassifier

model = RandomForestClassifier(n_estimators=20, random_state=0)
model.fit(x_all, y_all)
y_pred = model.predict(x_test)


"""
test model
"""
from sklearn.metrics import classification_report, confusion_matrix, accuracy_score

print(confusion_matrix(y_test,y_pred))
print(classification_report(y_test,y_pred))
print(accuracy_score(y_test, y_pred))

"""
export model
"""
import pickle

data_used = [data[0][1],data[0][3], data[0][5], data[0][7]]
pickle.dump(model, open("model_forest_pleno", 'wb'))
pickle.dump(bssid_token, open("BSSID_pleno", 'wb'))