package com.lmh.eduService.service;

import com.lmh.eduService.entity.EduComment;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 评论 服务类
 * </p>
 *
 * @author lmh
 * @since 2021-03-01
 */
public interface EduCommentService extends IService<EduComment> {

    boolean deleteById(String commentId, String memberIdByJwtToken);

    boolean isComment(String commentId, String memberIdByJwtToken);
}
