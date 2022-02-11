package ${package}.interfaces;

import ${package}.model.User;

public interface UserManagement {

    void createUser(User user);

    User getUserByName(String username);
}
