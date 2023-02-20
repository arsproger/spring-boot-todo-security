package com.arsen.dto;

import com.arsen.enams.TaskStatus;
import com.arsen.models.User;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

@Data
public class TaskDTO {
    private String header;
    private String description;
    @Temporal(value = TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private Date deadline;
    private TaskStatus taskStatus;
    private User owner;
}
