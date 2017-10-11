INSERT INTO chat(chat_title)  VALUES
  ('first chat'),
  ('second chat'),
  ('third chat');
INSERT INTO "user"(active, email, first_name, last_name, password)  VALUES
  (TRUE, 'sanak@i.ua', 'Alex','Sanak', '123');
INSERT INTO message(content, created_at, chat_id,read) VALUES
  ('Hi','1999-01-08 04:05:06',1,TRUE ),
  ('Hi1','1999-01-08 04:05:08',1,TRUE ),
  ('Hi2','1999-01-08 04:05:10',1,FALSE ),
  ('Hi3','1999-01-08 04:05:12',2,TRUE ),
  ('Hi4','1999-01-08 04:05:15',2,TRUE ),
  ('Hi5','1999-01-08 04:05:18',3,FALSE ),
  ('Hi6','1999-01-08 04:05:19',3,FALSE );
INSERT INTO user_chat(chat_id, user_id) VALUES (1,1);
