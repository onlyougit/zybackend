package com.rttmall.shopbackend.app.other.service.impl;

import com.github.pagehelper.PageHelper;
import com.rttmall.shopbackend.app.other.mapper.NoticeCustomMapper;
import com.rttmall.shopbackend.app.other.mapper.NoticeMapper;
import com.rttmall.shopbackend.app.other.pojo.Notice;
import com.rttmall.shopbackend.app.other.pojo.NoticeCustom;
import com.rttmall.shopbackend.app.other.service.NoticeService;
import com.rttmall.shopbackend.pojo.PageBean;
import com.rttmall.shopbackend.pojo.Pagination;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NoticeServiceImpl implements NoticeService {
    @Autowired
    private NoticeCustomMapper noticeCustomMapper;
    @Autowired
    private NoticeMapper noticeMapper;

    @Override
    public Map queryNotice(NoticeCustom noticeCustom, Pagination grid) {
        Map map = new HashMap();
        PageHelper.startPage(grid.getPageIndex()+1, grid.getPageSize());
        List<NoticeCustom> noticeCustomList = noticeCustomMapper.queryNotice(noticeCustom);
        PageBean<NoticeCustom> pb = new PageBean(noticeCustomList);
        map.put("data", pb.getList());
        map.put("total", pb.getTotal());
        return map;
    }

    @Override
    public void insertNoticeSubmit(Notice notice) {
        noticeMapper.insertSelective(notice);
    }

    @Override
    public void batchDeleteNotice(List idList) {
        noticeCustomMapper.batchDeleteNotice(idList);
    }
}
