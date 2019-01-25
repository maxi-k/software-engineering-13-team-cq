# Meeting January, 17, 2019
(To prevent translation errors, this protocol is kept in the language
of the meeting, which was held in german).

## Anwesende Kunden

* Martin Hartmond
* Matthias Moosburger
* Dr. Dominik Haneberg

## Frontend
* Atomic Design darf verwendet werden, eine Aufteilung in Module wird aber von MbW verwendet.
* Zur Navigation im Frontend sollte ein Modal verwendet werden.
* Zur Lokalisierung sollte im Frontend die von MbW vorgegebene .json-Datei verwendet werden. Eigene Additionen sollten in eine eigene Datei geschrieben werden. Lokalisierung soll nur im Frontend erfolgen.
* Der BMW-Styleguide muss beachtet werden, beispielsweise bei der Formattierung von Fehlermeldungen. Der Back-Button hat keinen Pfeil, der Next-Button aber schon.
* Tests mit snapshot-Tests reichen aus.

## Backend
* Deployment soll docker-compose unterstützen. Für die Abgabe soll Heroku verwendet werden. 
Für die Abgabe Deployment Mittels Heroku.
* Eine gute Richtlinie für die Struktur unseres Projektdateien stellt der Ordner "Customizable Notification System" unter "App" dar.
