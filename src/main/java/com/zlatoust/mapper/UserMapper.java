package com.zlatoust.mapper;

import com.zlatoust.models.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Optional;

@Mapper
public interface UserMapper {

    Optional<User> findUserById(Long id);

    Optional<User> findUserByEmail(String email);

    void saveUser(User user);

    void deleteUserById(Long id);

    void updateUser(User user);

    List<User> findAll();

    void updatePassword(User user);
}
