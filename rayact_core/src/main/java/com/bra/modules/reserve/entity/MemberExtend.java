package com.bra.modules.reserve.entity;

/**
 * 用户扩展信息
 * Created by xiaobin on 16/2/18.
 */
public class MemberExtend {

    private String id;
    private String token;//token
    private String nickname;//昵称
    private String qq;//qq号码

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }
}
