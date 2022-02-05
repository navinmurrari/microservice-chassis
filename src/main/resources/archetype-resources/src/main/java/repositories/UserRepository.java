package ${package}.repositories;

import ${package}.entities.UserEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<UserEntity,String> {

    Optional<UserEntity> findByUserName(String name);
}
