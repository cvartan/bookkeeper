package pro.cvartan.test.bookkeeper.repository;

import pro.cvartan.test.bookkeeper.entity.User;

public interface UserRepository {
    User getUserByLogin(String login);
    
}

