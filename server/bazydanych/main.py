import json
import mysql.connector

login = "prettyjohnny"
password = "1234"

mydb = mysql.connector.connect(
    host="localhost",
    user="root",
    password="R3k@ViCZK@",
    database="tracker"
)

# REJESTRACJA UŻYTKOWNIKA
def registration():
    mycursor = mydb.cursor()
    mycursor.execute("SELECT MAX(clientID) FROM tracker.client;")
    maxid = mycursor.fetchall()
    maxid = maxid[0]
    maxid = str(maxid[0] + 1)
    with open('regis.json') as jsonNewUserData:
        newUserData = json.load(jsonNewUserData)
    login = newUserData["login"]
    password = newUserData["password"]
    name = newUserData["name"]
    surname = newUserData["surname"]
    email = newUserData["email"]
    query = "INSERT INTO tracker.client VALUES(" + maxid + ",\"" + login + "\",\"" + password + "\");"
    mycursor.execute(query)
    mydb.commit()
    jsonNewUserData.close()

#registration()

# UWIERZYTELNIANIE UŻYTKOWNIKA
def authorization(login, password):
    mycursor = mydb.cursor()
    query = "SELECT * FROM tracker.client WHERE login=\"" + login + "\";"
    mycursor.execute(query)
    userData = mycursor.fetchall()
    if userData.__len__() == 0:
        print("Nieznany użytkownik")
    else:
        userData = userData[0]
        if userData[1] == login:
            if userData[2] == password:
                print("poświadczenia poprawne")
                return userData[0]
            else:
                print("Hasło jest niepoprwne")
        else:
            print("Nazwa użytkownika jest niepoprawna!")

userID = authorization(login, password)

print(userID)
# POBIERANIE DANYCH HISTORYCZNYCH
