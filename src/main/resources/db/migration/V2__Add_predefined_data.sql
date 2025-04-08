INSERT INTO bk.users ("name",login,pass_hash,is_blocked) VALUES
	 ('Administator','admin','$2y$10$L22P2kayWAaMVlu4ku51OecnGpEf40XIwdYZit0TYyvGtaBvHYk1K',false);

INSERT INTO bk.user_privilegies (user_id,privilege_name) VALUES
	 ((SELECT id FROM bk.users WHERE login='admin'),'ADMIN');

INSERT INTO bk.user_privilegies (user_id,privilege_name) VALUES
	 ((SELECT id FROM bk.users WHERE login='admin'),'USER');