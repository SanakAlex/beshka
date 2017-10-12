INSERT INTO chat(chat_title)  VALUES
  ('first chat'),
  ('second chat'),
  ('third chat');
INSERT INTO "user"(active, email, first_name, last_name, password)  VALUES
  (TRUE, 'sanak@i.ua', 'Alex','Sanak', '123'),
  (TRUE, 'sanak@i.ua', 'Petia','A', '123'),
  (TRUE, 'sanak@i.ua', 'Kolia','Z', '123'),
  (TRUE, 'sanak@i.ua', 'Vasia','W', '123');
INSERT INTO message(content, created_at, chat_id, read, sender_id) VALUES
  ('Hi','1999-01-08 04:05:06',1,TRUE, 1),
  ('Hi1','1999-01-08 04:05:08',1,TRUE, 1),
  ('Hi2','1999-01-08 04:05:10',1,FALSE, 2),
  ('Hi3','1999-01-08 04:05:12',2,TRUE, 3),
  ('Hi4','1999-01-08 04:05:15',2,TRUE, 4),
  ('Hi5','1999-01-08 04:05:18',3,FALSE, 1),
  ('Hi6','1999-01-08 04:05:19',3,FALSE, 1);
INSERT INTO user_chat(chat_id, user_id) VALUES
  (1,1),
  (1,2),
  (2,3),
  (2,4),
  (3,1),
  (3,4);
