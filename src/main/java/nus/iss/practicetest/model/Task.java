package nus.iss.practicetest.model;

import java.util.Date;
import java.util.UUID;

import jakarta.validation.constraints.*;

public class Task {
    @Size(max = 50, message = "Max length for ID is 50 characters")
    private String id;
    @Size(min = 10, max = 50, message = "Name should be between 10 and 50 characters")
    private String name;
    @Size(max = 255, message = "Max length for description is 255 characters")
    private String description;
    @FutureOrPresent(message = "Date cannot be in the past")
    private Date dueDate;
    private String priority;
    private String status;
    private Date createdAt;
    private Date updatedAt;

    public Task() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String generateId() {
        return UUID.randomUUID().toString();
    }

    @Override
    public String toString() {
        return id + "," + name + "," + description + "," + dueDate.getTime()
                + "," + priority + "," + status + "," + createdAt.getTime() + ","
                + updatedAt.getTime();
    }

}
