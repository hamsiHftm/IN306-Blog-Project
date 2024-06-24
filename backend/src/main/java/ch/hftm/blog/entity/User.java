package ch.hftm.blog.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@ToString
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

    @NotEmpty
    @Column(nullable = false)
    private String password;

    private String firstName;

    private String lastName;

//    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
//    private List<Blog> createdBlogs = new ArrayList<>();

    // TODO profile pic as binary
//    @Lob
//    private byte[] profilePicture;
}
