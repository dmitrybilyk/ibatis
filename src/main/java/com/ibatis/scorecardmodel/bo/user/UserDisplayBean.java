package com.ibatis.scorecardmodel.bo.user;

import java.io.Serializable;

/**
 * Created by IntelliJ IDEA.
 * User: Zdary
 * Date: 05/01/13
 * Time: 17:09
 * To change this template use File | Settings | File Templates.
 */
public class UserDisplayBean implements Serializable {
    private static final long serialVersionUID = 4212345656560863956L;

    private Integer userid;
    private String name;
    private String surname;

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserDisplayBean that = (UserDisplayBean) o;

        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (surname != null ? !surname.equals(that.surname) : that.surname != null) return false;
        if (!userid.equals(that.userid)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = userid.hashCode();
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (surname != null ? surname.hashCode() : 0);
        return result;
    }
}
