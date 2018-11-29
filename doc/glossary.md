# Glossar

## Domänenmodell

### Notification-Rule

Regeln die der Flottenmanager anlegt, um bestimmte Ereignisse, die aus den Fahrzeugdaten hervorgehen, zu überwachen.

### Condition

Die Menge der Bedingungen die die Notification-Rule überwachen soll. Eine Bedingung kann entweder ein bestimmtes Ereignis sein (z.B. Service fällig) oder die logische Verknüfung (und, oder, nicht) solcher Ereignisse.
<!-- Composite Pattern -->

### Auslösung

Passt das Muster der Conditions einer Notification-Rule zu den eingehenden Fahrzeugdaten, so kommt es zur Auslösung.
Durch dieses Ereignis wird eine Notification an den Empfänger generiert.

### Notification

Konktete Nachricht an einen oder mehrere Empfänger, die über eine Auslösung informiert. Diese wird dann durch den BMW-Messagingservice an die entsprechenden Empfänger versentet.

### Aggregator

Sammelt alle Notifications einer Notification-Rule. Der Aggregator wird nach denen in ihme definierten Zeitpunkten geleert.

### Notificaiton-text-template

Ein Vorschlag für den Text der Notification.

- Fasst für jede Regel bestimmte Informationen zusammen.
- Bildet das Grundgerüst (Header, Footer) für eine Notification.

Das Template ist ggf. vom Flottenmanager anpassbar.

### Fahrzeugdatentyp

Die eingehenden Fahrzeugdaten werden bestimmten Gruppen (Status, Vertrag, Batterie) zugeordnet, je nachdem welche Information die Fahrzeugdaten übermitteln.