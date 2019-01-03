package com.plasticlove.shiro.realm;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;

import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class CustomeRealm extends AuthorizingRealm {
    Map<String,String> userMap = new HashMap<String, String>();
    {
        userMap.put("luka","11cfde807e348fd131327f264ac7d3bd");
        super.setName("customerRealm");
    }



    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        String username = (String) principals.getPrimaryPrincipal();

        Set<String> roles = getRolesByUsername(username);


        Set<String> permissions = getPermissionsByUsername(username);
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        authorizationInfo.setRoles(roles);
        authorizationInfo.setStringPermissions(permissions);

        return authorizationInfo;
    }

    private Set<String> getRolesByUsername(String username) {

        Set<String> roleSet = new HashSet<String>();
        roleSet.add("admin");
        roleSet.add("user");
        return roleSet;
    }

    private Set<String> getPermissionsByUsername(String username) {
        Set<String> permissionsSet = new HashSet<String>();
        permissionsSet.add("user:delete");
        permissionsSet.add("user:update");
        return permissionsSet;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        String username = (String) token.getPrincipal();

        String password = getPasswordByUsername(username);
        if (password==null){
            return null;
        }
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo("luka",password,"customerRealm");
        authenticationInfo.setCredentialsSalt(ByteSource.Util.bytes("luka"));
        return authenticationInfo;
    }

    private String getPasswordByUsername(String username) {
        return userMap.get(username);
    }

    // public static void main(String[] args) {
    //     Md5Hash md5Hash = new Md5Hash("123456","luka");
    //     System.out.println(md5Hash.toString());
    // }





}
