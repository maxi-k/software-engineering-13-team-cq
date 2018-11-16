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
Anna erhofft sich, durch eine bessere IT der lokalen Flottenmanager eine Entlastung im day-to-day Management.  Sie ist viel unterwegs, und möchte auch von unterwegs ihre Aufgaben im System erledigen können. Sie möchte vom System ausschließlich grobe Auswertungen erhalten, ohne sich tief in das System einarbeiten zu müssen.

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
| Regeln für Nachrichten: Create, Update | Stefan | X |
| Regeln für Nachrichten: Read | Maxi | |
| Regeln für Nachrichten: Delete | Maxi | |
| Regelkategorien: CRUD | Fiona | X |
| Allgemeine Einstellungen: RU (Overall Template etc.) | Swantje |  |
| Übersicht über die versendeten Nachrichten (mit jeweils verknüpften Regeln, Ereignissen) ("Log") | Bernhard | X |


### Erstellung UI Prototypen


<!-- | Testen von Regeln anhand von vergangenen Daten ("sie hätten folgende Nachrichten mit dieser Regel vergangene Woche erhalten") | Maxi | X bei Create, Update -->

### Use-Case Grobbeschreibungen
Template (nach Übungsblatt):

| Name                 | Inhalt           
|----------------------|------------------|
| Name                 | ...
| Trigger              | ...
| Primärer Akteur      | ...
| Haupterfolgszenario  | ...
| Alternative Abläufe  | ...
| Vorbedingung         | ...
| Nachbedingung        | ...

#### Use-Case: Regeln für Nachrichten: Create, Update
| Name                 | Inhalt           
|----------------------|------------------|
| Name                 | Regeln für Nachrichten: Create/Update
| Trigger              | Der Manager möchte eine neue Regel anlegen oder eine bestehende bearbeiten
| Primärer Akteur      | Flottenmanager
| Haupterfolgszenario  | <ol><li>Der Manager öffnet die Regelübersicht im System </li><li> Der Manager wählt eine der Kategorien aus</li><li> Der Manager wählt eine der angezeigten Regeln aus</li><li>Das System zeigt die Detailinformationen der ausgewählten Regel an.</li><li>Der Manager sieht eine Detailansicht der Regel</li><li>Der Manager bearbeitet Informationen wie den Trigger der Regel und/oder den Nachrichtentext der erzeugten Nachricht</li><li>Der Manager speichert den neuen Stand sobald der zulässiger Zustand den er haben wollte erreicht wurde</li><li>Der Manager verlässt das System</li></ol>
| Alternative Abläufe  | <ul><li>(4.a) Der Manager legt eine neue Regel an.</li></ul>
| Vorbedingung         | Es existiert mindestens eine Kategorie im System, Der Flottenmanager ist authentifiziert
| Nachbedingung        | Die Daten der Regeln sind kohärent und vollständig

#### Use-Case: Regeln für Nachrichten: Read
| Name                 | Inhalt           
|----------------------|------------------|
| Name                 | Regeln für Nachrichten: Read
| Trigger              | Der Manager möchte eine bestimme Regel ansehen
| Primärer Akteur      | Flottenmanager
| Haupterfolgszenario  | <ol><li>Der Manager öffnet die Regelübersicht im System </li><li> Der Manager wählt eine der angezeigten Regeln aus</li><li>Das System zeigt die Detailinformationen der ausgewählten Regel an.</li><li>Der Manager sieht die Detailinformationen ein</li><li>Der Manager verlässt das System</li></ol>
| Vorbedingung         | Es existiert mindestens eine Regel im System, der Flottenmanager ist authentifiziert
| Nachbedingung        | Die Daten der Regeln sind kohärent

