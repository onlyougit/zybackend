package com.rttmall.shopbackend.app.other.mapper;

import com.rttmall.shopbackend.app.other.pojo.Notice;
import com.rttmall.shopbackend.app.other.pojo.NoticeCustom;

import java.util.List;

public interface NoticeCustomMapper {
    List<NoticeCustom> queryNotice(NoticeCustom noticeCustom);

    void batchDeleteNotice(List idList);
}