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
INSERT INTO ACCOUNTS(ID, DISPLAY_NAME, IN_SERVER_ADDRESS, IN_SERVER_PORT, IN_SERVER_TYPE, PASSWORD, SMTP_ADDRESS, SMTP_PORT, USERNAME) VALUES(3, 'fake.petar.petrovic@gmail.com', 'fake.server.address', 8080, 1, 'petarpetrovic789*', 'smtp.gmail.com', 587, 'fake.petar.petrovic@gmail.com');

INSERT INTO USERS_USER_ACCOUNTS(USER_ID, USER_ACCOUNTS_ID) VALUES(1, 1);
INSERT INTO USERS_USER_ACCOUNTS(USER_ID, USER_ACCOUNTS_ID) VALUES(2, 2);

INSERT INTO FOLDERS(ID, NAME, PARENT_FOLDER_ID) VALUES(1, 'folder1', null);
INSERT INTO FOLDERS(ID, NAME, PARENT_FOLDER_ID) VALUES(2, 'folder2', 1);

INSERT INTO contacts(id, display_name, email, first_name, last_name, note) VALUES(1, 'displayName', 'email@gmail.com', 'first name', 'last name', 'note');
INSERT INTO contacts(id, display_name, email, first_name, last_name, note) VALUES(2, 'displayName1', 'email1@gmail.com', 'first name1', 'last name1', 'note1');

INSERT INTO messages(id, bcc, cc, content, _from, subject, _to, unread, account_id) VALUES(1, 'bcc', 'cc', 'new content', "from", 'subject1', 'to1', 0, 1);
INSERT INTO messages(id, bcc, cc, content, _from, subject, _to, unread, account_id) VALUES(2, 'bcc1', 'cc1', 'new content1', "from", 'subject2', 'to2', 1, 2);

INSERT INTO messages(id, bcc, cc, content, _from, subject, _to, unread, account_id) VALUES(3, 'fake.petar.petrovic@gmail.com, mailtestib887@gmail.com', 'mailtestib887@gmail.com', 'Test email from OSA project', 'fake.petar.petrovic@gmail.com', 'Success? Fail? Hotel Trivago', 'mailtestib887@gmail.com', 1, 3);

INSERT INTO attachments(id, data, mime_type, name) VALUES(1, 'data1', 'mime type1', 'name1');
INSERT INTO attachments(id, data, mime_type, name) VALUES(2, 'data2', 'mime type2', 'name2');

INSERT INTO accounts_account_folders(account_id, account_folders_id) VALUES(1,1);

--INSERT INTO contacts_contact_photos(contact_id, contact_photos_id) VALUE(1, null);

INSERT INTO folders_messages(folder_id, messages_id) VALUES(1,1);
INSERT INTO folders_messages(folder_id, messages_id) VALUES(2,2);

--INSERT INTO messages_attachments(message_id, attachments_id) VALUES(1,1);
--INSERT INTO messages_attachments(message_id, attachments_id) VALUES(2,2);

--INSERT INTO messages_tags(message_id, tags_id) VALUES(1,3);
--INSERT INTO messages_tags(message_id, tags_id) VALUES(2,4);

INSERT INTO rules(id, _condition, operation, value, destination_folder_id) VALUES(1, 1, 2, 'value1', 1);

INSERT INTO users_user_contacts(user_id, user_contacts_id) VALUES(1, 1);
INSERT INTO users_user_contacts(user_id, user_contacts_id) VALUES(2, 2);

INSERT INTO users_user_tags(user_id, user_tags_id) VALUES(1, 2);
INSERT INTO users_user_tags(user_id, user_tags_id) VALUES(2, 1); 