package com.oopsmails.common.filter.util;

import java.util.Set;

public class FilterUtil {

    public static boolean isExemptionUrl(final String path, Set<String> loggingExemptionUrls) {
        for (String url : loggingExemptionUrls) {
            if (path.indexOf(url) >= 0) {
                return true;
            }
        }

//        if (path != null && loggingExemptionUrls != null) {
//            return loggingExemptionUrls.stream().anyMatch(ele -> ele != null && path.trim().indexOf(ele.trim()) >= 0);
//        }
        return false;
    }
}
