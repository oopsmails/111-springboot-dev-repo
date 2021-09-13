package com.oopsmails.springboot.mockbackend.model.general;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.concurrent.TimeUnit;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OopsTimeout implements Serializable {
    private static final long serialVersionUID = 1L;
    private int timeout;
    private TimeUnit timeUnit = TimeUnit.SECONDS;
}
