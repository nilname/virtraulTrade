//package com.ooteco.controller;
//
//import com.ooteco.utils.ResultFactory;
//import com.ooteco.service.UploaderService;
//import io.swagger.annotations.*;
//import org.apache.log4j.Logger;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.multipart.MultipartFile;
//
//import javax.servlet.http.HttpServletRequest;
//import java.io.File;
//import java.io.FileOutputStream;
//import java.util.UUID;
//
///**
// * Created by shenyu on 2018/02/12.
// */
//@Api(value = "/uploader", description = "上传相关接口")
//@RestController
//@RequestMapping(value = "/uploader")
//public class UploaderController extends AbstractController{
//
//    private static Logger log = Logger.getLogger(UploaderController.class);
//
//    @Value("${uploader.basepath}")
//    private String basePath;
//
//    @ApiOperation(value = "上传图片", notes = "上传图片")
//    @ApiImplicitParam(name = "file", value = "图片文件", required = false, dataType = "file", paramType="form")
//    @ApiResponses(value = {
//            @ApiResponse(code = ResultFactory.SUCCESS, message = "D://IdeaProjects/wb/zk/virtualTrade/trunk/upload/image/16.png") ,
//            @ApiResponse(code = ResultFactory.FAIL, message = "校验失败"),
//            @ApiResponse(code = ResultFactory.EXCEPTION, message = "服务异常")})
//    @ResponseBody
//    @RequestMapping(value = "/image", produces = {"application/json;charset=UTF-8"}, method = RequestMethod.POST)
//    public String image(@RequestParam("file") MultipartFile file, HttpServletRequest request){
//        //校验文件是否为空
//        if(file.isEmpty()) {
//            log.error("uploader,image,file is empty.");
//            return renderResult(ResultFactory.newFail("文件不能为空"));
//        }
//        //校验文件类型
//        UploaderService uploaderService = new UploaderService();
//        String originalFilename = file.getOriginalFilename();
//        String suffix = originalFilename.substring(originalFilename.lastIndexOf(".")+1);
//        if(!uploaderService.validImageFileType(suffix)) {
//            log.error("uploader,image,file type.");
//            return renderResult(ResultFactory.newFail("文件格式不正确"));
//        }
//        //校验文件大小
//        if(!uploaderService.validImageFileSize(file.getSize())) {
//            log.error("uploader,image,file size.");
//            return renderResult(ResultFactory.newFail("文件大小超出限制"));
//        }
//
//        String filePath = (basePath!=null ? basePath : request.getSession().getServletContext().getRealPath("upload/")) +"images/";
//        try {
//            File targetFile = new File(filePath);
//            if(!targetFile.exists()){
//                targetFile.mkdirs();
//            }
//
//            String fileName;
//            do{
//                fileName = UUID.randomUUID().toString().replaceAll("-", "") +"."+ suffix;
//            }while (new File(filePath+fileName).exists());
//
//            FileOutputStream out = new FileOutputStream(filePath+fileName);
//            out.write(file.getBytes());
//            out.flush();
//            out.close();
//
//            log.info(String.format("uploader,image,filepath:%s", filePath+fileName));
//            return renderResult(ResultFactory.newSucc("/upload/images/"+fileName));
//        } catch (Exception e) {
//            //e.printStackTrace();
//            log.error(String.format("uploader,image,exception:%s", e));
//            return renderResult(ResultFactory.exception());
//        }
//    }
//}
