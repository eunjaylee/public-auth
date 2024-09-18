package org.example.auth.application.cms.service

import org.example.auth.domain.cms.entity.PostReadCheck
import org.example.auth.domain.cms.entity.PostsReaction

interface PostReactionService {
    /**
     * 좋아요/ 싫어요 혹은 기존 리액션 삭제 등의 액션을 실행한다.
     */
    fun doPostAction(reaction: PostsReaction)

    /**
     * 읽음 여부 처리
     */
    fun readAction(readAction: PostReadCheck)
}
