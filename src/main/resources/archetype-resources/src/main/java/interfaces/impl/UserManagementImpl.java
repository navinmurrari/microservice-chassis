package ${package}.interfaces.impl;

import ${package}.cache.CacheStore;
import ${package}.entities.UserEntity;
import ${package}.exceptions.ValidationException;
import ${package}.interfaces.UserManagement;
import ${package}.model.User;
import ${package}.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

import static ${package}.constants.ErrorCodes.USER_NOT_FOUND;

@Slf4j
@Service
public class UserManagementImpl implements UserManagement {
    @Autowired
    private CacheStore<User> userCacheStore;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private UserRepository repository;

    @Transactional
    @Override
    public void createUser(User user) {
        UserEntity userEntity = mapUserModelToEntity(user);
        userEntity.setUserId(UUID.randomUUID().toString());
        repository.save(userEntity);
    }

    @Override
    public User getUserByName(String username) {
        Optional<User> user = userCacheStore.get(username);
        if(user.isPresent()){
            log.info("username found in cache = {}",username);
            return user.get();
        }
        log.info("username = {}",username);
        Optional<UserEntity> userEntity = repository.findByUserName(username);
        if(!userEntity.isPresent()){
            throw new ValidationException("user not found",USER_NOT_FOUND,username);
        }
        User userModel = mapUserEntityToModel(userEntity);
        userCacheStore.add(username,userModel);
        return userModel;
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
