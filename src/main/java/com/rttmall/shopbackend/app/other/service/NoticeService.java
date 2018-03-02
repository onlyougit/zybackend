package com.rttmall.shopbackend.app.other.service;

import com.rttmall.shopbackend.app.other.pojo.Notice;
import com.rttmall.shopbackend.app.other.pojo.NoticeCustom;
import com.rttmall.shopbackend.pojo.Pagination;

import java.util.List;
import java.util.Map;

public interface NoticeService {
    Map queryNotice(NoticeCustom noticeCustom, Pagination grid);

    void insertNoticeSubmit(Notice notice);

    void batchDeleteNotice(List idList);
}
