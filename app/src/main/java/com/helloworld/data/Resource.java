package com.helloworld.data;

import static com.helloworld.data.Status.ERROR;
import static com.helloworld.data.Status.LOADING;
import static com.helloworld.data.Status.SUCCESS;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class Resource<T> {
    @NonNull
    public final Status status;
    @Nullable
    public final String message;
    @Nullable
    public final T data;
    public Resource(@NonNull Status status, @Nullable T data, @Nullable String message) {
        this.status = status;
        this.data = data;
        this.message = message;
    }
    public static <T> Resource<T> success(@Nullable T data) {
        return new Resource<>(SUCCESS, data, null);
    }
    public static <T> Resource<T> error(String msg) {
        return new Resource<>(ERROR, null, msg);
    }
    public static <T> Resource<T> loading() {
        return new Resource<>(LOADING, null, null);
    }

}