#### Use-Case: Regeln für Nachrichten: Delete
| Name                 | Inhalt           
|----------------------|------------------|
| Name                 | Regeln für Nachrichten: Delete
| Trigger              | Der Manager möchte eine bestimme Regel löschen
| Primärer Akteur      | Flottenmanager
| Haupterfolgszenario  | <ol><li>Der Manager öffnet die Regelübersicht im System </li><li> Der Manager wählt eine der angezeigten Regeln aus</li><li>Das System zeigt die Detailinformationen der ausgewählten Regel an.</li><li>Der Manager löscht die ausgewählte Regel.</li><li>Das System fragt nach, ob die Regel wirklich gelöscht werden soll.</li><li>Der Manager verlässt das System</li></ol>
| Alternative Abläufe  | <ul><li>(4-5a) Der Manager deaktiviert die ausgewählte Regel lediglich.</li></ul>
| Vorbedingung         | Es existiert mindestens eine Regel im System, der Flottenmanager ist authentifiziert
| Nachbedingung        | Die Regel ist gelöscht oder deaktiviert

#### Use-Case: Regelkategorien CRUD
| Name                 | Inhalt           
|----------------------|------------------|
| Name                 | Regelkategorien: CRUD
| Trigger              | Der Manager möchte eine Regelkategorie anlegen, bearbeiten, aktualisieren oder löschen
| Primärer Akteur      | Flottenmanager
| Haupterfolgszenario  | <ol><li>Der Manager öffnet die Kategorienübersicht im System </li><li> Der Manager wählt eine der angezeigten Kategorien aus</li><li>Das System zeigt die Detailinformationen der ausgewählten Kategorie an.</li><li>Der Manager bearbeitet die ausgewählte Kategorie.</li><li>Der Manager speichert die bearbeiteten Informationen</li><li>Das System gibt eine Erfolgsmeldung aus</li></ol>
| Alternative Abläufe  | <ul><li>(2.a) <ol><li>Der Manager signalisiert dem System, dass er eine neue Kategorie anlegen möchte</li><li>Das System bietet die Möglichkeit, eine neue Kategorie anzulegen</li><li>Der Manager gibt die Informationen zur Kategorie ein</li><li>Der Manager speichert die Kategorie</li><li>Das System gibt eine Erfolgsmeldung aus.</li></ol></li><li>(4.a)<ol><li>Der Manager betrachtet lediglich die Detailinformationen der Kategorie, ohne sie zu bearbeiten</li></ol></li><li>(4.b)<ol><li>Der Manager löscht die gewählte Kategorie</li><li>Das System fragt nach, ob die Kategorie wirklich gelöscht werden soll und weißt auf ggf. vorhandene Regeln innerhalb dieser Kategorie hin</li><li>Das System löscht die Kategorie falls vom Benutzer nicht abggebrochen wird.</li><li>Das System gibt eine Erfolgsmeldung aus.</li></ol></li></ul>
| Vorbedingung         | Der Flottenmanager ist authentifiziert
| Nachbedingung        | Die Daten befinden sich in einem konsistenten Zustand

#### Use-Case: Übersicht über die versendeten Nachrichten
| Name | Inhalt |
| ---- | ------ |
| Name | Übersicht über die versendeten Nachrichten |
| Trigger | Der Manager möchte die versendeten Nachrichten kontrollieren, z.B. beim Debugging seiner Regeln oder der dem Nachvollziehen von Geschehnissen anhand der Events. |
| Primärer Akteur | Manager |
| Haupterfolgsszenario | <ol><li>Der Manager besucht das Nachrichten-Log-Interface. </li><li>Das System stellt Übersicht der vom System versendeten protokollierten Nachrichten dar. </li><li> Der Manager analyisiert diese. Optional kann der Manager eine Detaildarstellung von einzelnen versendeten Nachrichten anfordern. </li><li> Das System stellt diese dar. </li><li> In den Detaildarstellungen informiert sich der Manger, zusätzlich zu den Informationen der Zusammenfassung, über die die Nachricht generierende Regel mitsamt der erfüllenden Belegung der Regel, sowie dem ausführlichen Text der Nachricht. </li><li> Der Manager schließt die Detaildarstellungen anschließend wieder. </li><li>Hierdurch kehrt er wieder zu den Zusammenfassungen der Nachrichten zurück (siehe 2.). </li><li>Der Manager beendet die Betrachtung.</li> |
| Alternative Abläufe | <ul><li>(*.a) Der Manager beendet die Betrachtung zu einem beliebigen Zeitpunkt.</li></ul> |
| Vorbedingungen | Manager ist authentifiziert. |
| Nachbedingung | Zustand des Systems ist identisch zu Vorbedingungen, d.h. keine Änderungen am System wurden ausgeführt. |

