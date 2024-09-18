package org.example.auth.view

import org.example.auth.adaptor.cms.dto.PostsDto
import org.example.auth.application.cms.service.PostService
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping("/board")
class BoardController(
    val postService: PostService,
//    val postsAttachFileDao : PostsAttachFileDao,
//    val postsDao: PostsDao
) {
    @GetMapping("/loan/show")
    fun loanShow(model: Model): String {
        return "loan/show2"
    }

    @GetMapping("/{boardType}")
    fun boardList(
        @PathVariable boardType: String,
//                  @ModelAttribute param : PostsParam,
        model: Model,
        @PageableDefault(page = 0, size = 20) pageable: Pageable,
    ): String {
        model.addAttribute("list", emptyList<String>())
        return "board/index"
    }

    @GetMapping("/{boardType}/posts")
    fun posts(
        @PathVariable boardType: String,
        model: Model,
    ): String {
//        model.addAttribute("tags", postsDao.getTag())
        model.addAttribute("post", PostsDto())
        return "board/postdetail"
    }

    @GetMapping("/{boardType}/posts/{postSeq}")
    fun postsModify(
        @PathVariable boardType: String,
        @PathVariable postSeq: Long,
        model: Model,
    ): String {
//        model.addAttribute("tags", postsDao.getTag())
//        model.addAttribute("post", postsDao.getPost(boardType, postSeq))

        return "board/postdetail"
    }

//
//    @ResponseBody
//    @PostMapping("/{boardType}/posts")
//    fun saveAction(@PathVariable boardType : String, @RequestBody posts : Any) : Any {
//        return postsDao.savePost(boardType, posts)
//    }
//
//
//    @ResponseBody
//    @PostMapping("/attachFile")
//    fun saveAttach(request : HttpServletRequest) : Any {
//
//        val fileInfo: MultiValueMap<String, Any> = LinkedMultiValueMap()
//
//        var multipartRequest = request as MultipartHttpServletRequest
//
//        multipartRequest.getFiles("file").forEach {
//            // null이 아니면 추가
//            fileInfo.add("file", object : ByteArrayResource(it.bytes) {
//                override fun getFilename() = it.originalFilename
//            })
//        }
//
//        println(fileInfo)
//
//        return postsAttachFileDao.saveAttachFile(fileInfo)
//    }
//
//    @ResponseBody
//    @PostMapping("/post/order")
//    fun reOrderPosts(@RequestBody postOrder : List<Long>) : Any {
//        return postsDao.reOrderPosts(postOrder)
//    }
//
//    @ResponseBody
//    @PostMapping("/post/status/{status}")
//    fun updateStstus(@RequestBody postOrder : List<Long>, @PathVariable status : String) : Any {
//        return postsDao.updateStatus(postOrder, status)
//    }
//
//    @ResponseBody
//    @GetMapping("/tags")
//    fun getTag() : Any {
//        return postsDao.getTag()
//    }
//
//    @ResponseBody
//    @PostMapping("/tags")
//    fun saveTag(@RequestBody tagList : List<String>) : Any {
//        return postsDao.appendTag(tagList)
//    }
//
}
