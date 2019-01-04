package com.plasticlove.session;

import com.plasticlove.utils.JedisUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;

import org.apache.shiro.session.mgt.eis.AbstractSessionDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.util.SerializationUtils;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class RedisSessionDao extends AbstractSessionDAO {



    @Autowired
    private JedisUtils jedisUtils;



    private final String SESSION_KEY_PREFIX = "plasticlove_session:";



    private byte[] getKey(String key) {
        if(key==null||key.length()==0){
            return null;
        }
        return (SESSION_KEY_PREFIX+key).getBytes();
    }

    private void saveSession(Session session){
        if(session!=null&&session.getId()!=null){

            byte[] key = getKey(session.getId().toString());
            byte[] value = SerializationUtils.serialize(session);
            jedisUtils.set(key,value);
            jedisUtils.expire(key,600);
        }
    }


    @Override
    protected Serializable doCreate(Session session) {
        System.out.println("create");
        Serializable sessionId = generateSessionId(session);
        assignSessionId(session,sessionId);
        saveSession(session);
        return sessionId;
    }



    @Override
    protected Session doReadSession(Serializable sessionId) {
        System.out.println("read");
        if(sessionId==null){
            return null;
        }
        byte[] key = getKey(sessionId.toString());
        byte[] value = jedisUtils.get(key);
        return (Session)SerializationUtils.deserialize(value);

    }

    @Override
    public void update(Session session) throws UnknownSessionException {
        saveSession(session);
    }

    @Override
    public void delete(Session session) {
        if(session==null){
            return;
        }
        byte[] key = getKey(session.getId().toString());
        jedisUtils.del(key);
    }

    @Override
    public Collection<Session> getActiveSessions() {
        Set<byte[]> keys = jedisUtils.getKeys(SESSION_KEY_PREFIX);
        Set<Session> sessionSet = new HashSet<>();

        if(CollectionUtils.isEmpty(keys)){
            return sessionSet;
        }

        for(byte[] key:keys){
            Session session = (Session) SerializationUtils.deserialize(key);
            sessionSet.add(session);
        }
        return sessionSet;
    }
}
