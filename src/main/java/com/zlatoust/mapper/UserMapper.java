package com.zlatoust.mapper;

import com.zlatoust.models.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.Optional;

@Mapper
public interface UserMapper {

    Optional<User> findUserById(Long id);

    Optional<User> findUserByEmail(String email);

    User saveUser(User user);
}
