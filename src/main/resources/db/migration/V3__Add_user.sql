INSERT INTO bk.users ("name",login,pass_hash,is_blocked) VALUES
	 ('Ordinary user','user','$2a$10$G8a9NEEX.GiK8DCah.PJ5.wm9yJCSvxOf97o6IhkfyuYj0CfUlYkS',false);

INSERT INTO bk.user_privilegies (user_id,privilege_name) VALUES
	 ((SELECT id FROM bk.users WHERE login='user') ,'USER');