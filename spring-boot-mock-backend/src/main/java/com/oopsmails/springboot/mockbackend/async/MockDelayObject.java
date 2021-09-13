package com.oopsmails.springboot.mockbackend.async;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = {"mockStringField"}, callSuper = false)
@ToString
public class MockDelayObject {
    private String mockStringField;
}
