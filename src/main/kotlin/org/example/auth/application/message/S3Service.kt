package org.example.auth.application.message
//
// import com.amazonaws.HttpMethod
// import com.amazonaws.services.s3.AmazonS3
// import com.amazonaws.services.s3.model.*
// import mu.KLogging
// import org.springframework.http.ContentDisposition
//
// import org.springframework.web.multipart.MultipartFile
// import java.io.*
// import java.net.URL
// import java.net.URLConnection
// import java.util.*
//
// class S3Service (val amazonS3 : AmazonS3, val endPoint : String, val bucket : String) {
//    companion object : KLogging()
//
//    /**
//     * get S3 URL. if [cdnUrl] provided will use to generate full URL
//     */
//    fun url(key: String) = "$endPoint/$bucket/${key.trimStart('/')}"
//
//    /**
//     * put string [contents] with [key]
//     */
//    fun putContents(key: String, contents: String) = amazonS3.putObject(bucket, key.trimStart('/'), contents)
//
//    /**
//     * upload local disk file
//     */
//    fun upload(key: String, filePath: String) =
//        upload(key, File(filePath))
//
//    fun upload(key: String, file: File, originalFilename: String? = null) =
//        upload(key, file.inputStream(), file.length(), null, originalFilename ?: file.name)
//
//    fun upload(key: String, file: MultipartFile) =
//        upload(key, file.inputStream, file.size, file.contentType, file.originalFilename)
//
//    fun upload(
//        key: String,
//        inputStream: InputStream,
//        contentLength: Long = 0L,
//        contentType: String? = null,
//        originalFilename: String? = null
//    ): String {
//        val meta = ObjectMetadata()
//
//        var input = inputStream
//        var size = contentLength
//        if (size <= 0L) {
//            // contentLength 가 제공되지 않은 경우 inputStream 을 읽어서 계산
//            size = 0L
//            if (input.markSupported()) { // just read if reset() available
//                val buffer = ByteArray(1024)
//                var bytes = input.read(buffer)
//                while (bytes >= 0) {
//                    size += bytes
//                    bytes = input.read(buffer)
//                }
//                input.reset()
//            } else {
//                val out = ByteArrayOutputStream(1024)
//                size = input.copyTo(out)
//                input = ByteArrayInputStream(out.toByteArray())
//            }
//        }
//        if (size < 1L) throw RuntimeException("0 length file")
//
//        meta.contentLength = size
//        meta.contentType = contentType ?: URLConnection.guessContentTypeFromName(key)
//
//        if (!originalFilename.isNullOrEmpty()) {
//            meta.contentDisposition = ContentDisposition.builder("inline")
//                .filename(originalFilename, Charsets.UTF_8)
//                .build().toString()
//        }
//
//        var putObj = PutObjectRequest(bucket, key, inputStream, meta)
//                    .withCannedAcl(CannedAccessControlList.PublicRead)
//
//        amazonS3.putObject(putObj)
//
//        return url(key)
//    }
//
//    /**
//     * get [InputStream]
//     */
//    fun read(key: String): InputStream? = if (!exists(key)) null
//    else amazonS3.getObject(bucket, key.trimStart('/'))?.objectContent
//
//    /**
//     * ttl 이후에 만료되는 URL을 생성합니다.
//     * @param fileName 다운로드 될 파일이름을 지정합니다. null 이면 S3에 지정된 메타정보 그대로 다운로드 됩니다.
//     * @param ttl URL 유효시간. 단위: 초. 기본값 600초(=10분)
//     * @param attachment [fileName]이 지정된 경우 Content-Disposition 의 type 을 attachment 로 줄것인지 inline 으로 줄것인지..
//     */
//    fun signedUrl(key: String, fileName: String? = null, ttl: Long = 600L, attachment: Boolean = true): URL {
//        val req = GeneratePresignedUrlRequest(bucket, key.trimStart('/'))
//            .withMethod(HttpMethod.GET)
//            .withExpiration(Date(System.currentTimeMillis() + ttl * 1000))
//
//        if (!fileName.isNullOrEmpty()) req.withResponseHeaders(
//            ResponseHeaderOverrides()
//                .withContentType(URLConnection.guessContentTypeFromName(fileName))
//                .withContentDisposition(
//                    ContentDisposition.builder(if (attachment) "attachment" else "inline")
//                        .filename(fileName, Charsets.UTF_8).build().toString()
//                )
//        )
//
//        return amazonS3.generatePresignedUrl(req)
//    }
//
//    /**
//     * check [key] is exists
//     */
//    fun exists(key: String) = amazonS3.doesObjectExist(bucket, key.trimStart('/'))
//
//    fun delete(key: String) = amazonS3.deleteObject(bucket, key.trimStart('/'))
//
//    fun md5Path(baseDir: String, md5: String, ext: String? = null): String {
//        val sb = StringBuilder(baseDir.trimEnd('/')).append('/')
//            .append(md5[0]).append(md5[1]).append('/')
//            .append(md5[2]).append(md5[3]).append('/')
//            .append(md5[4]).append(md5[5]).append('/')
//            .append(md5.substring(6))
//        if (!ext.isNullOrEmpty()) sb.append(".").append(ext.toLowerCase())
//        return sb.toString()
//    }
//
//    fun readString(key: String):String? {
//        var result : String = ""
//        if (!exists(key))
//            return null
//        else {
//            var s3is : S3ObjectInputStream = amazonS3.getObject(bucket, key.trimStart('/')).objectContent
//            val reader = BufferedReader(InputStreamReader(s3is))
//            var line: String? = null
//            while (reader.readLine().also { line = it } != null) {
//                result += line
//            }
//        }
//        return result
//    }
// }
