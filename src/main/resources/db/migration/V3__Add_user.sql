INSERT INTO bk.users ("name",login,pass_hash,is_blocked) VALUES
	 ('Ordinary user','user','',false);

INSERT INTO bk.user_privilegies (user_id,privilege_name) VALUES
	 ((SELECT id FROM bk.users WHERE login='user') ,'USER');