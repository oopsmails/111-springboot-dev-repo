package com.oopsmails.exceptionhandling;

import com.oopsmails.exceptionhandling.institution.domain.BranchDto;
import com.oopsmails.exceptionhandling.institution.entity.BranchSimple;
import com.oopsmails.exceptionhandling.model.DestinationBean;
import com.oopsmails.exceptionhandling.model.NestedBean;
import com.oopsmails.exceptionhandling.model.NestedBean2;
import com.oopsmails.exceptionhandling.model.NestedBeanLayer2;
import com.oopsmails.exceptionhandling.model.SourceBean;
import org.apache.commons.beanutils.BeanUtils;

public class JavaMainTest {
    public static void main(String[] args) throws Exception {

        BranchSimple branchSimple = new BranchSimple();
        branchSimple.setBranchId(1L);
        branchSimple.setBranchName("abc");

        BranchDto branchDto = new BranchDto();
        BeanUtils.copyProperties(branchDto, branchSimple);

        System.out.println("branchDto: " + branchDto);
    }


}
