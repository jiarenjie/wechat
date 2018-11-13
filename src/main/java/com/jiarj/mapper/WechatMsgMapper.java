package com.jiarj.mapper;

import com.jiarj.entity.WechatMsg;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface WechatMsgMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(WechatMsg record);

    WechatMsg selectByPrimaryKey(Integer id);

    List<WechatMsg> selectAll();

    int updateByPrimaryKey(WechatMsg record);
}