CREATE TABLE 'users'(
'id' INT NOT NULL AUTO_INCREMENT,
'username' VARCHAR(45) NOT NULL,
'password' VARCHAR(45) NOT NULL,
'enabled' INT NOT NULL,
PRIMARY KEY('id')
);

CREATE TABLE 'authorities'(
'id' INT NOT NULL AUTO_INCREMENT,
'username' VARCHAR(45) NOT NULL,
'authority' VARCHAR(45) NOT NULL,
PRIMARY KEY('id')
);

INSERT IGNORE INTO my_photos.users VALUES (NULL, 'happy','12345','1');
INSERT IGNORE INTO my_photos.authorities VALUES (NULL, 'happy','write');

CREATE TABLE `my_photos`.`authorities` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `customer_id` INT NOT NULL,
  `name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`));
