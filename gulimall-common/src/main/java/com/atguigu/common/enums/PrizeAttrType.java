package com.atguigu.common.enums;

/**
 * @author yubo
 * 属性类型枚举类
 */
public enum PrizeAttrType {

    BaseType("base", 1),

    SaleType("sale", 0);

    private String typeName;

    private int code;

    PrizeAttrType(String typeName, int code) {
        this.typeName = typeName;
        this.code = code;
    }

    public String getTypeName() {
        return typeName;
    }

    public int getCode() {
        return code;
    }
}
