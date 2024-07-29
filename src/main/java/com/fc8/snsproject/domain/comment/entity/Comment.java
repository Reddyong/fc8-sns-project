package com.fc8.snsproject.domain.comment.entity;

import com.fc8.snsproject.domain.post.entity.Post;
import com.fc8.snsproject.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.sql.Timestamp;
import java.time.Instant;

// TODO : @Where 을 사용하는 방법을 현재 hibernate 방법에 맞게 고쳐보기
@Getter
@NoArgsConstructor
@AllArgsConstructor
@SQLDelete(sql = "UPDATE comments SET deleted_at = NOW() where id=?")
@Where(clause = "deleted_at is NULL")
@Table(indexes = {
        @Index(name = "post_id_idx", columnList = "post_id")
}, name = "comments"
)
@Entity
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    private String content;

    @Column(name = "registered_at")
    private Timestamp registeredAt;

    @Column(name = "updated_at")
    private Timestamp updatedAt;

    @Column(name = "deleted_at")
    private Timestamp deletedAt;

    public Comment(User user, Post post, String content) {
        this.user = user;
        this.post = post;
        this.content = content;
    }

    public Comment(Long id, User user, Post post, String content) {
        this.id = id;
        this.user = user;
        this.post = post;
        this.content = content;
    }

    public static Comment of(User user, Post post, String content) {
        return new Comment(user, post, content);
    }

    public static Comment of(Long id, User user, Post post, String content) {
        return new Comment(id, user, post, content);
    }

    @PrePersist
    void registeredAt() {
        this.registeredAt = Timestamp.from(Instant.now());
    }

    @PreUpdate
    void updatedAt() {
        this.updatedAt = Timestamp.from(Instant.now());
    }
}
