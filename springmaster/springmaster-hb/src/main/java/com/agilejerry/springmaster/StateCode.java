package com.agilejerry.springmaster;

public class StateCode {
    public static final  int ORG_IS_NOT_SET = -1023;
    public static final int OK = 1;
    public static final int DUPLICATED_MEMBER = -2;
    public static final int HAVE_ADMINISTRATION_GROUP = -1;
    public static final int NULL = 0;
    public static final int NOT_ADMINISTRATION_GROUP = -101;
    public static final int ERROR = -999;
    public static final int FIELD_NEEDED = -1024;
    private StateCode(){
        //hide the public constructor
    }
}
