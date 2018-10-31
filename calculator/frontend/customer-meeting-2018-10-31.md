# Initial Meeting October 31, 2018

## Customers

* Kerstin Öchsner (MaibornWolff GmbH) - Lastenheft Fachliche und organisatorische Ansprechpartnerin, bei MaibornWollf seit 1 Jahr, Usability
* Luisa Lucius (BMW AG) - Lastenheft: Fachliche Ansprechpartnerin. Connected Drive seit 3 Jahren, Projektleiterin
* ... Name? (MaibornWolff GmbH) - Anforderungen, Priorisierung etc, seit 3 Jahren IT Consultant, Wirtschaftswissenschaftler, dann bei IBM

* Kommunikation (Fragen etc.): Jahrgangsverteiler und ST Projektleiter auf CC, Mails gerne an Kerstin Öchsner. Wegen Zeitramen lieber früher als später anfragen.

## Abstract
* Ziel: nicht immer diese Flottenübersichtsseite besuchen zu müssen, sondern Nachrichten über Events etc. bekommen
* Festes Regelwerk, aus denen man sich Sachen zurechtklicken kann, Wie oft, Von welchem Service
* Regel-Editor von Outlook als Bsp anschaun, Vorschläge als optionaler Zusatz?
* Daten kann man pullen, Test-Schnittstelle unklar ob pushen oder pullen oder was auch immer
* Dynamische Daten momentan in der Nacht immer daily updates
* In welchen Perioden sollen Daten angefordert werden? - ca 1x pro Stunde. Zum Testen evtl öfter
* Fuhrpark max. Größe? - 70.000, Testdaten: 200 Autos
* Wenn Benachrichtigungen nicht ankommen? Nicht darauf reagiert wird? - Loggen, in der Oberfläche sehen, wenn E-Mail nicht ankommt
* Flottenadmin wählt einmal aus über welchem Weg er immer kontaktiert werden will
* Einmalige Benachrichtigungen vs. periodisch neu versendet wenn sich nichts ändert
* Mock der Backend-Systeme selber machen anscheinend
* Es gibt von BMW Messaging Service, über diesen können wir die Sachen verschicken haben aber noch keine Schnittstellenspezifikation
* 2 Use Cases: 1. Außerplanmäßige Events 2. Regelmäßiger Abstand (jeden Montag 10 Uhr welche Fahrzeuge laufen aus, letzter Freitag im Monat Benachrichtigung über alle Fahrzeuge die im nächsten Monat in den Service müssen)
* Pro Regel Adressaten festlegen. Pro Regel mehrere E-Mail-Adressen und Handy-Nummern angeben, für Flottenadmin reicht einmaliges Hinterlegen einer Präferenz, dieser ist eh immer dabei?
* Wir sollen von einem einzelnem Admin für jetzt ausgehen
* Eine Liste von Regeln pro User (-> Jeder Manager hat seine eigenen Regeln, die auch der Admin nicht sehen muss)
* Flottenadmin darf nicth sehen was seine Flottenmanager machen
* Es gibt Flottenadmins und manager
* Wir müssen nicht verhindern dass von verschiedenen Usern gleiche Nachrichten an einzelne Leute versendet werden
* Tokens nur weitergeben im Backend, Frontend ist schon alles gemacht
* Mehrere Flottenmanager pro Flotte, nicht mehr als 2 realistischerweise
* Ein mehrere Flottenmanager kann mehrere Flotten managen, separate Konfiguration pro Flotte -> Beziehung Flotte <-> Manager ist N-zu-N, Ein Regelwerk bezieht sich auf Paar (Flotte, Manager)
* Für jede Regel einstellen für welche Flotte
* Dokumentation Englisch
* Soll abgegeben werden als Docker-Container der auf OpenShift läuft
* Müssen erstmal nicht manuell als Docker-Contaienr auf Heroku pushen, umstellen ist etwas für danach
* Performance und Usability: an ISO-Norm orientieren, soll an Look und Feel anschließen von bestehendem Frontend -> An größe der Flotten (bis 70.000) orientieren für Performanceeinschätzung
* Zugriff auf das Frontend: Wir bekommen eine Kopie des Frontends
* Integration mit existierenden Services wird von uns gemocked (z.B. von BMW Messaging-Service), mit Beispieldaten und Specs der Services:
* Logging? -> Wichtig. Aber keine genauen Anforderungen. Wann wurde was versendet. -> Logging Service? -> Nein, wird gerade erst konzipiert
* Organisatorisch? 3 Teams, gleiche Lösung, am Ende wird ein Sieger ausgewählt. Technische Rahmenbedingungen? Alle Use Cases abgedeckt? Benutzfreundlich? Passt von Look and feel zum Bestehendem? Gibt es zusätzliche Features?
* Styleguides bekommen wir
* Prometheus monitoring, OAuth Authentication oÄ wurde gesagt? Stimmt das? Hab den einen Punkt nicht ganz verstanden -> Ja wurde gesagt. Prometheus und OAuth+JWT
* 15.11. ist Termin, 7.12. Architektur-Workshop, nächster dann nach Weihnachten

