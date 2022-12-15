package com.usersservice.repositories;

import com.usersservice.entities.Users;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
public interface UsersRepository extends JpaRepository<Users, Integer> {



    @Modifying @Transactional
    @Query(value = "delete from users where id = :id_user", nativeQuery = true)
    void deleteByIdUser(@Param("id_user") Integer id_user);
    @Modifying @Transactional
    @Query(value = "delete from user_role where user_id = :id_user", nativeQuery = true)
    void deleteRoleByIdUser(@Param("id_user") Integer id_user);

    @Query(value = "select * from users order by id asc", nativeQuery = true)
    List<Users> findAllUsers();

    @Query(value = "select * from users", nativeQuery = true)
    List<Users> findAllUsersWithPage(Pageable pageable);

    @Query(value = "select * from users where id = :id_user", nativeQuery = true)
    Users findUsersById(@Param("id_user") Integer id_user);

    @Query(value = "select * from users where username = :username", nativeQuery = true)
    List<Users> findUsersByName(@Param("username") String name);

    //jwt
    Optional<Users> findByUsername(String username);
    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);

    @Modifying @Transactional
    @Query(value = "update users set username = :username, address= :address, email = :email, password = :password where id = :id_user", nativeQuery = true)
    void updateUser(@Param("id_user") Integer id_user,
                    @Param("username") String username,
                    @Param("address") String address,
                    @Param("email") String email,
                    @Param("password") String password);

}
