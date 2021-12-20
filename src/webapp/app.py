import requests

from flask import Flask, render_template, request

app = Flask(__name__)

REST_API = "http://localhost:8080"

@app.route("/")
def hello():
    return render_template("index.html")

@app.route("/submit", methods=['POST'])
def handle_poke_attacks():
    poke_id = request.form['poke_id']
    attacks = requests.get("http://localhost:8080/attacks?pokemon={}".format(poke_id))
    print(attacks)

@app.route("/pokemon/<id>")
def show_pokemon(id):
    pokemon = requests.get(REST_API + "/pokemon?pokemon={}".format(id)).text
    return render_template("pokemon.html", pokemon=pokemon)
