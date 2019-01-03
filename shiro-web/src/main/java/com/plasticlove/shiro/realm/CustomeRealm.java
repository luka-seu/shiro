package com.plasticlove.shiro.realm;

import com.plasticlove.dao.PermissionMapper;
import com.plasticlove.dao.RoleMapper;
import com.plasticlove.dao.UserMapper;
import com.plasticlove.pojo.User;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;

import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.apache.shiro.util.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

public class CustomeRealm extends AuthorizingRealm {



    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private PermissionMapper permissionMapper;



    // Map<String,String> userMap = new HashMap<String, String>();
    // {
    //     userMap.put("luka","11cfde807e348fd131327f264ac7d3bd");
    //     super.setName("customerRealm");
    // }



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

        Set<String> roleSet = roleMapper.selectRoleNameByUsername(username);

        if(CollectionUtils.isEmpty(roleSet)){
            return  null;
        }
        return roleSet;

    }

    private Set<String> getPermissionsByUsername(String username) {
        Set<String> permissionsSet = permissionMapper.selectPermissionsByUsername(username);
        if(CollectionUtils.isEmpty(permissionsSet)){
            return null;
        }
        return permissionsSet;

    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        String username = (String) token.getPrincipal();

        String password = getPasswordByUsername(username);
        if (password==null){
            return null;
        }
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(username,password,"customerRealm");
        authenticationInfo.setCredentialsSalt(ByteSource.Util.bytes(username));
        return authenticationInfo;
    }

    private String getPasswordByUsername(String username) {
        List<User> users = userMapper.selectUserByUsername(username);
        if(users==null){
            return  null;
        }
        return  users.get(0).getPassword();

    }

    // public static void main(String[] args) {
    //     Md5Hash md5Hash = new Md5Hash("123456","luka");
    //     System.out.println(md5Hash.toString());
    // }





}
