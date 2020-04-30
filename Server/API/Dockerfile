FROM tiangolo/uwsgi-nginx-flask:python3.6

WORKDIR /app/

COPY requirements.txt /app/
RUN pip install -r ./requirements.txt

ENV ENVIRONMENT production

COPY main.py __init__.py model_forest_pleno BSSID_pleno model_kelas BSSID_kelas /app/ 