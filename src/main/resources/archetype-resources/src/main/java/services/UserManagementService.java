package ${package}.services;

import ${package}.interfaces.UserManagement;
import ${package}.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserManagementService {

    @Autowired
    private UserManagement userManagement;

    public ResponseEntity<Void> createUser(User user){
        userManagement.createUser(user);
        return ResponseEntity.ok().build();
    }

    public ResponseEntity<User> getUserByName(String username) {
        return ResponseEntity.ok(userManagement.getUserByName(username));
    }


}
