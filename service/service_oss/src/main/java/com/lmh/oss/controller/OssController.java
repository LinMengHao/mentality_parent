package com.lmh.oss.controller;

import com.lmh.oss.service.OssService;
import com.lmh.utils.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Api(tags = "阿里云Oss存储技术")
@CrossOrigin
@RestController
@RequestMapping("/ossService/fileOss")
public class OssController {
    @Autowired
    private OssService ossService;
    @ApiOperation("上传头像，并返回头像访问地址")
@PostMapping("avatar")
    public R uploadOssFile(MultipartFile file){
        String url=ossService.uploadFileAvatar(file);
    return R.ok().data("url",url);
}
}
