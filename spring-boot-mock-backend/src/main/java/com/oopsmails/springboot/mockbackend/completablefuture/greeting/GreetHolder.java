package com.oopsmails.springboot.mockbackend.completablefuture.greeting;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(of = {"greet"}, callSuper = false)
public class GreetHolder {
    private String greet;
}
