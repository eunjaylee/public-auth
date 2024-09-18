package org.example.auth.adaptor.cms.controller

import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Tag(description = "게시판 관련", name = "Posts")
@RestController
@RequestMapping("/api/v1/")
class CommentController
