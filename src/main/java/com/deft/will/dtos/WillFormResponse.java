package com.deft.will.dtos;

import lombok.*;
import org.bson.codecs.pojo.annotations.BsonId;

import java.time.LocalDate;
import java.util.Date;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
public class WillFormResponse {
    @BsonId
    private String Id;
    private String firstName;
    private String lastName;
    private String email;
    private String mobile;
    private LocalDate DoB;
    private String relation;
}
