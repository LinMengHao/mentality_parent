package com.lmh.eduService.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lmh.eduService.entity.EduPsychologist;
import com.lmh.eduService.service.EduPsychologistService;
import com.lmh.eduService.entity.vo.PsychologistQuery;
import com.lmh.utils.R;
import com.lmh.utils.ResultCode;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 讲师 前端控制器
 *
 * @author lmh
 * @since 2020-12-30
 */

//@CrossOrigin
@Api(tags = "心理讲师")
@RestController
@RequestMapping("/eduService/edu-psychologist")
public class EduPsychologistController {
  @Autowired private EduPsychologistService eduPsychologistService;
  @ApiOperation("获取所有心理医师列表")
  @GetMapping("getAllList")
  public R getAllList(){
      List<EduPsychologist> list = eduPsychologistService.list(null);
      return R.ok().data("items", list);
  }

  @ApiOperation("条件分页查询")
  @PostMapping("findAll/{current}/{size}")
  public R findAll(
      @PathVariable(name = "current") long current,
      @PathVariable(name = "size") long size,
      @RequestBody(required = false) PsychologistQuery psychologistQuery) {
      String email = psychologistQuery.getEmail();
      String identityCard = psychologistQuery.getIdentityCard();
      Integer level = psychologistQuery.getLevel();
      String name = psychologistQuery.getName();
      String phoneNumber = psychologistQuery.getPhoneNumber();
      Integer sort = psychologistQuery.getSort();
      QueryWrapper<EduPsychologist>wrapper=new QueryWrapper<>();
      if(!StringUtils.isEmpty(email)){
          wrapper.like("email",email);
      }
      if(!StringUtils.isEmpty(identityCard)){
          wrapper.like("identity_card",identityCard);
      }
      if(!StringUtils.isEmpty(level)){
          wrapper.eq("level",level);
      }
      if(!StringUtils.isEmpty(name)){
          wrapper.like("name",name);
      }
      if(!StringUtils.isEmpty(phoneNumber)){
          wrapper.like("phone_number",phoneNumber);
      }
      if(!StringUtils.isEmpty(sort)){
          wrapper.eq("sort",sort);
      }

      wrapper.orderByDesc("gmt_create");
      Page<EduPsychologist> page=new Page<>(current,size);
      eduPsychologistService.page(page,wrapper);
      List<EduPsychologist> records = page.getRecords();
      long total = page.getTotal();
      return R.ok().data("total",total).data("records",records);
  }
  @ApiOperation("添加心理讲师")
    @PostMapping("addPsychologist")
    public R addPsychologist(@RequestBody EduPsychologist eduPsychologist){
      boolean flag=eduPsychologistService.save(eduPsychologist);
    if (flag) {
      return R.ok();
      }
    return R.error().code(ResultCode.SAVE_ERROR.getCode()).message(ResultCode.SAVE_ERROR.getMessage());
  }
  @ApiOperation("修改心理讲师")
    @PostMapping("updatePsychologist")
    public R updatePsychologist(@RequestBody EduPsychologist eduPsychologist){
      boolean b = eduPsychologistService.updateById(eduPsychologist);
      if(b){
          return R.ok();
      }
      return R.error().code(ResultCode.PARAM_NOT_VALID.getCode()).message(ResultCode.PARAM_NOT_VALID.getMessage());
  }
  @ApiOperation("根据id查询讲师详情")
    @GetMapping("getById/{id}")
    public R getById(@PathVariable String id){
      EduPsychologist psychologist = eduPsychologistService.getById(id);
      return R.ok().data("psychologist",psychologist);
  }
  @ApiOperation("删除")
    @DeleteMapping("delete/{id}")
    public R delete(@ApiParam(name = "id", value = "讲师id", required = true) @PathVariable("id") String id){
      boolean b = eduPsychologistService.removeById(id);
      if(b){
          return R.ok();
      }
      return R.error().code(ResultCode.PARAM_NOT_VALID.getCode()).message(ResultCode.PARAM_NOT_VALID.getMessage());
  }
}
