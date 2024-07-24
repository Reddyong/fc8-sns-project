package com.fc8.snsproject.domain.post.controller;

import com.fc8.snsproject.common.Response;
import com.fc8.snsproject.domain.post.dto.PostDto;
import com.fc8.snsproject.domain.post.dto.request.PostCreateRequest;
import com.fc8.snsproject.domain.post.dto.response.PostCreateResponse;
import com.fc8.snsproject.domain.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
