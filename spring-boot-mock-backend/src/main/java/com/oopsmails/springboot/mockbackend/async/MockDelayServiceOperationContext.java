package com.oopsmails.springboot.mockbackend.async;

import lombok.Data;

import java.util.List;

@Data
public class MockDelayServiceOperationContext extends OperationContext {
    private List<String> byIds;
}
