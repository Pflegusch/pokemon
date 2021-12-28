import requests

from flask import Flask, render_template, request, session
from http import HTTPStatus

from werkzeug.utils import redirect
from dotenv import load_dotenv

app = Flask(__name__)
app.secret_key = "p402*#22ya+#-.!!?" #hardcoded for now

load_dotenv()

REST_API = "http://localhost:8080/rest"

logged_in_users = []

@app.route("/")
def index():
    return render_template("index.html")

@app.route("/signup", methods=["GET", "POST"])
def signup():
    if request.method == "POST":
        req = request.form
        username = req["username"]
        email = req["email"]
        password = req["password"]

        response = requests.get(REST_API + "/signup?username={}&email={}&password={}"
            .format(username, email, password))

        if response.status_code == HTTPStatus.OK:
            return redirect("/login", 302)
        elif response.status_code == HTTPStatus.INTERNAL_SERVER_ERROR:
            return response.text, 500
        else:
            return "<br>Server Error!</br>", 501

    return render_template("signup.html")

@app.route("/login", methods=["GET", "POST"])
def login():
    if request.method == "POST":
        req = request.form
        username = req["username"]
        password = req["password"]

        session["user_id"] = username

        response = requests.get(REST_API + "/login?username={}&password={}"
            .format(username, password))

        if response.status_code == HTTPStatus.OK:
            logged_in_users.append(session["user_id"])
            return redirect("/dashboard", 302)
        elif response.status_code == HTTPStatus.FORBIDDEN:
            return "<br>FORBIDDEN!</br>", 403
        elif response.status_code == HTTPStatus.NOT_FOUND:
            return "<br>Not found!</br>", 404
    
    return render_template("login.html")

@app.route("/logout")
def logout():
    logged_in_users.remove(session["user_id"])
    session.pop("user_id", None)
    return redirect("/login", 302)

@app.route("/dashboard")
def dashboard():
    if not session:
        return redirect("/login", 302)

    user_id = session["user_id"]   
    print("Currently logged in: " + user_id)
    pokemons = requests.get(REST_API + "/pokemons?user_id={}".format(user_id)).json()
    print(pokemons)
    show_logged_in_users()
    return render_template("dashboard.html", username=user_id, pokemons=pokemons)

@app.route("/submit")
def handle_poke_attacks():
    poke_id = request.form['poke_id']
    attacks = requests.get(REST_API + "/attacks?pokemon={}".format(poke_id))
    print(attacks)

@app.route("/pokemon/<id>")
def show_pokemon(id):
    pokemon = requests.get(REST_API + "/pokemon?pokemon={}".format(id)).text
    return render_template("pokemon.html", pokemon=pokemon)

def show_logged_in_users():
    print(logged_in_users)