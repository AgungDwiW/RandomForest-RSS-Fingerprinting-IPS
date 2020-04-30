from flask import Blueprint
from flask import render_template
from flask import request
from os import remove
from werkzeug import secure_filename
from flask import Flask, redirect, url_for
import pickle
import json

"""
predictor
"""

"""
controller
"""
WebApp = Blueprint("controller",__name__)

@WebApp.route('/api', methods=['POST'])
def index():
    print(request.get_json())
    jsons = request.get_json()
    cleaned = [0 for i in range(len(bssid_token))]
    for i in bssid_token:
        cleaned[bssid_token[i]] = int(jsons[i])
    
    if len(cleaned) != len(bssid_token):
        err = {"error": "error - some AP no found"}
        return json.dumps(err)
    try:
        pred = model.predict([cleaned])
    except:
        err = {"error": "error - can't predict"}
        return json.dumps(err)
    
    ret = str(pred[0])
    #ret = {"predicted": str(pred[0])}
    #ret = json.dumps(ret)
    print(ret)
    return ret
    #return str(bc.predict())

@WebApp.route('/test', methods=['POST'])
def test():
    print(request.get_json())
    return request.get_json()
