package com.test.api_0822_sh.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.time.LocalDate;

/**
 * User DTO model
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
public class UserDTO {

    @ApiModelProperty(value = "Id of the user", name = "id", example = "1")
    private Long id;
    @NonNull
    @ApiModelProperty(value = "Name of the user", name = "name", example = "test name")
    private String name;
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    @NonNull
    @ApiModelProperty(value = "Birthday of the user", name = "birthday", example = "01-01-1901")
    private LocalDate birthday;
    @NonNull
    @ApiModelProperty(value = "Country of the user", name = "country", example = "France")
    private String country;
    @ApiModelProperty(value = "Phone number of the user", name = "phoneNumber", example = "0123456789")
    private String phoneNumber;
    @ApiModelProperty(value = "Gender of the user", name = "gender", example = "M")
    private Character gender;

}