#### Use-Case: Allgemeine Einstellungen
<!--
| Name                 | Inhalt           
|----------------------|------------------|
| Name                 | Allgemeine Einstellungen
| Trigger              | Der Manager möchste die allgemeinen Nachrichteneinstellungen einsehen oder bearbeiten
| Primärer Akteur      | Flottenmanager
| Haupterfolgszenario  | <ol><li>Der Manager wählt "Einstellungen"</li><li>Das System zeigt die Einstellungen des Nachrichtensystems an.</li><li>Der Manager sieht die Einstellungen ein.</li></ol>
| Alternative Abläufe  |<ul><li>(3.a)<ol><li></li>Der Manager bearbeit eine Einstellung</li><li>Der Manager speichert die Einstellungen</li></ol><ul><li>(3.a.1)<ol><li>Der Manager bearbeitet eine weitere Einstellung</li></ol></li></ul></li></ul>
| Vorbedingung         | Der Flottenmanager ist authentifiziert
| Nachbedingung        | Alle Einstellungen sind kohärent 
-->

| Name | Lesen allgemeiner Einstellungen
|----------------------| -----------------------|
| Trigger | Flottenmanager möchte einen Überblick über seine bestehenden Einstellungen bekommen
| Primärer Akteur | Flottenmanager (FM)
| Haupterfolgsszenario | <ol><li> Dem FM werden seine groben Einstellungen und Verweise auf feingranularere Informationen angezeigt </li><li>FM ist mit allg. Einstellungen zufrieden und verlässt System.</li></ol>
| Alternative Abläufe  | <ul><li>(*) FM verlässt System: Einstellungen bleiben unverändert <li>(2a.)FM möchte detailliertere Informationen zu den bestehenden Regeln und lässt sich zu diesen weiterleiten </li><li>(3a.) FM ist mit den Einstellungen nicht zufrieden und möchte diese ändern -> Übergang zu *Verändern allg. Einstellungen*</li></ul>
| Vorbedingung | FM ist am System authentifiziert
| Nachbedingung | Einstellungen wurden nicht verändert

| Name | Verändern allgemeiner Einstellungen
|----------------------| -----------------------|
| Trigger | Flottenmanager möchte bestehende Einstellungen anpassen
| Primärer Akteur | Flottenmanager (FM)
| Haupterfolgsszenario | <ol><li>Dem FM werden seine groben Einstellungen und Verweise auf feingranularere Informationen angezeigt </li><li>FM passt eine Einstellung an </li><li>FM speichert Einstellung </li><li>Die neue Einstellung ist aktiv </li><li>FM verlässt System</li></ol>
| Alternative Abläufe | <ul><li>(1a-4a)FM verlässt System: Einstellungen behalten alten Zustand bei</li><li>(6a.) FM möchte weitere Einstellungen ändern: *Weiter mit 3.*</li></ul>
| Vorbedingung | FM ist am Sytem authentifiziert
| Nachbedingung | Einstellungen sind aktualisiert, aktiv und gespeichert

## Aufgabe 3 - UI-Mockups
Die UI Prototypen sind unter
https://www.figma.com/file/ab3V6oY85VAM9xv1TD7GRzdm/Messaging-Application?node-id=26%3A160 einsehbar
