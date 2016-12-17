package br.com.hsacademy.app.model;

public class User {

    /**
     * id : 2
     * first_name : Marcus Eduardo
     * last_name : Motta Duarte
     * email : marcusedu@hotmail.com
     * create_date : 2016-12-17 00:44:39
     */

    private String id;
    private String first_name;
    private String last_name;
    private String email;
    private String create_date;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCreate_date() {
        return create_date;
    }

    public void setCreate_date(String create_date) {
        this.create_date = create_date;
    }
}
