package ru.kata.spring.boot_security.demo.service;


import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.dao.UserDaoImp;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImp implements UserDetailsService {

    private final UserDaoImp userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserServiceImp(UserDaoImp userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }

        return user;
    }

    public User findUserById(Long userId) {
        Optional<User> userFromDb = userRepository.findById(userId);
        return userFromDb.orElse(new User());
    }

    public List<User> allUsers() {
        return userRepository.findAll();
    }

    public void updateUser(User user) {
        userRepository.save(user);
    }

    public boolean saveUser(User user) {
        User userFromDB = userRepository.findByUsername(user.getUsername());

        //редактирование существующего пользователя
        if (userFromDB != null) {
            user.setUsername(userFromDB.getUsername());
            user.setLastName(userFromDB.getLastName());
            user.setEmail(userFromDB.getEmail());
            userRepository.save(user);
            return false;
        }

        //создать нового пользователя
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setPassword(user.getPassword());
        user.setRoles(user.getRoles());

        userRepository.save(user);
        return true;
    }

    public boolean deleteUser(Long userId) {
        if (userRepository.findById(userId).isPresent()) {
            userRepository.deleteById(userId);
            return true;
        }
        return false;
    }
}
