package com.lanjy.blog.enums;

import lombok.Getter;

/**
 * @author：lanjy
 * @date：2020/3/29
 * @description：
 */
public enum UserTypeEnum {
    USER(0,"普通用户"),ADMIN(1,"普通管理员"),ROOT(2,"超级管理员");
    @Getter
    private Integer code;
    @Getter
    private String desc;

    UserTypeEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static UserTypeEnum forEach(Integer code){
        for (UserTypeEnum element : UserTypeEnum.values()) {
            if (code == element.getCode()){
                return element;
            }
        }
        return null;
    }
}
