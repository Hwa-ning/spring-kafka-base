package com.douzone.kafka.model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Student {
    private String registrationNumber;
    private String name;
    private String grade;
}
