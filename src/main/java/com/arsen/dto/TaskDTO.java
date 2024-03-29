package com.arsen.dto;

import com.arsen.enams.TaskStatus;
import com.arsen.models.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskDTO {
    private Long id;

    @NotEmpty(message = "Header не может быть пустым!")
    @Size(max = 20, message = "Превышено максимальное значение для поля header!")
    private String header;

    @Size(max = 50, message = "Превышено максимальное значение для поля description!")
    private String description;

    @NotNull(message = "Deadline не может быть пустым!")
    @Temporal(value = TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private Date deadline;

    private TaskStatus taskStatus;

    private User owner;
}
