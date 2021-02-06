package com.lmh.vod.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface VodService {
    String uploadAlyVideo(MultipartFile file);

    void deleteAlyVideo(String id);

    void deleteAlyVideoList(List<String> listVideoIds);
}
