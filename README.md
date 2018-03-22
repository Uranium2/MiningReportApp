# MiningReportApp
Server Report Android App


# Prérequis
Installer MSI AfterBurner et une API custom pour communiquer avec elle:
  http://rivatuner.doomdealer.com/afterburner/MSIAfterburner.NET/1.1.1/MSIAfterburner.NET.dll
Lancer un serveur Web sur votre machine.
Changer l'addresse IP et le Path dans le fichier:
MiningReportApp/app/src/main/java/com/example/tavernier/miningcharts/AsyncExample.java
    "String request = "http://<YOUR_IP>:<YOUR_PORT>/<YOUR_PATH>";"
