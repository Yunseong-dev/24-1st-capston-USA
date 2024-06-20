package com.capstone.usa.job.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CreateJobDto {
    private String title;
    private String content;
}