## Questions

### Allgemein
- [x] Kotlin Support? -> Ja ist von MaibornWolff verwendet und kann unterstützt werden
- [x] GitHub Organisation? -> Nein
- [x] Heroku/ Travis Premium? -> Nein
- [x] Open Source -> kein Copy Left, d.h. nichts was kommerzielle Nutzung einschränkt
- [x] Bekommen wir Folien mit Personas etc.? -> Ja
- [x] Only production-grade libraries? (Exposed ORM!) -> Wir verwenden Hibernate.
- [x] Benachrichtigungen erstmal nur als Mails (SMS wurde auch genannt)? -> Modular aufbauen, Service von ihnen nutzen von denen wir noch keine Schnittstellenspezifikation haben
- [x] Was existieren für Vorstellungen wie die Nachrichten ausschauen sollen? Nur direkt Benachrichtigungen weiterleiten? -> Betreff von Email sollte wichtigste Infos beinhalten (Bsp: CBS Wert stimmt nicht, welches Fahrzeug, welche Floote) Innerhalb der E-Mail wichtigste Infos, .csv Anhang mit weiteren Infos zB Fahrzeugliste welche Fahrzeuge alle betroffen sind -> Frage: Link zur Aufbereitung im Portal? -> Link ja, warum nicht, aber .csv nicht schlecht
- [x] Seite soll eingebunden werden können in bestehendes Frontend, aber wird erstmal extra entwickelt
- [x] Gibt es React Component Package? -> Ja, bekommen wir wohl
- [x] Welche Browserversionen werden unterstützt -> Firefox, Chrome, Safari und Edge, jeweils die letzten drei. Fokus auf Chrome und Firefox.
- [x] Datenschutzanforderungen für uns erstmal egal
- [x] Responsiveness: Es soll auch auf dem Tablet nutzbar sein. min screen size = 768px
- [x] Anforderungen an E-Mails ->  Müssen wir uns keine Gedanken machen, es ist davon auszugehen das BMW-Service das macht (an Anforder)

### Auf später zurückgestellte Fragen
- [ ] Frameworkliste? -> Später abklären erst z.B. beim Architekturworkshop
- [ ] Other Frontend Technologies? Redux state mngmnt? TypeScript? -> Abklären bei Architekturworkshop
- [ ] "Um eine Weiterentwicklung und Anpassungen zu ermöglichen muss der Service auf einen kontinuierlichen Integrations-Prozess ausgelegt sein. Dies Betrifft v.a. die Migration des Datenbank Schemas" -> Also zwingend DB Schema Migration Tool? -> Das Schema Migration Tool wird vorgegeben, Information welches gibt es später, wahrscheinlich beim

### Von anderen Teams
* Alle Daten kommen übers Backend nur: Service-Termin fällig wird erst aktualisiert wenn der Service im Backend angekommen ist, momentan nur über Notizen
* Benachrichtigungseinstellung
* Benachrichtigungen anzeigen - keine Priorität aber anzeigen + als erledigt markieren usw Nice2Have, keinerlei Priorität
* Prioritätsstufen von Nachrichten? - Kritisch und nicht-kritisch
* Alltag des Flottenmanagers? -> Computer-Person, aber sitzt nicht nur vor dem Rechner
* Regelwerk für Nachrichten -> Es gibt am Anfang ein festes / initiales, aber welches geändert werden kann
* Testdaten -> Wir arbeiten nicht auf echten Daten, sondern auf Testdaten die von MW bereitgestellt werden
* Sollen Nachrichten nur an bestimmte Personen oder auch zB an Fahrer gesendet werden? - Nicht an Fahrer, dieser ist unbekannt
* 2. Addressat Bsp Use Case? Mit Kollegen zusammen Arbeit teilen, Urlaubsvertretung usw -> Es gibt Flottenadministrator (super-admin) und Flottenmanager (für einzelne Flotten). D.h unterschiedliche Nachrichtentypen auf Regeln festlegen können, wer jeweils benachrichtigt wird, und wer z.B auf CC sein soll

### Domäne
- [ ] Daten die "Fahrzeugzustand" definieren? -> Erechnet sich aus 7 verschiedenen Werten: Motoröl, Brems..., ...

### Zu Demo
- [ ]  Konkrete Einstellung oder Learning von Nachrichten? -> Flottenmanager soll konkret und explizit einstellen, welche Benachrichtigungen er bekommen soll
- [ ] SMS Service Provider -> Twilio? -> Nein, durch BMW Internen Service (E-mail und SMS)
- [ ] Frontend Components als package verfügbar?

## Notes

* Idee: Als "Wow" feature: OAuth anbindung von 3rd party apps, wie if-this-then-that für benachrichtigungen
