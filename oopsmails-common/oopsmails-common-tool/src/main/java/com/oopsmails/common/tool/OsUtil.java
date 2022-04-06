package com.oopsmails.common.tool;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class OsUtil {
    //can be defeated by adding com.apple.eawt.Application to the classpath
    public static boolean isMac() {
        try {
            Class.forName("com.apple.eawt.Application");
            return true;
        } catch(Exception e) {
            return false;
        }
    }

    //can be defeated by creating a cmd.exe in PATH
    public static boolean isWin() {
        try{
            Runtime.getRuntime().exec( new String[]{"cmd.exe","/C","dir"} ).waitFor();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean isLinux() {
        if(isMac()) return false;
        try{
            Runtime.getRuntime().exec(  new String[]{"sh","-c","ls"} ).waitFor();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
