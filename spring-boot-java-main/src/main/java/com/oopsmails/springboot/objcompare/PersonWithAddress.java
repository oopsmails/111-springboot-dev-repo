package com.oopsmails.springboot.objcompare;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PersonWithAddress {
    private Integer id;
    private String name;
    private List<Address> address;
}
