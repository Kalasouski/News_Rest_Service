package by.itechart.newsrestservice.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "news")
public class News {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String heading;

    private String news;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "category")
    private NewsCategory category;

    @Column(name = "created_at")
    private Timestamp createdAt;

    @OneToMany(mappedBy = "news")
    private List<Comment> comments;
}