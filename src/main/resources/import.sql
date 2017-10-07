INSERT INTO chat(chat_title)  VALUES
  ('first chat'),
  ('second chat'),
  ('third chat');
INSERT INTO "user"(active, email, first_name, last_name, password)  VALUES
  (TRUE, 'sanak@i.ua', 'Alex','Sanak', '123');
INSERT INTO message(content, created_at, chat_id, sender_id) VALUES
  ('Hi','1999-01-08 04:05:06',1,1),
  ('Hi2','1999-01-08 04:05:08',1,1),
  ('Hi3','1999-01-08 04:05:10',1,1);
INSERT INTO user_chat(chat_id, user_id) VALUES (1,1);
