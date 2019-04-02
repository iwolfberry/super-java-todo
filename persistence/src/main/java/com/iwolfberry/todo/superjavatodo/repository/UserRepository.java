package com.iwolfberry.todo.superjavatodo.repository;

import com.iwolfberry.todo.superjavatodo.bean.UserInfo;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * {@link com.iwolfberry.todo.superjavatodo.bean.UserInfo} {@link Repository}
 */
@Repository
public class UserRepository {

    public UserRepository() {
    }

    private ConcurrentMap<Integer, UserInfo> concurrentMap = new ConcurrentHashMap<>();

    private final static AtomicInteger idGenerator = new AtomicInteger();

    /**
     * 保存用户对象
     * @param userInfo {@link UserInfo} 对象
     * @return 如果保存成功，返回<code>true</code>，
     * 否则，返回<code>false</code>
     */
    public boolean save(UserInfo userInfo){
        //id从1开始
        Integer id = idGenerator.incrementAndGet();
        userInfo.setId(id);
        return concurrentMap.put(id, userInfo) == null;
    }

    public Collection<UserInfo> findAll(){
        return concurrentMap.values();
    }
}
