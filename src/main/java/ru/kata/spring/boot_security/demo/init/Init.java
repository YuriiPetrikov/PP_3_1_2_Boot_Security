package ru.kata.spring.boot_security.demo.init;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleServiceImp;
import ru.kata.spring.boot_security.demo.service.UserServiceImp;

import javax.annotation.PostConstruct;
import java.util.HashSet;
import java.util.Set;

@Component
public class Init {
    private final UserServiceImp userService;
    private final RoleServiceImp roleService;

    @Autowired
    public Init(UserServiceImp userService, RoleServiceImp roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @PostConstruct
    public void initNewUsers() {
        Role role1 = new Role(1L, "ROLE_USER");
        Role role2 = new Role(2L, "ROLE_ADMIN");

        roleService.addRole(role1);
        roleService.addRole(role2);

        Set<Role> roleAdminUser = new HashSet<>();
        roleAdminUser.add(role1);
        roleAdminUser.add(role2);

        Set<Role> roleUser = new HashSet<>();
        roleUser.add(role1);

        Set<Role> roleAdmin = new HashSet<>();
        roleAdmin.add(role2);

        User user1 = new User(1L, "admin", "adminov", "admin@mail.ru",
                "admin", "admin", roleAdminUser);
        User user2 = new User(2L, "user", "userov", "user@mail.ru",
                "user", "user", roleUser);
        User user3 = new User(3L, "user1", "user1ov", "user1@mail.ru",
                "user1", "user1", roleAdmin);

        userService.saveUser(user1);
        userService.saveUser(user2);
        userService.saveUser(user3);
    }
}
