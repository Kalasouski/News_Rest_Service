package by.itechart.newsrestservice.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class NewsCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique=true)
    private String name;

}
