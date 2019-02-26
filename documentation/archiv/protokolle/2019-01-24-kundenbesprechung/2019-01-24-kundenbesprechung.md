# Kundenbesprechung: 24. Januar 2019

## Anwesende
* Julian Hanke
* Benedikt Eberhardinger
* Stefan Grafberger
* Maximilian Kuschewski
* Fiona Guerin

## Frage: CRON
* CRON-Expression sollten eher im Backend als im Frontend aufbereitet werden.
* Die Expression sollte strukturiert gespeichert werden (nicht nur als String).
* CRON ist als Format okay/gut.

## Frage: Deployment/Service-Replikation
* Am besten ist eine Absprache mit den anderen Teams. Team „Augsburg“ verwendet Subsets.
* Evtl. Ist diese Anforderung nicht relevant für die Benotung.
* Ansätze: (1) Orchestration von extern, (2) Kapselung der Services, um Abhängigkeiten zu verhindern.
* Ansätze zum Testen der Replikation: Nach Killen eines oder mehrerer Services wird überprüft, ob der Rest noch funktioniert. Dies lässt sich automatisieren. Evtl. gibt es dafür schon Tools, z.B. JMeter. 
*  Tests in der Heroku-Konsole wären gut. 

## Feedback: Tests im Backend
* Die Tests sind grundsätzlich in Ordnung: Ein Test wird pro Funktion durchgeführt, Abhängigkeiten bei der Integration sollen minimiert werden. 
* Testdaten sollten variiert werden (die Verwendung nur eines Testdatum deckt einfache, generische Fehler auf). So sollten Grenzwerttests (Instanzen für verschiedene Wertebereich testen, sinnvolle Wertebereich testen, verschiedene Abläufe des Programms testen) und Negativtests verwendet werden.
* Eine späte Testbarkeit (wegen starker Abhängigkeit) impliziert Probleme beim Schnitt der Komponente. 
* Lasttests stehen nicht im Fokus, weil die Skalierung stark von Heroku abhängt.

## Feedback: Tests im Frontend
* Das Frontend sollte getestet werden, auch wenn es von Seiten MbW nicht unbedingt erfordert wird. 
* Selenium-Tests sind eine gute Wahl, um das Frontend zu testen, dies sollte für einen Use Case gemacht werden. Dabei bietet sich entweder Caption Replay oder ein generisches Vorgehen an.
* Manuelle Tests sind auch üblich, diese sollten für jeden Use Case durchgeführt werden. 
* Für den gesamten Service können wir JMeter verwenden. 

## Organisatorisches
* Spezialisten-Bildung ist normal/in Ordnung. 
* Wir sollten unsere Meilensteine mit ihren Issues updaten.

## Ausblick
* Das Thema des nächsten Meetings ist offen.
* Das Thema das übernächsten Meetings ist Visualisierung.

