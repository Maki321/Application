package com.project.service.result;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Objects;

@Data
public class ActionResult {
    private boolean success;
    private String message;
    @JsonIgnore
    private HttpStatus status;

    public <T> ResponseEntity<T> intoResponseEntity() {
        if (success)
            return (ResponseEntity<T>) ResponseEntity.ok(this);

        return (ResponseEntity<T>) ResponseEntity.status(status).body(this);
    }

    public ActionResult(boolean success, String message) {
        this.success = success;
        this.message = message;
        determineStatus(success);
    }

    public ActionResult(boolean success, String message, HttpStatus status) {
        this.success = success;
        this.message = message;
        this.status = status;
    }

    private void determineStatus(boolean success) {
        if (success)
            status = HttpStatus.OK;
        else
            status = HttpStatus.CONFLICT;
    }

    @Override
    public int hashCode() {
        return Objects.hash(success, message, status);
    }
}
