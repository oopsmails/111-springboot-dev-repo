package com.oopsmails.springboot.jpa.mongodb.springbootmongodb.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "todos")
public class TodoDTO {
    @Id
    private String id;
    @NotEmpty(message = "todo cannot be empty")
    @NotNull(message = "todo cannot be null")
    private String todo;
    @NotEmpty(message = "description cannot be empty")
    @NotNull(message = "description cannot be null")
    private String description;
    @NotNull(message = "completed cannot be null")
    private Boolean completed;
    private Date createdAt;
    private Date UpdatedAt;
}
