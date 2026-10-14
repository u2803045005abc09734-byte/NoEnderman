# Endit – Minecraft Java 26.3 / Fabric (GitHub Build)

Dieses Projekt enthält die Quelldateien und den GitHub-Actions-Workflow für Endit für Minecraft 26.3 mit Fabric. Version 1.0.2 fügt zusätzlich hinzu, dass im End keine Endermen spawnen.

## GitHub Build

1. Alle Inhalte dieses Ordners in das Hauptverzeichnis deines GitHub-Repositories hochladen. Wichtig: `.github/workflows/build.yml` muss enthalten sein.
2. Änderungen committen und den Tab **Actions** öffnen.
3. Nach einem erfolgreichen Lauf das Artefakt **endit-fabric-26.3** herunterladen. Darin liegt die gebaute JAR.

## Befehl

`/setendspawn <x y z>`

Der Befehl wird nur für Game-Master-Berechtigungen (Operator-Rechte) freigeschaltet.

## Endermen im End deaktivieren

Version 1.0.2 verhindert, dass Endermen im End neu hinzugefügt werden, und entfernt bereits vorhandene Endermen aus geladenen Bereichen regelmäßig. In der Oberwelt und im Nether bleiben Endermen unverändert. Diese Regel blockiert auch das manuelle Spawnen von Endermen im End (z. B. per Spawn-Ei oder `/summon`).

## Hinweis

Der Build und das Verhalten im Spiel müssen über GitHub Actions und anschließend auf einem Testserver überprüft werden. Vor der Installation auf dem Hauptserver bitte ein Backup der Welt erstellen.


Hinweis: Die Mixin-Konfiguration verwendet `JAVA_25`, passend zu Minecraft 26.3 und dem Java-25-Bytecode.
