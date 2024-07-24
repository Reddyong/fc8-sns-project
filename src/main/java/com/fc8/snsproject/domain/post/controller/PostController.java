package com.fc8.snsproject.domain.post.controller;

import com.fc8.snsproject.common.Response;
import com.fc8.snsproject.domain.post.dto.PostDto;
import com.fc8.snsproject.domain.post.dto.request.PostCreateRequest;
import com.fc8.snsproject.domain.post.dto.request.PostModifyRequest;
import com.fc8.snsproject.domain.post.dto.response.PostCreateResponse;
import com.fc8.snsproject.domain.post.dto.response.PostFeedResponse;
import com.fc8.snsproject.domain.post.dto.response.PostModifyResponse;
import com.fc8.snsproject.domain.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping(path = "/api/v1/posts")
@RestController
public class PostController {

    private final PostService postService;

    @PostMapping(path = "")
    public Response<PostCreateResponse> create(
            @RequestBody PostCreateRequest postCreateRequest,
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        PostDto postDto = postService.create(postCreateRequest.title(), postCreateRequest.body(), userDetails.getUsername());
        PostCreateResponse postCreateResponse = PostCreateResponse.from(postDto);

        return Response.success(postCreateResponse);
    }

    @PutMapping(path = "/{post-id}")
    public Response<PostModifyResponse> update(
            @PathVariable(name = "post-id") Long postId,
            @RequestBody PostModifyRequest postModifyRequest,
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        PostDto postDto = postService.update(postModifyRequest.title(), postModifyRequest.body(), userDetails.getUsername(), postId);
        PostModifyResponse postModifyResponse = PostModifyResponse.from(postDto);

        return Response.success(postModifyResponse);
    }

    @DeleteMapping(path = "/{post-id}")
    public Response<String> delete(
            @PathVariable(name = "post-id") Long postId,
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        postService.delete(postId, userDetails.getUsername());

        return Response.success("delete success");
    }

    @GetMapping(path = "")
    public Response<Page<PostFeedResponse>> findAll(
            Pageable pageable,
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        Page<PostDto> postDtoPage = postService.findAll(pageable);

        return Response.success(postDtoPage.map(PostFeedResponse::from));
    }

    @GetMapping(path = "/my")
    public Response<Page<PostFeedResponse>> findAllMyPosts(
            Pageable pageable,
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        Page<PostDto> allMyPosts = postService.findAllMyPosts(pageable, userDetails.getUsername());

        return Response.success(allMyPosts.map(PostFeedResponse::from));
    }
}
