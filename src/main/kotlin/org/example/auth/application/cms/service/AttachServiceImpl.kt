package org.example.auth.application.cms.service

import org.example.auth.domain.cms.dao.PostsAttachFileJpaDao
import org.example.auth.domain.cms.entity.PostsAttachFile
import org.example.auth.infrastructure.file.FileHandler
import org.springframework.http.codec.multipart.FilePart
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.io.File
import java.time.LocalDate
import java.util.*
import kotlin.collections.ArrayList

@Service
class AttachServiceImpl(
    val attachFileDao: PostsAttachFileJpaDao,
    val fileHandler: FileHandler,
) : AttachService {
    //    @Value("\${ncloud.storage-url}")
//    lateinit var endPoint : String
//
//    @Value("\${ncloud.s3.bucket.posts}")
//    lateinit var bucket : String

//    val s3Service : S3Service by lazy {
//        S3Service(amazonS3, endPoint, bucket)
//    }

    override fun uploadFiles(files: List<MultipartFile>): List<PostsAttachFile> {
//        var resultList : MutableList<PostsAttachFile> = ArrayList()
//
//        files.forEach {
//            val fileKey = UUID.randomUUID().toString()
// //            var url = s3Service.upload(fileKey, it)
//            var url = ""
//            var attachFile = PostsAttachFile(fileKey = fileKey, filePath = url,
//                                            fileName=it.originalFilename!!, fileSize = it.size)
//            resultList.add(attachFile)
//        }

        var resultList = ArrayList<PostsAttachFile>()
        files.forEach {
            var attachFile = fileHandler.uploadFile(it)
            resultList.add(attachFile)
        }

        attachFileDao.saveAll(resultList)
        return resultList
//        attachFileDao.saveAll(resultList)
//        return resultList
    }

    override fun uploadAsyncFile(files: Flux<FilePart>): Mono<List<PostsAttachFile>> {
        return files.flatMap {
                filePart ->
            val fileKey = UUID.randomUUID().toString()
            var url = "/upload/${LocalDate.now()}/$fileKey"
            val file = File(url)
            filePart.transferTo(file)
                .then(
                    Mono.just(
                        PostsAttachFile(
                            fileKey = fileKey,
                            filePath = url,
                            fileName = filePart.filename(),
                            fileSize = file.length(),
                        ),
                    ),
                )
        }.collectList()
    }

//    override fun uploadFile(files: List<File>) : List<PostsAttachFile> {
//        TODO("Not yet implemented")
//    }

    override fun getPostsFile(postSeq: Long): List<PostsAttachFile> {
        return attachFileDao.findByPostSeq(postSeq)
    }

    override fun getFileInfo(attachFileSeq: Long): Optional<PostsAttachFile> {
        return attachFileDao.findById(attachFileSeq)
    }

    override fun getFile(attachFileSeq: Long): File {
        attachFileDao.findById(attachFileSeq).ifPresent {
            File(it.filePath)
        }

        // TODO 없는 파일이나 혹은 에러
        return File("")
    }
}
