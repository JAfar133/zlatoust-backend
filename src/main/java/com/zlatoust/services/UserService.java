package com.zlatoust.services;

import com.zlatoust.mapper.UserMapper;
import com.zlatoust.models.User;
import com.zlatoust.services.dto.UserDTO;
import com.zlatoust.services.dto.mapper.UserDTOListMapper;
import com.zlatoust.services.dto.mapper.UserDTOMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {

    private final UserMapper userMapper;
    private final UserDTOMapper userDTOMapper;
    private final UserDTOListMapper userDTOListMapper;
    private final PasswordEncoder passwordEncoder;

    public UserDTO findUserById(Long id) throws ResourceNotFoundException {
        return userMapper.findUserById(id)
                .map(userDTOMapper::toDto)
                .orElseThrow(()-> new ResourceNotFoundException("User with id = " + id + " wasn't found"));
    }

    public List<UserDTO> findAll() throws ResourceNotFoundException {
        return userDTOListMapper.toDto(userMapper.findAll());
    }

    public UserDTO findUserByEmail(String email) throws ResourceNotFoundException {
        return userMapper.findUserByEmail(email)
                .map(userDTOMapper::toDto)
                .orElseThrow(()-> new ResourceNotFoundException("User with email = " + email + " wasn't found"));
    }

    @Transactional
    public void deleteUser(long id) {
        userMapper.deleteUserById(id);
    }

    @Transactional
    public void updateUser(long id, UserDTO userDTO) {
        userMapper.updateUser(userDTOMapper.toEntity(userDTO));
    }

    @Transactional
    public void changePassword(long id, String oldPassword, String newPassword) {
        User user = userMapper.findUserById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User with id = " + id + " wasn't found"));

        if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
            throw new IllegalArgumentException("Старый пароль неверен");
        }

        user.setPassword(passwordEncoder.encode(newPassword));
        userMapper.updatePassword(user);
    }
}
