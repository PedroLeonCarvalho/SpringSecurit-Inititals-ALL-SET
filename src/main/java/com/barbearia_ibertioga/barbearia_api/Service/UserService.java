package com.barbearia_ibertioga.barbearia_api.Service;

import com.barbearia_ibertioga.barbearia_api.DTO.UserDto;
import com.barbearia_ibertioga.barbearia_api.domain.User;
import com.barbearia_ibertioga.barbearia_api.repository.RoleRepository;
import com.barbearia_ibertioga.barbearia_api.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class UserService {
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    public UserService(RoleRepository roleRepository, PasswordEncoder passwordEncoder, UserRepository userRepository) {
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    public UserDto createUser(UserDto data) {
        User newUser = new User(data);
        newUser.setName(data.name());
        newUser.setEmail(data.email());
        newUser.setEnabled(true);
        newUser.setBirthDate(data.birthDate());
        newUser.setPhoneNumber(data.phoneNumber());
        newUser.setRoles(Arrays.asList(roleRepository.findByName("ROLE_USER")));
        newUser.setPassword(passwordEncoder.encode(data.password()));

        userRepository.save(newUser);
        return convertToUserDto(newUser);
    }

public  UserDto convertToUserDto (User user) {
        UserDto dto = new UserDto(user.getName(), user.getEmail(),user.getPhoneNumber(), user.getBirthDate(), user.getPassword());
        return dto;
}
}
