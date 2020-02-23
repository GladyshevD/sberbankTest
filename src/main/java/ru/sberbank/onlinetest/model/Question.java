package ru.sberbank.onlinetest.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class Question extends AbstractBaseEntity {

    @NotBlank
    @Size(min = 6, max = 255)
    private String questionItem;

    private Set<Option> options = new HashSet<>();

    public Question(Integer id, @NotNull Date createdAt, String questionItem) {
        super(id, createdAt);
        this.questionItem = questionItem;
    }

    public void addOption(Option option) {
        this.options.add(option);
    }
}
