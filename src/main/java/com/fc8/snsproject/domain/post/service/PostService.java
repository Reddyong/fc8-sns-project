package com.fc8.snsproject.domain.post.service;

import com.fc8.snsproject.common.ErrorCode;
import com.fc8.snsproject.domain.alarm.entity.Alarm;
import com.fc8.snsproject.domain.alarm.entity.AlarmArgs;
import com.fc8.snsproject.domain.alarm.entity.enums.AlarmType;
import com.fc8.snsproject.domain.alarm.repository.AlarmRepository;
import com.fc8.snsproject.domain.alarm.service.AlarmService;
import com.fc8.snsproject.domain.comment.dto.CommentDto;
import com.fc8.snsproject.domain.comment.entity.Comment;
import com.fc8.snsproject.domain.comment.repository.CommentRepository;
import com.fc8.snsproject.domain.like.entity.Like;
import com.fc8.snsproject.domain.like.repository.LikeRepository;
import com.fc8.snsproject.domain.post.dto.PostDto;
import com.fc8.snsproject.domain.post.entity.Post;
import com.fc8.snsproject.domain.post.repository.PostRepository;
import com.fc8.snsproject.domain.user.entity.User;
import com.fc8.snsproject.domain.user.repository.UserRepository;
import com.fc8.snsproject.exception.SnsApplicationException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final LikeRepository likeRepository;
    private final CommentRepository commentRepository;
    private final AlarmRepository alarmRepository;

    private final AlarmService alarmService;

    @Transactional
    public PostDto create(String title, String body, String username) {
        // user find
        User user = getUserOrException(username);

        // post save
        Post savedPost = postRepository.save(Post.of(user, title, body));

        return PostDto.from(savedPost);
    }

    @Transactional
    public PostDto update(String title, String body, String username, Long postId) {
        // user find
        User user = getUserOrException(username);

        // post exist
        Post post = getPostOrException(postId);

        // post permission
        if (post.getUser() != user) {
            throw new SnsApplicationException(ErrorCode.INVALID_PERMISSION, String.format("%s has no permission to Post %s", username, postId));
        }

        post.setTitle(title);
        post.setBody(body);

        return PostDto.from(post);
    }

    @Transactional
    public void delete(Long postId, String username) {
        // user find
        User user = getUserOrException(username);

        // post exist
        Post post = getPostOrException(postId);

        // post permission
        if (!post.getUser().getId().equals(user.getId())) {
            throw new SnsApplicationException(ErrorCode.INVALID_PERMISSION, String.format("%s has no permission to Post %s", username, postId));
        }

        likeRepository.deleteAllByPost(post);
        commentRepository.deleteAllByPost(post);

        postRepository.deleteById(postId);
    }

    public Page<PostDto> findAll(Pageable pageable) {
        Page<Post> posts = postRepository.findAll(pageable);

        return posts.map(PostDto::from);
    }

    public Page<PostDto> findAllMyPosts(Pageable pageable, String username) {
        // user find
        User user = getUserOrException(username);

        // find my posts
        Page<Post> posts = postRepository.findAllByUser(pageable, user);

        return posts.map(PostDto::from);
    }

    @Transactional
    public void like(Long postId, String username) {
        // user find
        User user = getUserOrException(username);

        // post exist
        Post post = getPostOrException(postId);

        // check like
        likeRepository.findByUserAndPost(user, post).ifPresent(it -> {
            throw new SnsApplicationException(ErrorCode.ALREADY_LIKED, String.format("%s already likes Post %s", username, postId));
        });

        // like save
        likeRepository.save(Like.of(user, post));

        Alarm alarm = alarmRepository.save(Alarm.of(post.getUser(), AlarmType.NEW_LIKE_ON_POST, new AlarmArgs(user.getId(), post.getId())));
        alarmService.send(alarm.getId(), post.getUser().getId());
    }

    public Long getLikeCount(Long postId) {
        // post exist
        Post post = getPostOrException(postId);

        // count like
        return likeRepository.countByPost(post);

    }

    @Transactional
    public void comment(Long postId, String username, String content) {
        // user find
        User user = getUserOrException(username);

        // post exist
        Post post = getPostOrException(postId);

        // comment save
        Comment comment = Comment.of(user, post, content);
        commentRepository.save(comment);

        Alarm alarm = alarmRepository.save(Alarm.of(post.getUser(), AlarmType.NEW_COMMENT_ON_POST, new AlarmArgs(user.getId(), post.getId())));
        alarmService.send(alarm.getId(), post.getUser().getId());
    }

    public Page<CommentDto> getComments(Long postId, Pageable pageable) {
        // post exist
        Post post = getPostOrException(postId);

        Page<Comment> commentPage = commentRepository.findAllByPost(post, pageable);

        return commentPage.map(CommentDto::from);
    }

    private Post getPostOrException(Long postId) {
        return postRepository.findById(postId).orElseThrow(
                () -> new SnsApplicationException(ErrorCode.POST_NOT_FOUND, String.format("Post %s Not Founded", postId))
        );
    }

    private User getUserOrException(String username) {
        return userRepository.findByUsername(username).orElseThrow(
                () -> new SnsApplicationException(ErrorCode.USER_NOT_FOUND, String.format("%s Not Founded", username)));
    }
}
