package com.dmaksimn.store.enums;

public enum OrderStatusEnum implements CodeEnum {
    NEW(0, "New OrderMain"),
    FINISHED(1, "Finished"),
    CANCELED(2, "Canceled")
    ;

    private int code;
    private String status;

    OrderStatusEnum(Integer code, String status) {
        this.code = code;
        this.status = status;
    }

    @Override
    public Integer getCode() {
        return code;
    }

}
