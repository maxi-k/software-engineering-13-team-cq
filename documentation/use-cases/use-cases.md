
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
* *Empfänger*: Ein Benutzer des Systems, der eine Nachricht empfängt (erste Annäherung: Manager oder Administrator)

### Use-Case Liste

- Regeln für Nachrichten: Create, Update
- Regeln für Nachrichten: Read
- Regeln für Nachrichten: Delete
- Versand aggregierter Nachrichten
- Versand kritischer Meldungen

<!-- ### Erstellung UI Prototypen ->>


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
| Trigger              | Der Manager möchte eine neue Regel anlegen oder eine Bestehende bearbeiten
| Primärer Akteur      | Flottenmanager
| Haupterfolgszenario  | <ol><li>Der Manager öffnet die Regelübersicht im System </li><li> Der Manager wählt eine der angezeigten Regeln aus</li><li>Das System zeigt die Detailinformationen der ausgewählten Regel an.</li><li>Der Manager sieht eine Detailansicht der Regel</li><li>Der Manager bearbeitet Informationen wie den Name der Regel und/oder die Bediungung der Regel.</li><li>Der Manager speichert den neuen Stand der Regel, sobald ein zulässiger Zustand erreicht wurde.</li><li>Der Manager verlässt das System.</li></ol>
| Alternative Abläufe  | <ul><li>(4.a) Der Manager legt eine neue Regel an.</li></ul>
| Vorbedingung         | Der Flottenmanager ist authentifiziert
| Nachbedingung        | Der Regel wurden die vom User ausgewählte Aggregationsstrategie, Regel-Bedingungen, Event-Quellen und ggf. erstellt und zugewiesen.

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
| Haupterfolgszenario  | <ol><li>Der Manager öffnet die Regelübersicht im System.</li><li> Der Manager wählt eine der angezeigten Regeln aus.</li><li>Das System zeigt die Detailinformationen der ausgewählten Regel an.</li><li>Der Manager löscht die ausgewählte Regel.</li><li>Das System fragt nach, ob die Regel wirklich gelöscht werden soll.</li><li>Der Manager bestätigt den Löschungsvorgang</li><li>Der Manager verlässt das System.</li></ol>
| Alternative Abläufe  | <ul><li>(4-5a) Der Manager entscheidet sich gegen das Löschen der Regel.</li></ul>
| Vorbedingung         | Es existiert mindestens eine Regel im System, der Flottenmanager ist authentifiziert
| Nachbedingung        | <ul><li>Die Regel wurde gelöscht und ist im System nicht mehr aktiv.</li> Die zugeordnete Aggregationsstrategie, Regel-Bedingung und Event-Quelle wurden gelöscht.</li></ul>

#### Use-Case: Versand aggregierter Nachrichten
| Name | Inhalt |
| ---- | ------ |
| Name | Versand aggregierter Nachrichten |
| Primärer Akteur | System |
| Vorbedingungen | Eine Regel definiert regelmäßige Zeitpunkte, zu denen das System aggregierte Informationen über spezifische Fahrzeugzustände versendet. Vorgegeben sind dabei der Nachrichtenempfänger, ein zu verwendender Kommunikationskanal sowie eine Vorlage zum Verfassen der Nachricht. |
| Trigger | Ein definierter Zeitpunkt ist eingetreten. |
| Haupterfolgsszenario | <ol><li>Das System fragt Informationen über die spezifizierten Fahrzeugzustände ab.</li><li>Das System bereitet die Daten in einem Bericht auf.</li><li>Das System entscheidet, an welche Personen, und ob die Nachricht jeweils per SMS oder E-Mail versendet werden soll.</li><li>Das System versendet die entsprechenden Nachrichten.</li></ol> |
| Nachbedingung | Das System hat die spezifizierten Nachrichten an die Empfänger versendet. |

#### Use-Case: Versand kritischer Meldungen
| Name | Inhalt |
| ---- | ------ |
| Name | Versand kritischer Meldungen |
| Primärer Akteur | System |
| Vorbedingungen | Eine Regel definiert kritische Fahrzeugzustände, die das System an den Flottenmanager melden soll. |
| Trigger | Ein definierter Fahrzeugzustand ist eingetreten. |
| Haupterfolgsszenario | <ol><li>Das System fragt Daten über relevante Fahrzeugzustände ab.</li><li>Auf Basis dieser Daten entscheidet das System, dass ein kritischer Fahrzeugzustand eingetreten ist.</li><li>Das System bereitet die Daten in einem Bericht auf.</li><li>Das System entscheidet, an welche Personen, und ob die Nachricht jeweils per SMS oder E-Mail versendet werden soll.</li><li>Das System versendet die entsprechenden Nachrichten.</li></ol> |
| Nachbedingung | Das System hat die spezifizierte Nachricht an den Flottenmanager versendet. |
