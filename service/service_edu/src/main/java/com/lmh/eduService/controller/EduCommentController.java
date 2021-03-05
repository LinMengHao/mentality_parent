package com.lmh.eduService.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lmh.eduService.client.UcenterClient;
import com.lmh.eduService.entity.EduComment;
import com.lmh.eduService.service.EduCommentService;
import com.lmh.orderVo.UcenterMemberOrder;
import com.lmh.utils.JwtUtils;
import com.lmh.utils.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * <p>
 * 评论 前端控制器
 * </p>
 *
 * @author lmh
 * @since 2021-03-01
 */
@Api(tags = "用户评功能")
//@CrossOrigin
@RestController
@RequestMapping("/eduService/edu-comment")
public class EduCommentController {
    @Autowired
    private EduCommentService commentService;
    @Autowired
    private UcenterClient ucenterClient;
    @ApiOperation(value = "分页查询评论")
    @GetMapping("commentList/{page}/{limit}")
    public R commentList( @ApiParam(name = "page", value = "当前页码", required = true)
                              @PathVariable Long page,

                          @ApiParam(name = "limit", value = "每页记录数", required = true)
                              @PathVariable Long limit,

                          @ApiParam(name = "courseQuery", value = "查询对象", required = false)
                                      String courseId){
        Page<EduComment> memberPage=new Page<>(page,limit);
        QueryWrapper<EduComment> wrapper=new QueryWrapper<>();
        if(!StringUtils.isEmpty(courseId)){
            wrapper.eq("course_id",courseId);
        }
        commentService.page(memberPage,wrapper);
        List<EduComment> commentList = memberPage.getRecords();

        Map<String, Object> map = new HashMap<>();
        map.put("items", commentList);
        map.put("current", memberPage.getCurrent());
        map.put("pages", memberPage.getPages());
        map.put("size", memberPage.getSize());
        map.put("total", memberPage.getTotal());
        map.put("hasNext", memberPage.hasNext());
        map.put("hasPrevious", memberPage.hasPrevious());
        return R.ok().data(map);
    }
    @ApiOperation(value = "添加评论")
    @PostMapping("saveComment")
    public R saveComment(@RequestBody EduComment comment, HttpServletRequest request){
        String memberIdByJwtToken = JwtUtils.getMemberIdByJwtToken(request);
        if(StringUtils.isEmpty(memberIdByJwtToken)){
            return R.error().code(999).message("请登录");
        }
        UcenterMemberOrder ucenterInfo = ucenterClient.getUserInfoOrder(memberIdByJwtToken);
        comment.setMemberId(memberIdByJwtToken);
        comment.setNickname(ucenterInfo.getNickname());
        comment.setAvatar(ucenterInfo.getAvatar());
        commentService.save(comment);
        return R.ok();
    }
    @ApiOperation(value = "删除评论")
    @DeleteMapping("deleteComment/{commentId}")
    public R delete(@PathVariable String commentId, HttpServletRequest request) {
        String memberIdByJwtToken = JwtUtils.getMemberIdByJwtToken(request);
        boolean result = commentService.deleteById(commentId, memberIdByJwtToken);
        if(result){
            return R.ok().message("删除评论成功");
        }else{
            return R.error().message("数据不存在");
        }
    }

    //判断是否是该用户的评论
    @ApiOperation(value = "判断是否是该用户的评论")
    @GetMapping("isComment/{commentId}")
    public R isComment(
            @ApiParam(name = "commentId", value = "评论id", required = true)
            @PathVariable String commentId,
            HttpServletRequest request) {

        String memberIdByJwtToken = JwtUtils.getMemberIdByJwtToken(request);
        boolean isComment = commentService.isComment(commentId, memberIdByJwtToken);
        return R.ok().data("isComment", isComment);
    }
}

