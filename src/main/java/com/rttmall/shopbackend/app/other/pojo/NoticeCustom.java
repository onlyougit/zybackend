package com.rttmall.shopbackend.app.other.pojo;

import com.rttmall.shopbackend.enums.NoticeStatus;
import com.rttmall.shopbackend.sys.pojo.User;

public class NoticeCustom extends Notice {

    private NoticeStatus noticeStatusEnum;

    private User user;

    public NoticeStatus getNoticeStatusEnum() {
        return noticeStatusEnum;
    }

    public void setNoticeStatusEnum(NoticeStatus noticeStatusEnum) {
        this.noticeStatusEnum = noticeStatusEnum;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
