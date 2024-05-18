# OAuth2-Facebook-Microservice
Questo progetto è un microservizio sviluppato con Spring Boot che utilizza Spring Security 6 per gestire l'autenticazione degli utenti tramite OAuth2 con provider Facebook. L'IDE utilizzato per lo sviluppo è Eclipse.

## Sommario
- Descrizione
- Prerequisiti
- Installazione
- Configurazione
- Uso

## Descrizione

## Prerequisiti
- Java 11 o superiore
- MySQL Database
- Eclipse IDE
- Un account sviluppatore Facebook
- Un'app Facebook configurata per OAuth2

## Installazione
### Clonazione del Repository

Clona il repository del progetto nella macchina locale:
git clone https: https://github.com/gius34/OAuth2-Facebook-Microservice.git

### Import del progetto in Eclipse
1. Apri Eclipse
2. Vai su 'File -> Import'
3. Seleziona 'Existing Maven Projects' e clicca 'Next'
4. Seleziona la directory del progetto clonato e clicca 'Finish'

## Configurazione
### Configurazione dell'App Facebook
1. Vai: https://developers.facebook.com/
2. Crea una nuova app
3. Ottieni l'ID App e la Chiave segreta

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
spring.security.oauth2.client.registration.facebook.client-id=ID App
spring.security.oauth2.client.registration.facebook.client-secret=Chiave segreta
```

Inserire le credenziali del DB MySQL:
```bash
spring.datasource.url=url
spring.datasource.username=name
spring.datasource.password=password
```
