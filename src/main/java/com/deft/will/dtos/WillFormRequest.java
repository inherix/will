package com.deft.will.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.time.LocalDate;
import java.util.Date;


@Getter
@Setter

@Data
public class WillFormRequest {

    @JsonProperty("firstName")
    private final String firstName;
    @JsonProperty("lastName")
    private final String lastName;
    @JsonProperty("email")
    private final String email;
    @JsonProperty("mobile")
    private final String mobile;
    @JsonProperty("DoB")
    @JsonFormat(pattern="yyyy-MM-dd")
    private final LocalDate dob;
    @JsonProperty("relation")
    private final String relationShip;
}
