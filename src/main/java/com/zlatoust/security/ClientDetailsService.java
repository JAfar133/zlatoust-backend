package com.zlatoust.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.zlatoust.mapper.UserMapper;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ClientDetailsService implements UserDetailsService {

    private final UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userMapper.findUserByEmail(email)
                .map(ClientDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException("Client not found"));
    }

}
