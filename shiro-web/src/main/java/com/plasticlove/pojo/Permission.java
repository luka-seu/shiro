package com.plasticlove.pojo;

public class Permission {
    private Integer id;

    private String username;

    private String permission;

    public Permission(Integer id, String username, String permission) {
        this.id = id;
        this.username = username;
        this.permission = permission;
    }

    public Permission() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRoleName() {
        return username;
    }

    public void setRoleName(String username) {
        this.username = username == null ? null : username.trim();
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission == null ? null : permission.trim();
    }
}