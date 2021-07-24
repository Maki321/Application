package com.project.service.result;

import org.springframework.http.HttpStatus;

import java.util.Objects;

public class DataResult<T> extends ActionResult {
    private T data;

    public DataResult(boolean success, T data) {
        super(success, null);
        this.data = data;
    }

    public DataResult(boolean success, String message, T data) {
        super(success, message);
        this.data = data;
    }

    public DataResult(boolean success, String message, HttpStatus status, T data) {
        super(success, message, status);
        this.data = data;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        DataResult<?> that = (DataResult<?>) o;
        return Objects.equals(data, that.data) && this.isSuccess() == that.isSuccess() &&
                Objects.equals(this.getMessage(), that.getMessage()) && this.getStatus() == that.getStatus();
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), data);
    }
}

