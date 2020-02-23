package ru.sberbank.onlinetest.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@NoArgsConstructor
public abstract class AbstractBaseEntity {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Integer id;

    @NotNull
    private Date createdAt = new Date();

    public AbstractBaseEntity(Integer id) {
        this.id = id;
    }

    public AbstractBaseEntity(Integer id, @NotNull Date createdAt) {
        this.id = id;
        this.createdAt = createdAt;
    }
}
