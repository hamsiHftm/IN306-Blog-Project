package ch.hftm.blog.service;

import ch.hftm.blog.entity.User;
import ch.hftm.blog.repository.UserRepository;
import io.quarkus.logging.Log;
import jakarta.inject.Inject;

import java.util.List;

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
}
