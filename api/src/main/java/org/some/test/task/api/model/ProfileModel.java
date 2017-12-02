package org.some.test.task.api.model;
import java.io.Serializable;

/**
 * Created by thefp on 02.12.2017.
 */
public class ProfileModel implements Serializable {

    private String phone;

    private String email;

    private String fio;

    private String hrefToImg;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFio() {
        return fio;
    }

    public void setFio(String fio) {
        this.fio = fio;
    }

    public String getHrefToImg() {
        return hrefToImg;
    }

    public void setHrefToImg(String hrefToImg) {
        this.hrefToImg = hrefToImg;
    }

}
