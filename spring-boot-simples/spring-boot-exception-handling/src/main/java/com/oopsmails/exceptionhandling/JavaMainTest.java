package com.oopsmails.exceptionhandling;

import com.oopsmails.exceptionhandling.institution.domain.BranchDto;
import com.oopsmails.exceptionhandling.institution.entity.BranchSimple;
import com.oopsmails.exceptionhandling.model.Source;
import com.oopsmails.exceptionhandling.model.Target;
import org.apache.commons.beanutils.BeanUtils;

public class JavaMainTest {
    public static void main(String[] args) throws Exception {
        Source source = new Source();
        source.setName("John Doe");
        source.setAge(30);
        source.setEmail("john.doe@example.com");

        Target target = new Target();
        BeanUtils.copyProperties(target, source);

        System.out.println("Target name: " + target.getName());
        System.out.println("Target age: " + target.getAge());
        System.out.println("Target email: " + target.getEmail());

        BranchSimple branchSimple = new BranchSimple();
        branchSimple.setBranchId(1L);
        branchSimple.setBranchName("abc");

        BranchDto branchDto = new BranchDto();
        BeanUtils.copyProperties(branchDto, branchSimple);

        System.out.println("branchDto: " + branchDto);
    }
}
