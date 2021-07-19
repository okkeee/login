package com.example.demo;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserRepository {

    //select文。userテーブルから、usernameが一致しているものを検索
    @Select("select * from user_sample where username = #{username}")
    public User findByUsername(String username);//識別する
}