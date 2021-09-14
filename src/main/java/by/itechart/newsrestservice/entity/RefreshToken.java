package by.itechart.newsrestservice.entity;

import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;

@Data
@Entity
@Table(name = "refresh_tokens")
public class RefreshToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "token")
    private String token;

    @Column(name = "expiration_date")
    private Timestamp expirationDate;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
}
