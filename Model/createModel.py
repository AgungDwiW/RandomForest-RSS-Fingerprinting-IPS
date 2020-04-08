# -*- coding: utf-8 -*-
"""
Created on Sun Apr  5 20:44:12 2020

@author: Project-C
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

data = loadDataset("tekber.csv")

dictBSSID = {"ce:73:14:c4:7a:28" : 0, "18:0f:76:91:f2:72" : 1, "c0:25:e9:7a:e6:30":2}
y = []
x = []
class_x = [[] for a in range(3)]


for item in data:
   
    temp = ['0' for a in range(3)]
    atomPrev= 0
    for atom in item[1:]:
        if atomPrev in dictBSSID.keys():
            temp[dictBSSID[atomPrev]] = int(atom)
        atomPrev = atom
    if '0' in temp:
        continue
    x.append(temp)
    y.append(item[0])
    class_x[int(item[0])].append(temp)

train_x =  []
train_y =  []
test_x =  []
test_y =  []
class_y = 1;
for item in class_x:
    leng = len(item)
    picked= []
    for a in range(int(leng/10)):
        while (True):
            pickedTemp = random.randint(0,len(item)-1)
            if (pickedTemp not in picked):
                picked.append(pickedTemp)
                break;
    counter = 0;
    for atom in item:
        if counter in picked:
            test_x.append(atom)
            test_y.append(class_y)
        else:
            train_x.append(atom)
            train_y.append(class_y)
        counter+=1
    class_y+=1;
        

from sklearn.ensemble import RandomForestClassifier

model = RandomForestClassifier(n_estimators=20, random_state=0)
model.fit(train_x, train_y)
y_pred = model.predict(test_x)

from sklearn.metrics import classification_report, confusion_matrix, accuracy_score

print(confusion_matrix(test_y,y_pred))
print(classification_report(test_y,y_pred))
print(accuracy_score(test_y, y_pred))

import pickle

fp = open("model1", "wb")
pickle.dump(model, fp)


