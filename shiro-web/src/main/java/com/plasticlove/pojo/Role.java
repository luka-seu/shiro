package com.plasticlove.pojo;

public class Role {
    private Integer id;

    private String username;

    private String roleName;

    public Role(Integer id, String username, String roleName) {
        this.id = id;
        this.username = username;
        this.roleName = roleName;
    }

    public Role() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName == null ? null : roleName.trim();
    }
}