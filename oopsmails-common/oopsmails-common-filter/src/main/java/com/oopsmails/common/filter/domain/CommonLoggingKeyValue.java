package com.oopsmails.common.filter.domain;

import com.fasterxml.jackson.annotation.JsonRawValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommonLoggingKeyValue {
    private String key;
    private String value;

    @Override
    @JsonValue
    @JsonRawValue
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("\"");
        stringBuilder.append(key);
        stringBuilder.append("\":\"");
        stringBuilder.append(value);

        return stringBuilder.toString();
    }
}
