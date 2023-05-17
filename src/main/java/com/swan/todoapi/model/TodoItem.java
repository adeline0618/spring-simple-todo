package com.swan.todoapi.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Entity
@Table(name = "todo_item")
public class TodoItem {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Getter
    @Setter
    private Long id;

    @Getter
    @Setter
    @NotBlank(message = "Description is required")
    private String description;

    @Getter
    @Setter
    private boolean completed;

    @Column(name = "created_date")
    @Getter
    @Setter
    private Instant createdDate;

    @Column(name = "modified_date")
    @Getter
    @Setter
    private Instant modifiedDate;

    public TodoItem() {
    }

    public TodoItem(String description, boolean completed, Instant createdDate, Instant modifiedDate) {
        this.description = description;
        this.completed = completed;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
    }

    @Override
    public String toString() {
        return "TodoItem{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", completed=" + completed +
                ", createdDate=" + createdDate +
                ", modifiedDate=" + modifiedDate +
                '}';
    }
}
