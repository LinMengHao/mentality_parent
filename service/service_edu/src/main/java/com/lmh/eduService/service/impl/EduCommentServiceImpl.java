package com.lmh.eduService.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lmh.eduService.entity.EduComment;
import com.lmh.eduService.mapper.EduCommentMapper;
import com.lmh.eduService.service.EduCommentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 评论 服务实现类
 * </p>
 *
 * @author lmh
 * @since 2021-03-01
 */
@Service
public class EduCommentServiceImpl extends ServiceImpl<EduCommentMapper, EduComment> implements EduCommentService {

    @Override
    public boolean deleteById(String commentId, String memberIdByJwtToken) {
        QueryWrapper<EduComment> queryWrapper = new QueryWrapper<>();
        queryWrapper
                .eq("id", commentId)
                .eq("member_id", memberIdByJwtToken);
        int delete = baseMapper.delete(queryWrapper);
        if(delete>0){
            return true;
        }
        return false;
    }

    @Override
    public boolean isComment(String commentId, String memberIdByJwtToken) {
        QueryWrapper<EduComment> queryWrapper = new QueryWrapper<>();
        queryWrapper
                .eq("id", commentId)
                .eq("member_id", memberIdByJwtToken);
        Integer count = baseMapper.selectCount(queryWrapper);
        return count.intValue() > 0;
    }
}
