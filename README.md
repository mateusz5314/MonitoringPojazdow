# MonitoringPojazdow
Projekt kompetencyjny \
Dokumentacja projektowa dostepna pod adresem: \
https://tulodz-my.sharepoint.com/:f:/g/personal/224815_edu_p_lodz_pl/EoilBa8vRYZDgjsHJJbNFWcB4vttHI2eEpsLvLWAsU4T8Q?e=URtUga

## Czesc 1
Aplikacja na telefon:
Zbiera informacje z GPS i przesyla na serwer po REST API.

## Czesc 2
Serwer:
Napisany w python.
Otrzymuje dane w formacie json/xml.
Webowa aplikacja dla uzytkownika

### Mozliwosci aplikacji
Historia tras
Srednia predkosc pokonanej trasy
Lokalizacje i czas postoju
Zapytanie o akutalna pozycje danego urzadzenia.

### Przykladowy plik json
```
{
    id
    lokalizacja
    timestamp
}
```