package org.example.auth.application.cms.service

import org.example.auth.domain.cms.dao.PostReActionSummeryJpaDao
import org.example.auth.domain.cms.dao.PostReactionJpaDao
import org.example.auth.domain.cms.dao.PostReadCheckJpaDao
import org.example.auth.domain.cms.entity.*
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
class PostReactionServiceImpl(
    private val postReactionDao: PostReactionJpaDao,
    private val postReadCheckJpaDao: PostReadCheckJpaDao,
    private val postReActionSummeryDao: PostReActionSummeryJpaDao,
) : PostReactionService {
    /**
     * 기존에 리액션이 있는지에 따라 좋아요 -> 싫어요 가능 좋아요 두번이면 삭제 등
     * 동시성 이슈가 없나?
     */
    @Transactional
    override fun doPostAction(reaction: PostsReaction) {
        if (postReactionDao.findById(reaction.pk).isPresent) {
            postReActionSummeryDao.findById(
                PostActionPk(postSeq = reaction.pk.postSeq, postActionType = reaction.pk.postReactionType),
            ).ifPresent {
                it.postActionCnt -= 1
            }
            postReactionDao.deleteById(reaction.pk)
        } else {
            val pk = PostActionPk(postSeq = reaction.pk.postSeq, postActionType = reaction.pk.postReactionType)
            val postReActionSummery = postReActionSummeryDao.findById(pk)
            summeryAction(postReActionSummery, pk)
            postReactionDao.save(reaction)
        }
    }

    override fun readAction(readAction: PostReadCheck) {
        // 읽은 내역이 없으면 증가하자.. 읽을때마다 찾아야 하나?
        if (!postReadCheckJpaDao.findById(readAction.pk).isPresent) {
            val pk = PostActionPk(postSeq = readAction.pk.postSeq, postActionType = "READ") // TODO enum
            val postReActionSummery = postReActionSummeryDao.findById(pk)
            summeryAction(postReActionSummery, pk)
            postReadCheckJpaDao.save(readAction)
        }
    }

    private fun summeryAction(
        postReActionSummery: Optional<PostReActionSummery>,
        pk: PostActionPk,
    ) {
        if (postReActionSummery.isPresent) {
            postReActionSummery.get().postActionCnt += 1
        } else {
            postReActionSummeryDao.save(PostReActionSummery(1, pk))
        }
    }
}
