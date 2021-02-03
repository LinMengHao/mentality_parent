package com.lmh.eduService.entity.vo;

import lombok.Data;

@Data
public class CourseQuery {
    private String title;
    private String status;
    private String psychologistId;
    private String subjectId;
    private String subjectParentId;
}
