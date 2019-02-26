
## Use-Cases

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
| Nachbedingung        | Der Regel wurden die vom User ausgewählte Aggregationsstrategie, Regel-Bedingungen, Event-Quellen und Text-Template ggf. erstellt und zugewiesen. Die Regel ist im System aktiv, falls nicht vom User explizit abgewählt.

#### Use-Case: Regeln für Nachrichten: Read
| Name                 | Inhalt
|----------------------|------------------|
| Name                 | Regeln für Nachrichten: Read
| Trigger              | Der Manager möchte eine bestimme Regel ansehen
| Primärer Akteur      | Flottenmanager
| Haupterfolgszenario  | <ol><li>Der Manager öffnet die Regelübersicht im System </li><li> Der Manager wählt eine der angezeigten Regeln aus</li><li>Das System zeigt die Detailinformationen der ausgewählten Regel an.</li><li>Der Manager sieht die Detailinformationen ein</li><li>Der Manager verlässt das System</li></ol>
| Vorbedingung         | Es existiert mindestens eine Regel im System, der Flottenmanager ist authentifiziert
| Nachbedingung        | Die Regel wurde nicht verändert.

#### Use-Case: Regeln für Nachrichten: Delete
| Name                 | Inhalt
|----------------------|------------------|
| Name                 | Regeln für Nachrichten: Delete
| Trigger              | Der Manager möchte eine bestimme Regel löschen
| Primärer Akteur      | Flottenmanager
| Haupterfolgszenario  | <ol><li>Der Manager öffnet die Regelübersicht im System </li><li> Der Manager wählt eine der angezeigten Regeln aus</li><li>Das System zeigt die Detailinformationen der ausgewählten Regel an.</li><li>Der Manager löscht die ausgewählte Regel.</li><li>Das System fragt nach, ob die Regel wirklich gelöscht werden soll.</li><li>Der Manager verlässt das System</li></ol>
| Alternative Abläufe  | <ul><li>(4-5a) Der Manager deaktiviert die ausgewählte Regel lediglich.</li></ul>
| Vorbedingung         | Es existiert mindestens eine Regel im System, der Flottenmanager ist authentifiziert
| Nachbedingung        | <ul><li>Die Regel wurde gelöscht und ist im System nicht mehr aktiv.</li> Die zugeordnete Aggregationsstrategie, Regel-Bedingung, Event-Quelle und Text-Template wurden gelöscht.</li></ul>

#### Use-Case: Regelkategorien CRUD
| Name                 | Inhalt
|----------------------|------------------|
| Name                 | Regelkategorien: CRUD
| Trigger              | Der Manager möchte eine Regelkategorie anlegen, bearbeiten, aktualisieren oder löschen
| Primärer Akteur      | Flottenmanager
| Haupterfolgszenario  | <ol><li>Der Manager öffnet die Kategorienübersicht im System </li><li> Der Manager wählt eine der angezeigten Kategorien aus</li><li>Das System zeigt die Detailinformationen der ausgewählten Kategorie an.</li><li>Der Manager bearbeitet die ausgewählte Kategorie.</li><li>Der Manager speichert die bearbeiteten Informationen</li><li>Das System gibt eine Erfolgsmeldung aus</li></ol>
| Alternative Abläufe  | <ul><li>(2.a) <ol><li>Der Manager signalisiert dem System, dass er eine neue Kategorie anlegen möchte</li><li>Das System bietet die Möglichkeit, eine neue Kategorie anzulegen</li><li>Der Manager gibt die Informationen zur Kategorie ein</li><li>Der Manager speichert die Kategorie</li><li>Das System gibt eine Erfolgsmeldung aus.</li></ol></li><li>(4.a)<ol><li>Der Manager betrachtet lediglich die Detailinformationen der Kategorie, ohne sie zu bearbeiten</li></ol></li><li>(4.b)<ol><li>Der Manager löscht die gewählte Kategorie</li><li>Das System fragt nach, ob die Kategorie wirklich gelöscht werden soll und weißt auf ggf. vorhandene Regeln innerhalb dieser Kategorie hin</li><li>Das System löscht die Kategorie falls vom Benutzer nicht abggebrochen wird.</li><li>Das System gibt eine Erfolgsmeldung aus.</li></ol></li></ul>
| Vorbedingung         | Der Flottenmanager ist authentifiziert
| Nachbedingung        | <ul><li>Alle Regeln sind genau einer Regelkategorie zugewiesen.</li> <li>Regelkategorie.name und Regelkategorie.description sind gesetzt und nicht leer.</li></ul>

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
| Nachbedingung | Einstellungen sind aktualisiert, aktiv und gespeichert.

#### Use-Case: Versand aggregierter Nachrichten
| Name | Inhalt |
| ---- | ------ |
| Name | Versand aggregierter Nachrichten |
| Primärer Akteur | System |
| Vorbedingungen | Eine Regel definiert regelmäßige Zeitpunkte, zu denen das System aggregierte Informationen über spezifische Fahrzeugzustände versendet. Vorgegeben sind dabei der Nachrichtenempfänger, ein zu verwendender Kommunikationskanal sowie eine Vorlage zum Verfassen der Nachricht. |
| Trigger | Ein definierter Zeitpunkt ist eingetreten. |
| Haupterfolgsszenario | <ol><li>Das System fragt Informationen über die spezifizierten Fahrzeugzustände ab. </li><li>Das System bereitet die Daten in einem Bericht auf: Es greift auf ein definiertes Nachrichten-Muster zurück und pflegt die Daten in diese Vorlage ein.</li><li> Das System konkretisiert den Nachrichtenversand. Es erfragt den registrierten Empfänger der Nachricht sowie die Entscheidung, ob die Nachricht per SMS oder E-Mail versendet werden soll. </li><li> Das System erstellt eine entsprechende Nachricht. </li><li> Das System versendet die Nachricht. </li> |
| Nachbedingung | Das System hat die spezifizierte Nachricht an den Flottenmanager versendet. Der Zustand des Systems ist identisch zu Vorbedingungen. |

#### Use-Case: Versand kritischer Meldungen
| Name | Inhalt |
| ---- | ------ |
| Name | Versand kritischer Meldungen |
| Primärer Akteur | System |
| Vorbedingungen | Eine Regel definiert kritische Fahrzeugzustände, die das System an den Flottenmanager melden soll. Vorgegeben sind dabei der Nachrichtenempfänger, ein zu verwendender Kommunikationskanal sowie eine Vorlage zum Verfassen der Nachricht. |
| Trigger | Ein definierter Fahrzeugzustand ist eingetreten. |
| Haupterfolgsszenario | <ol><li>Das System fragt Daten über relevante Fahrzeugzustände ab. </li><li>Auf Basis dieser Daten entscheidet das System, dass ein kritischer Fahrzeugzustand eingetreten ist.</li><li>Das System erstellt darüber eine Meldung: Es greift auf ein definiertes Nachrichten-Muster zurück und pflegt die Daten in diese Vorlage ein. </li><li>Das System konkretisiert den Nachrichtenversand. Es erfragt den registrierten Empfänger der Nachricht sowie die Entscheidung, ob die Nachricht per SMS oder E-Mail versendet werden soll. </li><li> Das System erstellt eine entsprechende Nachricht. </li><li> Das System versendet die Nachricht. </li> |
| Nachbedingung | Das System hat die spezifizierte Nachricht an den Flottenmanager versendet. Der Zustand des Systems ist identisch zu Vorbedingungen. |
