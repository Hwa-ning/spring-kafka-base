package com.douzone.kafka.model;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class MessageModel {
    private String empNo;
    private String deptCd;
    private String deptNm;
    private String requestDate;
    private String completeDate;
    private int hasNext;
}