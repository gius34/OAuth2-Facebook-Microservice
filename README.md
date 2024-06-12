# OAuth2-Facebook-Microservice
Questo progetto è un microservizio sviluppato con Spring Boot che utilizza Spring Security 6 per gestire l'autenticazione degli utenti tramite OAuth2 con provider Facebook. L'IDE utilizzato per lo sviluppo è Eclipse.

## Sommario
- Descrizione
- Prerequisiti
- Configurazione
- Uso

## Descrizione
Questo progetto è un microservizio per la visualizzazione di una galleria di foto, che utilizza Spring Boot e Spring Security 6 per il back-end e Thymeleaf per il front-end. L'app offre la funzionalità di autenticazione sia tramite OAuth2 (con provider di autenticazione Facebook) che tramite user e password. Gestisce due ruoli utente: `USER` e `ADMIN`, con differenti permessi di accesso alle funzionalità dell'app.

### OAuth2 Single Sign-On
L'applicazione utilizza OAuth 2.0 per implementare il Single Sign-On (SSO) con Facebook. Questa funzionalità consente agli utenti di autenticarsi con le loro credenziali Facebook, senza dover ricordare un nuovo set di credenziali specifiche per l'app. In particolare, l'applicazione utilizza un tipo di OAuth 2.0 grant chiamato Authorization Code Grant, per ottenere un token di accesso da Facebook (il server di autorizzazione). Con questo token, l'app può chiedere a Facebook alcuni dettagli personali dell'utente, come l'ID di accesso, il nome e l'email. In questo scenario, Facebook funge da Resource Server, decodificando il token che viene fornito dall'app e determinando se consentire all'app l'accesso ai dettagli dell'utente. Se il processo ha esito positivo, l'applicazione inserisce le informazioni dell'utente nel contesto di Spring Security in modo che l'utente venga autenticato.

## Prerequisiti
- Java 11 o superiore
- Maven
- MySQL Database
- Un account sviluppatore Facebook
- Un'app Facebook configurata per OAuth2

## Configurazione
### Clonazione del Repository

Clona il repository del progetto nella macchina locale:
```bash
git clone https://github.com/gius34/OAuth2-Facebook-Microservice.git
cd OAuth2-Facebook-Microservice
```

### Configurazione dell'App Facebook
1. Vai: https://developers.facebook.com/
2. Crea una nuova app
3. Ottieni `Client ID` e `Client Secret`

### Configurazione del DB MySQL
Script per creare il DB MySql
```sql
CREATE DATABASE my_photos;

CREATE TABLE 'customer'(
'customer_id' INT NOT NULL AUTO_INCREMENT,
'email' VARCHAR(45) NOT NULL,
'pwd' VARCHAR(200) NOT NULL,
'role' VARCHAR(45) INT NOT NULL,
PRIMARY KEY('customer_id')
);

CREATE TABLE 'photo'(
'id' INT NOT NULL AUTO_INCREMENT,
'url' VARCHAR(45),
PRIMARY KEY('id')
);

INSERT INTO my_photos.photo VALUES (NULL, './img/01.png');
```

### Configurazione del file 'application.properties'
Modificare il file 'application.properties'

Inserire le credenziali Facebook:
```bash
spring.security.oauth2.client.registration.facebook.client-id=Client ID
spring.security.oauth2.client.registration.facebook.client-secret=Client Secret
```

Inserire le credenziali del DB MySQL:
```bash
spring.datasource.url=url
spring.datasource.username=name
spring.datasource.password=password
```

## Uso
1. Esegui il comando Maven per avviare l'applicazione: `mvn spring-boot:run`
2. L'applicazione sarà disponibile al seguente URL: `http://localhost:8080`
3. Al primo avvio dell'applicazione, poiché il database non conterrà ancora utenti, il primo utente che si registrerà avrà automaticamente il ruolo di `ADMIN`. La registrazione può avvenire inserendo email e password o tramite OAuth2 SSO (con provider Facebook).

   3.1 **Registrazione con email e password**
   
   - Visita la pagina di registrazione all'indirizzo: `http://localhost:8080/register`
   - Inserire i dettagli richiesti (email e password) per completare la registrazione
     
     <img width="454" alt="image" src="https://github.com/gius34/OAuth2-Facebook-Microservice/assets/34596825/01238997-6043-4840-a7dd-56341ee3131d">

   3.2 **Registrazione tramite OAuth2 SSO**
   
   - Visita la pagina di login all'indirizzo: `http://localhost:8080/login`
   - Selezionare l'opzione `Login con Facebook`. Dopo l'autenticazione tramite Facebook, l'utente riceverà automaticamente il ruolo di `ADMIN` (se il database non conterrà ancora utenti)

     ![image](https://github.com/gius34/OAuth2-Facebook-Microservice/assets/34596825/aa8e1b14-283f-48d4-8028-f4ceff07e147)


4. Dopo che il primo utente si sarà registrato, tutti i successivi utenti che si registreranno avranno il ruolo di `USER`.

5. Dopo l'autenticazione, gli utenti vengono reindirizzati alla pagina `/home`, accessibile solo agli utenti autenticati. Da questa pagina, gli utenti possono:

   5.1 **Visualizzare le immagini presenti nella galleria**
   
   - In questa pagina è possibile visualizzare le immagini presenti nella galleria
   - Pagina accessibile sia agli utenti con ruolo `USER` sia agli utenti con ruolo `ADMIN`
     >**Note**
     >
     >Le immagini sono salvate al seguente percorso: `/src/main/resources/static/img`

   5.2 **Gestire le immagini presenti nella galleria**
   
   - In questa pagina è possibile aggiungere o eliminare le immagini presenti nella galleria
   - Pagina accessibile solo agli utenti con ruolo `ADMIN`

   5.3 **Effettuare il logout**

