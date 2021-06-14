import datetime
import json
import mysql.connector

login = "prettyjohnny"
password = "1234"

with open('car.json') as json_data:
    data_dict = json.load(json_data)
    for x in data_dict:
        print(x)


trackerDB = mysql.connector.connect(
    host="localhost",
    user="root",
    password="R3k@ViCZK@",
    database="tracker"
)
mycursor = trackerDB.cursor()


# dodać sprawdzanie czy taki użytkownik istnieje
def registration():
    """REJESTRACJA UŻYTKOWNIKA"""
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
    trackerDB.commit()
    jsonNewUserData.close()



def authorization(login, password):
    """UWIERZYTELNIANIE UŻYTKOWNIKA"""
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


def getRoute(transmiterID, date, beginTime, endTime):
    """POBIERANIE DANYCH HISTORYCZNYCH"""
    querySelect = "SELECT Latitude, Longitude "
    queryFrom = "FROM tracker.location "
    queryWhere1 = "WHERE TransmitterID = " + str(transmiterID) + " AND ReadingDate like \"" + date
    queryWhere2 = "\" AND ReadingTime BETWEEN \"" + beginTime + "\" AND \"" + endTime + "\";"
    query = querySelect + queryFrom + queryWhere1 + queryWhere2
    mycursor.execute(query)
    routeData = mycursor.fetchall()



def addLocation(transmiterID, date, time, latitude, longitude):
    """DODANIE LOKALIZACJI DO BAZY DANYCH"""
    queryInsert = "INSERT INTO tracker.location "
    queryValues = "VALUES(" + str(transmiterID) + ", \"" + date + "\", time(\"" + time + "\"), " + latitude + ", " + longitude + ");"
    query = queryInsert + queryValues
    mycursor.execute(query)
    trackerDB.commit()


dateAndTime = datetime.datetime.now()
date = dateAndTime.strftime("%Y-%m-%d")
time = dateAndTime.strftime("%X")
latitude = "51.788427"
longitude = "19.449363"


#registration()
#userID = authorization(login, password)
#addLocation(1, date, time, latitude, longitude)
#getRoute(1, "2021-06-13", "11:36:00", "11:37:00")

json_data.close()