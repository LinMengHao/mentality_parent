package com.lmh.eduService.client.impl;

import com.lmh.eduService.client.VodClient;
import com.lmh.utils.R;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public class VodClientImpl implements VodClient {
    @Override
    public R deleteAlyVideo(String id) {
        return R.error().message("连接超时");
    }

    @Override
    public R deleteAlyVideoList(List<String> listVideoIds) {
        return R.error().message("连接超时");
    }
}
