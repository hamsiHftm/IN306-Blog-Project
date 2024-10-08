package ch.hftm.blog.service;

import ch.hftm.blog.entity.User;
import ch.hftm.blog.repository.UserRepository;
import io.quarkus.logging.Log;
import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.util.List;

@Dependent
public class UserService {
    @Inject
    UserRepository userRepository;

    public User getUserById(Long id) {
        return userRepository.findById(id);
    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public User createUser(User user) {
        Log.info("Adding User " + user.getEmail());
        userRepository.persist(user);
        return user;
    }

    @Transactional
    public User updateUserName(User user, String firstName, String lastName) {
        if (firstName != null && !firstName.isEmpty()) {
            user.setFirstName(firstName);
        }
        if (lastName != null && !lastName.isEmpty()) {
            user.setLastName(lastName);
        }
        userRepository.persist(user);
        Log.info("Updated user " + user.getEmail());
        return user;
    }

    @Transactional
    public User changePassword(User user, String newPassword) {
        user.setPassword(newPassword);
        userRepository.persist(user);
        Log.info("Password changed for user " + user.getEmail());
        return user;
    }

    @Transactional
    public void deleteUser(User user) {
        Long id = user.getId();
        userRepository.delete(user);
        Log.info("Deleted user with id " + id);
    }

    public boolean checkPassword(User user, String password) {
        return user.getPassword().equals(password); // Replace with hash comparison in the future
    }
}
