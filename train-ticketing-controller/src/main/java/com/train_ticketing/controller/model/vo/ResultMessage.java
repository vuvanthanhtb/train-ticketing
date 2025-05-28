package com.train_ticketing.controller.model.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class ResultMessage<T> implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * Cờ thành công
     */
    private boolean success;

    /**
     * Thông báo
     */
    private String message;

    /**
     * Mã trả về
     */
    private Integer code;

    /**
     * Dấu thời gian
     */
    private long timestamp = System.currentTimeMillis();

    /**
     * Đối tượng kết quả
     */
    private T result;
}
