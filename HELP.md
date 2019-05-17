# Continuous Integration/Continuous Development Pipeline

Questa pipeline effettua un ciclo completo di build, test e deploy di una repository Github. L'eseguibile viene containerizzato su Docker e lanciato su una iI componenti sono i seguenti:

* AWS Codepipeline come base per la struttura della pipeline
* Un host EC2 con una immagine Jenkins per l'esecuzione delle fasi di build, push della pipeline
* SonarQube come tool di analisi del codice, eseguito durante la fase di build
* Gatling come tool di load testing per web applications, eseguito durante la fase di test
* Kubernetes per la gestione dei container Docker

Attualmente la pipeline è funzionante per progetti Maven, con immagini Docker basate su Linux. AWS al momento non supporta la virtualizzazione di container Windows.

### Accessso alla pipeline

La pipeline creata su AWS è disponibile su: https://eu-west-1.console.aws.amazon.com/codesuite/codepipeline/pipelines/cicdapp/view?region=eu-west-1. Affinché venga lanciato un nuovo ciclo di sviluppo, si deve fare un push di una commit sulla repository oppure dalla pagina di CodePipeline, andare sulla pipeline *cicdapp* e cliccare su Relase Change. Gli step di build e test sono eseguiti dal servizio Jenkins presente all'url: http://52.211.223.238:8080. E'necessario loggarsi come utente di Github per accedere all'url e monitorare i progressi delle build.

L'artefatto costruito in fase di build viene immesso in una immagine Docker inviata a una repository di AWS Elastic Container Registry (https://eu-west-1.console.aws.amazon.com/ecr/repositories/cicdapp/?region=eu-west-1). 

Una volta completata la build e il testing, è possibile osservare i risultati relativi a SonarQube e Gatling rispettivamente su: http://52.211.223.238:9000 e http://52.211.223.238:8080/job/cicdapp-test/gatling/ (i singoli report sono visualizzabili in basso come file HTML).

Per l'ultima fase del ciclo, CodeDeploy si occupa di immettere l'immagine Docker dal registro su una istanza EC2 attraverso un file di orchestrazione (appspec.yml) presente nella root della repository Git. E'possibile monitorare il processo di deploy su https://eu-west-1.console.aws.amazon.com/codesuite/codedeploy/deployments?region=eu-west-1 (da notare che CodeDeploy impiega un tempo fisso di 5 minuti per chiudere e riaprire il traffico dell'istanza EC2 durante l'installazione ed esecuzione del container Docker e dunque questa ultima fase può durare intorno ai 15 minuti).

Il monitoraggio dei container Docker è effettuabile tramite Kubernetes. La dashboard è presente sull'url: https://api-cicd-k8s-local-gtgus3-1144388551.eu-west-1.elb.amazonaws.com/api/v1/namespaces/kube-system/services/https:kubernetes-dashboard:/proxy. In caso di sessione scaduta sulla dashboard, inserire le seguenti credenziali: 

* admin 
* OLhwZn303nuVNJv4yY4S0DzGNVEc32TI 

e il seguente token se richiesto: 

* NyUwrzOKK2spRzVy6WaCmTWCvJlKuinq

