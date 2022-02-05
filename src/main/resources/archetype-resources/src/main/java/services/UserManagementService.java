package ${package}.services;

import ${package}.cache.CacheStore;
import ${package}.entities.UserEntity;
import ${package}.exceptions.ValidationException;
import ${package}.model.User;
import ${package}.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Random;
import java.util.UUID;

import static ${package}.constants.ErrorCodes.USER_NOT_FOUND;

@Service
@Slf4j
@CacheConfig(cacheNames = "user")
public class UserManagementService {

    @Autowired
    private UserRepository repository;

    @Autowired
    private CacheStore<User> userCacheStore;

    @Autowired
    private PasswordEncoder encoder;

    @Transactional
    public ResponseEntity<Void> createUser(User user){
        UserEntity userEntity = mapUserModelToEntity(user);
        userEntity.setUserId(UUID.randomUUID().toString());
        repository.save(userEntity);
        return ResponseEntity.ok().build();
    }

    private UserEntity mapUserModelToEntity(User user) {
        UserEntity userEntity = new UserEntity();
        userEntity.setEmail(user.getEmail());
        userEntity.setPassword(encoder.encode(user.getPassword()));
        userEntity.setUserName(user.getUsername());
        userEntity.setLastName(user.getLastName());
        userEntity.setFirstName(user.getFirstName());
        return userEntity;
    }

    public ResponseEntity<User> getUserByName(String username) {
        Optional<User> user = userCacheStore.get(username);
        if(user.isPresent()){
            log.info("username found in cache = {}",username);
            return ResponseEntity.ok(user.get());
        }
        log.info("username = {}",username);
        Optional<UserEntity> userEntity = repository.findByUserName(username);
        if(!userEntity.isPresent()){
            throw new ValidationException("user not found",USER_NOT_FOUND,username);
        }
        User userModel = mapUserEntityToModel(userEntity);
        userCacheStore.add(username,userModel);
        return ResponseEntity.ok(userModel);
    }

    private User mapUserEntityToModel(Optional<UserEntity> userEntity) {
        User user = new User();
        UserEntity userEntityFromDb = userEntity.get();
        user.setEmail(userEntityFromDb.getEmail());
        user.setPassword(userEntityFromDb.getPassword());
        user.setUsername(userEntityFromDb.getUserName());
        user.setLastName(userEntityFromDb.getLastName());
        user.setFirstName(userEntityFromDb.getFirstName());
        user.setId(userEntityFromDb.getUserId());
        return user;
    }
}
