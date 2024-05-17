package ch.hftm.blog.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.*;

@AllArgsConstructor
@ToString
@Entity
@Data
public class Blog {
    @Id @GeneratedValue private long id;
    private @Getter @Setter String title;
    private @Getter @Setter String text;

    public Blog() {}

    public Blog(String title, String text) {
        this.title = title;
        this.text = text;
    }
}
