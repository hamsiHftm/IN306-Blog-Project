package ch.hftm.blog.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = {"createdBlogs", "createdComments"})
@Getter
@Setter
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    @Column(unique = true, nullable = false, updatable = false)
    private String email;

    private String picUrl;

    @NotEmpty
    @Column(nullable = false)
    private String password;

    // TODO change to enum
    private String role;

    private String firstName;

    private String lastName;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Blog> createdBlogs = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Comment> createdComments = new ArrayList<>();

    // Maybe add list of blogLike and comment like when needed...

    // TODO profile pic as binary
//    @Lob
//    private byte[] profilePicture;
}
