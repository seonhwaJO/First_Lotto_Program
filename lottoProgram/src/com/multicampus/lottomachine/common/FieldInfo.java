package com.multicampus.lottomachine.common;

public enum FieldInfo {
	ID("아이디","id",true),
    NAME("이름", "name", null),
    PASSWORD("패스워드", "password", false),
    NICKNAME("닉네임", "nickname", true),
	BALANCE("보유금액","balance",false);
	
    private final String fieldName;
    private final String fieldValue;
    private final Boolean check;
    
    FieldInfo(String fieldName, String fieldValue, Boolean check) {
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
        this.check = check;
    }
    
    public String getFieldName() {
        return fieldName;
    }

    public String getFieldValue() {
        return fieldValue;
    }

    public Boolean isCheck() {
        return check;
    }
}
