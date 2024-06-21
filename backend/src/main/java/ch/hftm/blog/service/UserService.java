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

    public List<User> findAll() {
        var users = userRepository.listAll();
        Log.info("Returning " + users.size() + " users");
        return users;
    }

    public User getUserById(Long id) {
        return userRepository.findById(id);
    }

    public User createUser(User user) {
        Log.info("Adding User " + user.getEmail());
        userRepository.persist(user);
        return user;
    }

    @Transactional
    public User updateUser(Long id, User updatedUser) {
        User user = userRepository.findById(id);
        if (user != null) {
            user.setFirstName(updatedUser.getFirstName());
            user.setLastName(updatedUser.getLastName());
            userRepository.persist(user);
            Log.info("Updated user " + updatedUser.getEmail());
        } else {
            Log.warn("No user found with id " + id + " to update");
        }
        return user;
    }

    @Transactional
    public User changePassword(Long id, String newPassword) {
        User user = userRepository.findById(id);
        if (user != null) {
            user.setPassword(newPassword); // Assuming you have a setPassword method in User entity
            userRepository.persist(user);
            Log.info("Password changed for user " + user.getEmail());
        } else {
            Log.warn("No user found with id " + id + " to change password");
        }
        return user;
    }

    @Transactional
    public void deleteUser(Long id) {
        User user = userRepository.findById(id);
        if (user != null) {
            userRepository.delete(user);
            Log.info("Deleted user with id " + id);
        } else {
            Log.warn("No user found with id " + id + " to delete");
        }
    }
}
