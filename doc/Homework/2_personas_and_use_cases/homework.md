# Softwaretechnik - Übungsblatt 2

## Aufgabe 1 - Personas
<!--
Attribute: 
* Allgemein: Name, Alter, Geschlecht, Vita, Hobbies, Motto, Familenstand, Kinder
* Technisch: Computeraffinität, Computerkenntnisse
* Domain: Ziel des Geschäftsvorgangs
* System: Motivation; Erwartungen an das System
-->

| Name        | Anna      | Marlene    | Bob
| ------------|-----------|------------|----------
| Alter       | 32        | 42                   | 55 
| Funktion    | Administratorin (Flotte) | Managerin (Flotte) | externer Nachrichtenempfänger
| Position    | Mittleres Management der IT | Assistenz der lokalen Geschäftsführung | Buchhaltung
| Geschlecht  | W              | W           | M    
| Ausbildung  | Fachinformatikerin | BWL-Studium | Kaufmännische Ausbildung
| Hobbies     | Marathon       | Yoga        | Fußball & Bier
| Motto       | Wer Rastet der Rostet | Der frühe Vogel fängt den Wurm, aber der späte Wurm verpasst den Vogel! | Prost! 
| Familie     | Verheirtatet | Geschieden | Ledig
| Kinder      | 2            | 4                | 0
| Computeraffinität | Mittel    | Hoch        | Gering
| Computerkenntnisse | Sehr Gut | Gering      | Mittel 

### Detaillierung

#### Anna
<img src="images/anna.jpg" width="500">

* *Motivation das System zu benutzen:*
Anna ist für ganz Deutschland die technisch Verantwortliche für die Flotte des Unternehmens, und ist damit stark belastet. Sie hat auch ohne micro-management der lokalen Flottenmanager genug zu tun, und möchte, dass diese eine möglichst gute IT haben, um die meisten Probleme selbst schnell, effektiv und gut lösen zu können.
* Erwartungen an das System:
Anna erhofft sich, durch eine bessere IT der lokalen Flottenmanager eine Entlastung im day-to-day Management.  Sie ist viel unterwegs, und möchte auch von unterwegs ihre Aufgaben im System erledigen können. Bis auf einige grobe Auswertungen möchte Sie mit dem System keinen domänenspezifischen Kontakt haben, wobei sie dennoch das Berechtigungsmanagement übernimmt.

#### Marlene
<img src="images/marlene.jpg" width="500">

* *Motivation das System zu benutzen:*
Marlene ist Verantwortlich für das Management der Flotte einer lokalen Zweigstelle des Unternehmens. Sie ist im operativen Geschäft tätig und führt damit oft Tätigkeiten im Interface des Systems aus. Marlene bekommt als Verantwortliche Ärger, wenn etwas nicht funktioniert oder nicht rechtzeitig erledigt wird, und ist deshalb sehr an einer nachrichtenbasierten Erweiterung des Systems interessiert, um derartigen Problemen aus dem Weg zu gehen.
* *Erwartungen an das System:*
Marlene möchte über dringende Vorfälle und anstehende Termine informiert werden, auch wenn Sie unterwegs ist. Dabei ist ihr wichtig, dass sie schnell alle wichtigen Informationen erhält, und sich so sofort um aufkommende Problemstellungen kümmern kann.

#### Bob
<img src="images/bob.jpg" width="500">

* *Motivation das System zu benutzen:*
Bob hat wenig Kontakt mit dem System, da er nicht im Flottenmanagement, sondern in der Buchhaltung tätig ist. Er kann nicht auf das Webinterface zugreifen. Allerings gab es in der Vergangenheit einige Vorfälle, bei denen Abrechnungen im Rahmen des Flottenmagements nicht korrekt durchgeführt wurden. Deshalb soll dieser Teil der Informationsübertragung automatisiert werden.
* *Erwartungen an das System:*
Bob möchte monatliche Zusammenfassungen mit den aktuellen abrechnungsrelevanten Daten erhalten. Diese sollen fehlerfrei und garantiert aktuell sein, und in einem tabellenformat, damit er sie einfach in sein Abrechnungssytem eintragen kann.

## Aufgabe 2 - Use-Cases

<!-- Template
| Name                 |
| Trigger              |
| Primärer Akteur      |
| Haupterfolgszenario  |
| Alternative Abläufe  |
| Vorbedingung         |
| Nachbedingung        |
-->
### Konzepte
* *Ereignis*: Ein externes Ereignis, durch welches mithilfe von Regeln eine Nachricht generiert wird.
* *Regel*: Eine vom Benutzer festgelegte Bedinung (potentiell mit innerer Struktur), welche aus Ereignissen Nachrichten generiert
* *Nachricht*: Eine durch ein Ereignis und eine Regel generierte Nachricht, die an einen Empfänger gesendet wird
* *Kommunikationskanal*: Ein Filter für Nachrichten, der Nachrichten mit einem Empfänger verknüpft, und diese dabei filtern und transformieren kann ("Filter" im Sinne einer Pipeline)
* *Empfänger*: Ein Benutzer des Systems, der eine Nachricht empfängt (erste Annäherung: Manager oder Administrator)

### Use-Case Liste
Use-Cases mit Zuweisung des Verantwortlichen / der Verantwortlichen für die Erstellung. 
Die Erstellung des UI-Prototyp ist nicht Aufgabe des Use-Case Verantwortlichen, und wird extra geregelt.

| Use-Case Name | Zugewiesene Person    | UI-Prototyp (Potentiell)
|---------------|-----------------------|------
| Administratoren: CRUD | Maxi |
| Manager: CRUD | Swantje | X 
| Zuweisen von Administratoren zu Fuhrparks | Fiona |
| Zuweisen von Manager zu Flotten |  Stefan |
| Regeln für Nachrichten: CRUD | Bernhard | X |
| Einteilung von Nachrichten in Kategorien (Verwaltung) | Stefan |
| Testen von Regeln anhand von vergangenen Daten ("sie hätten folgende Nachrichten mit dieser Regel vergangene Woche erhalten") | Maxi | X
| Kommunikationskanäle: CRUD | Swantje |
| Regeln für Flotte und Kommunikationskanal zuweisen | Fiona |
| Übersicht über die versendeten Nachrichten (mit jeweils verknüpften Regeln, Ereignissen, Kommunikationskanälen) | Stefan |
| Definition von "Virtuellen Flotten", die flottenübergreifend nach beliebigen Kriterien Fahrzeuge gruppiert werden, um Nachrichten zu dispatchen | Bernhard |
| Pushen eines Datenupdates an den Service (Datenupdate => Neue Ereignisse pushen) (je nach Push/Pull Modell, muss mit Kunden abgeklärt werden) | Maxi |


### Use-Case Grobbeschreibungen
Template (nach Übungsblatt):
| Name                 | Inhalt
|----------------------|------------------|
| Name                 |
| Trigger              |
| Primärer Akteur      |
| Haupterfolgszenario  |
| Alternative Abläufe  |
| Vorbedingung         |
| Nachbedingung        |

## Aufgabe 3 - UI-Prototypen
