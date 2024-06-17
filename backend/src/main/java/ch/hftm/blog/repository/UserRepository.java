package ch.hftm.blog.repository;

import ch.hftm.blog.entity.User;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

public class UserRepository implements PanacheRepository<User> {
}
