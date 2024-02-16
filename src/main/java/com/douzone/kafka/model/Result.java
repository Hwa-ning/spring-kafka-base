package com.douzone.kafka.model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Result {
    private String name;
    private String percentage;
    private String result;
}