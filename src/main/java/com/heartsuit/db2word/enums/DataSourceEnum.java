package com.heartsuit.db2word.enums;

/**
 * Author:  Heartsuit
 * Date:  2021/6/8 17:59
 */
public enum DataSourceEnum {
    PG("PG"), MYSQL("MYSQL");

    private String value;

    DataSourceEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}
