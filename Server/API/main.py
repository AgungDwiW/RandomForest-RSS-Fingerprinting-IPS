#!flask/bin/python
import os
from flask import Flask
from flask import request
import pandas as pd
from sklearn.ensemble import RandomForestClassifier
import pickle
import json
import mysql.connector
from flask import jsonify
    
    
# creating and saving some model
"""
predictor
"""
terminalCounter = 0
bssid_token = {"ce:73:14:c4:7a:28" : 0, 
             "18:0f:76:91:f2:72" : 1, 
             "c0:25:e9:7a:e6:2f":2}
model = pickle.load(open("model1", 'rb'))

"""
MySQL connector
"""


def getData(dataID):
    #fetch data
    #fetch booth
    
    cnx = mysql.connector.connect(user='root', password='',
                                  host='127.0.0.1',
                                  database='IPS')
    cursor = cnx.cursor()
    cursor.execute("SELECT * FROM booth where booth_id_model='"+str(dataID)+"'")
    booth = cursor.fetchall()
    
    #fetch booth info
    cursor.execute("SELECT * FROM booth_info where booth_id='"+str(booth[0][0])+"'")
    booth_infos = cursor.fetchall()
    
    cnx.close()
    
    infos = {}
    counter = 1
    
    for item in booth_infos:
        temp = {"title" : item[2],
                "subtitle" : item[3]
                }
        infos[str(counter)] = temp
        counter+=1
    
    #create json
    output = {"status" : "success", 
              "booth_id" : booth[0][2],
              "booth_name" : booth[0][1],
              "booth_info" : infos
              }
    outputJson = json.dumps(output)
    
    return outputJson

    

def predict(test):
    """
    test = {"c4:a3:66:ba:07:be":"-89",
            "98:de:d0:ed:3f:24":"-86",
            "4c:5e:0c:9d:fb:ff":"-90",
            "fc:ec:da:47:11:17":"-76",
            "04:18:d6:ad:04:71":"-79",
            "64:70:02:3f:4e:59":"-88",
            "fc:ec:da:47:15:3b":"-60",
            "fc:ec:da:47:0d:7f":"-80"}
    """
    print(test)
    cleaned = [0 for i in range(len(bssid_token))]
    for i in bssid_token:
        if i in test:
            cleaned[bssid_token[i]] = int(test[i])
    if len(cleaned) != len(bssid_token):
        return -2
    try:
        print ([cleaned])
        model.n_jobs = 1
        pred = model.predict([cleaned])
    except:
        return -1
        
    return str(pred[0])
app = Flask(__name__)

@app.route('/isAlive')
def index():
    return "true"

@app.route('/api', methods=['POST'])
def predictApi():
    PostData = request.form.get('PostData')
    jsons = json.loads(PostData)
    out1 = predict(jsons)
    
    if out1==-1:
        output = {"status" : "error", "message" : "predict error"}
    
    
    elif out1==-2:
        output = {"status" : "error", "message" : "ap error, not found"}
        
    
    else:
        output = getData(out1)
        
    return output


@app.route('/test', methods=['POST'])
def test():
    PostData = request.form.get('PostData')

    print(PostData)
    jsons = json.loads(PostData)
    global terminalCounter
    terminalCounter+=1
    print(terminalCounter, ": test-",jsons)

    return str(PostData)


if __name__ == '__main__':
    app.run(port=5000,host='0.0.0.0')
    """
    if os.environ['ENVIRONMENT'] == 'production':
        app.run(port=80,host='0.0.0.0')
    if os.environ['ENVIRONMENT'] == 'local':
        app.run(port=5000,host='0.0.0.0')
    """
        
    