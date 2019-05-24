# Continuous Integration/Continuous Development Pipeline

Questa pipeline effettua un ciclo completo di build, test e deploy di una repository Github. L'eseguibile viene containerizzato su Docker e lanciato su una iI componenti sono i seguenti:

* Un host EC2 con una immagine Jenkins per l'esecuzione delle fasi di build, push e deploy della pipeline
* SonarQube come tool di analisi del codice, eseguito durante la fase di build
* Gatling come tool di load testing per web applications, eseguito durante la fase di test
* Kubernetes per la gestione dei container Docker

Attualmente la pipeline è funzionante per progetti Maven e Gradle, con immagini Docker basate su Linux. AWS al momento non supporta la virtualizzazione di container Windows.

