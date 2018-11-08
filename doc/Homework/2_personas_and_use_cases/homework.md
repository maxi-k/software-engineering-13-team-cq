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
| Hobbies     | Marathon       | Yoga        | Fußball & Bier
| Motto       | Wer Rastet der Rostet | Der frühe Vogel fängt den Wurm, aber der späte Wurm verpasst den Vogel! | Prost! 
| Familie     | Verheirtatet | Geschieden | Ledig
| Kinder      | 2            | 4                | 0
| Computeraffinität | Mittel    | Hoch        | Gering
| Computerkenntnisse | Sehr Gut | Gering      | Mittel 

### Detaillierung

#### Anna
![Anna](images/anna.jpg){
    width: 200px;
    float: right;
}
* *Motivation das System zu benutzen:*
Anna ist für ganz Deutschland die technisch Verantwortliche für die Flotte des Unternehmens, und ist damit stark belastet. Sie hat auch ohne micro-management der lokalen Flottenmanager genug zu tun, und möchte, dass diese eine möglichst gute IT haben, um die meisten Probleme selbst schnell, effektiv und gut lösen zu können.
* Erwartungen an das System:
Anna erhofft sich, durch eine bessere IT der lokalen Flottenmanager eine Entlastung im day-to-day Management.  Sie ist viel unterwegs, und möchte auch von unterwegs ihre Aufgaben im System erledigen können. Bis auf einige grobe Auswertungen möchte Sie mit dem System keinen domänenspezifischen Kontakt haben, wobei sie dennoch das Berechtigungsmanagement übernimmt.

#### Marlene
![Marlene](./images/marlene.jpg =250x)
* *Motivation das System zu benutzen:*
Marlene ist Verantwortlich für das Management der Flotte einer lokalen Zweigstelle des Unternehmens. Sie ist im operativen Geschäft tätig und führt damit oft Tätigkeiten im Interface des Systems aus. Marlene bekommt als Verantwortliche Ärger, wenn etwas nicht funktioniert oder nicht rechtzeitig erledigt wird, und ist deshalb sehr an einer nachrichtenbasierten Erweiterung des Systems interessiert, um derartigen Problemen aus dem Weg zu gehen.
* *Erwartungen an das System:*
Marlene möchte über dringende Vorfälle und anstehende Termine informiert werden, auch wenn Sie unterwegs ist. Dabei ist ihr wichtig, dass sie schnell alle wichtigen Informationen erhält, und sich so sofort um aufkommende Problemstellungen kümmern kann.

#### Bob
![Bob](images/bob.jpg =250x)
* *Motivation das System zu benutzen:*
Bob hat wenig Kontakt mit dem System, da er nicht im Flottenmanagement, sondern in der Buchhaltung tätig ist. Er kann nicht auf das Webinterface zugreifen. Allerings gab es in der Vergangenheit einige Vorfälle, bei denen Abrechnungen im Rahmen des Flottenmagements nicht korrekt durchgeführt wurden. Deshalb soll dieser Teil der Informationsübertragung automatisiert werden.
* *Erwartungen an das System:*
Bob möchte monatliche Zusammenfassungen mit den aktuellen abrechnungsrelevanten Daten erhalten. Diese sollen fehlerfrei und garantiert aktuell sein, und in einem tabellenformat, damit er sie einfach in sein Abrechnungssytem eintragen kann.
