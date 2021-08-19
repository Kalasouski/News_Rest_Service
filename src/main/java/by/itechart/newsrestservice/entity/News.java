package by.itechart.newsrestservice.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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

    private String brief;

    private String content;

//    @Enumerated(value = EnumType.STRING)
//    @Column(name = "category")
//    private NewsCategoryEnum category;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "news_category_id")
    private NewsCategory newsCategory;

    @Column(name = "created_at")
    private Timestamp createdAt;

    @JsonManagedReference
    @OneToMany(mappedBy = "news")
    private List<Comment> comments;

    @JsonManagedReference
    @OneToMany(mappedBy = "news")
    private List<Like> likes;

}
