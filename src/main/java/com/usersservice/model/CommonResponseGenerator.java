package com.usersservice.model;

import org.springframework.stereotype.Component;

@Component
public class CommonResponseGenerator<T> {

    public CommonResponse<T> successResponse(T data, String message){
        CommonResponse<T> commonResponse = new CommonResponse<>();
        commonResponse.setStatus("200");
        commonResponse.setMessage(message);
        commonResponse.setData(data);

        return commonResponse;
    }
    public CommonResponse<T> failedResponse(T data, String message){
        CommonResponse<T> commonResponse = new CommonResponse<>();
        commonResponse.setStatus("500");
        commonResponse.setMessage(message);

        return commonResponse;
    }

    public CommonResponse<T> failedClientResponse(String code, String message){
        CommonResponse<T> commonResponse = new CommonResponse<>();
        commonResponse.setStatus(code);
        commonResponse.setMessage(message);

        return commonResponse;
    }
}
