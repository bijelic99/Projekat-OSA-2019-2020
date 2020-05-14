--U zavisnosti od potrebe za odredjenim podacima kako bi testirali aplikaciju dodavati statenente.
--Pokrece se kada se pokrene aplikacija.

INSERT INTO TAGS VALUES(1, 'Urgent');
INSERT INTO TAGS VALUES(2,'Important');
INSERT INTO TAGS VALUES(3, 'Work');
INSERT INTO TAGS VALUES(4, 'Private');

INSERT INTO USERS(ID, FIRST_NAME, LAST_NAME, USERNAME, PASSWORD) VALUES(1, 'John', 'Doe', 'john_doe', 'password123');
INSERT INTO USERS(ID, FIRST_NAME, LAST_NAME, USERNAME, PASSWORD) VALUES(2, 'Petar', 'Petrovic', 'petar_petrovic', 'password123');

INSERT INTO ACCOUNTS(ID, DISPLAY_NAME, IN_SERVER_ADDRESS, IN_SERVER_PORT, IN_SERVER_TYPE, PASSWORD, SMTP_ADDRESS, SMTP_PORT, USERNAME) VALUES(1, 'johndoe@mail.com', 'fake.server.address', 8080, 1, 'password', 'fake.server.address', 8080, 'johnDoesMailAccount');
INSERT INTO ACCOUNTS(ID, DISPLAY_NAME, IN_SERVER_ADDRESS, IN_SERVER_PORT, IN_SERVER_TYPE, PASSWORD, SMTP_ADDRESS, SMTP_PORT, USERNAME) VALUES(2, 'petar_petrovic@mail.com', 'fake.server.address', 8080, 1, 'password', 'fake.server.address', 8080, 'johnDoesMailAccount');

INSERT INTO USERS_USER_ACCOUNTS(USER_ID, USER_ACCOUNTS_ID) VALUES(1, 1);
INSERT INTO USERS_USER_ACCOUNTS(USER_ID, USER_ACCOUNTS_ID) VALUES(2, 2);

