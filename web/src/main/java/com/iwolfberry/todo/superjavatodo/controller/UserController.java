package com.iwolfberry.todo.superjavatodo.controller;

import com.iwolfberry.todo.superjavatodo.bean.UserInfo;
import com.iwolfberry.todo.superjavatodo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.server.*;
import reactor.core.publisher.Flux;

import java.util.Collection;

@RestController
public class UserController {

    private final UserRepository userRepository;

    @Autowired
    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping("/user/save")
    public UserInfo save(@RequestParam String userName){
        UserInfo userInfo = new UserInfo();
        userInfo.setName(userName);
        if(userRepository.save(userInfo)){
            System.out.printf("保存的用户是：%s",userInfo);
        }
        return userInfo;
    }

    /**
     * 响应接口：ServerREsponse
     * 即可支持servlet规范，也可以支持自定义，比如Netty(web server)
     * 以本例：
     * 定义GET请求，并且返回所有的用户对象
     * Flux 是0 - N个对象集合
     * Mono 是0 - 1个对象集合
     * Reactive中的Flux或者Mono它是异步处理（非阻塞）
     * 集合对象基本上是同步处理（阻塞）
     * Flux或者Mono都是Publisher
     * @param userRepository
     * @return
     */
    @Bean
    @Autowired
    public RouterFunction<ServerResponse> personFindAll(UserRepository userRepository){

        return RouterFunctions.route(
                RequestPredicates.GET("/person/findall"),
                serverRequest -> {
                    Collection<UserInfo> userInfoCollection = userRepository.findAll();
                    Flux<UserInfo> flux=Flux.fromIterable(userInfoCollection);
                    return  ServerResponse.ok().body(flux,UserInfo.class);
                }
        );

    }
}
