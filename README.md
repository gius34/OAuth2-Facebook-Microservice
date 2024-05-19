# OAuth2-Facebook-Microservice
Questo progetto è un microservizio sviluppato con Spring Boot che utilizza Spring Security 6 per gestire l'autenticazione degli utenti tramite OAuth2 con provider Facebook. L'IDE utilizzato per lo sviluppo è Eclipse.

## Sommario
- Descrizione
- Prerequisiti
- Installazione
- Configurazione
- Uso

## Descrizione
Questo progetto è un microservizio per la visualizzazione di una galleria di foto, che utilizza Spring Boot e Spring Security 6 per il back-end e Thymeleaf per il front-end. L'app offre funzionalità di autenticazione sia tramite OAuth2 (con provider di autenticazione Facebook) che tramite user e password. Gestisce due ruoli utente: `USER` e `ADMIN`, con differenti permessi di accesso alle funzionalità dell'app.

### OAuth2 Single Sign-On
L'applicazione utilizza OAuth 2.0 per implementare il Single Sign-On (SSO) con Facebook. Questa funzionalità consente agli utenti di autenticarsi con le loro credenziali Facebook, senza dover ricordare un nuovo set di credenziali specifiche per l'app. L'applicazione utilizza un tipo di OAuth 2.0 grant chiamato Authorization Code Grant, per ottenere un token di accesso da Facebook (il server di autorizzazione). Con questo token, l'app può chiedere a Facebook alcuni dettagli personali dell'utente, come l'ID di accesso, il nome e l'email. In questo scenario, Facebook funge da Resource Server, decodificando il token che viene fornito dall'app e determinando se consentire all'app l'accesso ai dettagli dell'utente. Se il processo ha esito positivo, l'applicazione inserisce le informazioni dell'utente nel contesto di Spring Security in modo che l'utente venga autenticato.

## Prerequisiti
- Java 11 o superiore
- MySQL Database
- Eclipse IDE
- Un account sviluppatore Facebook
- Un'app Facebook configurata per OAuth2

## Installazione
### Clonazione del Repository

Clona il repository del progetto nella macchina locale:
```bash
git clone https: https://github.com/gius34/OAuth2-Facebook-Microservice.git
cd OAuth2-Facebook-Microservice
```

### Import del progetto in Eclipse
1. Apri Eclipse
2. Vai su 'File -> Import'
3. Seleziona 'Existing Maven Projects' e clicca 'Next'
4. Seleziona la directory del progetto clonato e clicca 'Finish'

## Configurazione
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
