package com.kalugin.draggablegridview;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Response;

/**
 * Common class used by API responses.
 */
public class ApiResponse<T> {

    private final int code;
    @Nullable
    public final T body;
    @Nullable
    public String errorMessage;

    @SuppressWarnings("SameParameterValue")
    public ApiResponse(int code, @Nullable T body, @Nullable String errorMessage) {
        this.code = code;
        this.body = body;
        this.errorMessage = errorMessage;
    }

    public ApiResponse(Throwable error) {
        code = 500;
        body = null;
        errorMessage = error.getMessage();
    }

    public ApiResponse(Response<T> response) {
        code = response.code();
        if (response.isSuccessful()) {
            body = response.body();
            errorMessage = null;
//            if (body != null && body instanceof BaseResponse) {
//                BaseResponse baseResponse = (BaseResponse) body;
//                if (baseResponse.getError() != null) {
//                    errorMessage = baseResponse.getError().getMessage();
//                }
//            }
        } else {
            String message = null;
            ResponseBody responseBody = response.errorBody();
            if (responseBody != null) {
                try {
                    message = responseBody.string();
                } catch (IOException ex) {
                    if (BuildConfig.DEBUG) {
                        ex.printStackTrace();
                    }
                }
            }
            if (message == null || message.trim().length() == 0) {
                message = response.message();
            }
            errorMessage = message;
            body = null;
        }
    }

    public boolean isSuccessful() {
        return code >= 200 && code < 300;
    }

    @NonNull
    @Override
    public String toString() {
        return "ApiResponse{" +
                "code=" + code +
                ", body=" + body +
                ", errorMessage='" + errorMessage + '\'' +
                '}';
    }
}
