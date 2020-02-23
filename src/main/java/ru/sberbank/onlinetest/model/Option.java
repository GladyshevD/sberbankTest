package ru.sberbank.onlinetest.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class Option extends AbstractBaseEntity {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Question question;

    @NotBlank
    @Size(min = 6, max = 255)
    private String optionItem;
}
