package com.shop.online;

import com.shop.online.entity.Role;
import com.shop.online.entity.User;
import com.shop.online.repository.RoleRepository;
import com.shop.online.repository.UserRepository;
import com.shop.online.utils.RoleEnum;
import com.shop.online.utils.UserEnum;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDateTime;

@Configuration
public class DataInit {

    @Bean
    public CommandLineRunner initData (RoleRepository roleRepository, UserRepository userRepository, BCryptPasswordEncoder passwordEncoder){
        return args -> {
            if (roleRepository.count() == 0) {
                Role adminRole = new Role();
                adminRole.setName(RoleEnum.Role.ADMIN.name());
                roleRepository.save(adminRole);

                Role teacherRole = new Role();
                teacherRole.setName(RoleEnum.Role.USER.name());
                roleRepository.save(teacherRole);

                Role parentsRole = new Role();
                parentsRole.setName(RoleEnum.Role.SUB_ADMIN.name());
                roleRepository.save(parentsRole);
            }
            if (userRepository.count() == 0) {
                Role adminRole = roleRepository.findByName(RoleEnum.Role.ADMIN.name()).orElseThrow();

                User admin = new User();
                admin.setEmail("vuhoan485@gmail.com");
                admin.setPassword(passwordEncoder.encode("hoan10a8"));
                admin.setRole(adminRole);
                admin.setStatus(UserEnum.Status.ACTIVE);
                admin.setCreatedAt(LocalDateTime.now());
                userRepository.save(admin);
            }
        };
    }
}
