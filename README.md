# MonitoringPojazdow
Projekt kompetencyjny \
Dokumentacja projektowa dostepna pod adresem: \
https://tulodz-my.sharepoint.com/:f:/g/personal/224815_edu_p_lodz_pl/EoilBa8vRYZDgjsHJJbNFWcB4vttHI2eEpsLvLWAsU4T8Q?e=URtUga

## Czesc 1
***Aplikacja na android OS:***
- Zbiera informacje z GPS.
- Ma mozliwosc ustawienia id kierowcy i pojazdu.
- Przesyla informacje w formie pliku json na serwer.

*Przykladowy plik json*
```
{
    driverId: 1
    latitude: 51.2584291
    longitude: 19.8636438
    timestamp: 1620233639623
    vehicleId: 4
}
```

## Czesc 2
***Serwer:***
- Udostepnia interface do zapisu danych otrzymanych w postaci pliku json.
- Przechowuje otrzymane dane w bazie.
- Udostepnia interface do pobrania danych w postaci pliku json.

## Czesc 3
***Aplikacjia uzytkownika.***
- Stworzona w technologi webowej.
- Mozliwosci aplikacji
    - Historia tras
    - Srednia predkosc pokonanego odcinka trasy
    - Lokalizacje i czas postoju
