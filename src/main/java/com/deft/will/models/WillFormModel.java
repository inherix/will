package com.deft.will.models;

import lombok.*;
import org.bson.codecs.pojo.annotations.BsonId;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
@Document(collection="WillForm")
public class WillFormModel
{
    @BsonId
    private String id;
    private  String firstName;
    private  String lastName;
    private  String email;
    private  String mobile;
    private LocalDate dob;
    private String relationShip;



}
