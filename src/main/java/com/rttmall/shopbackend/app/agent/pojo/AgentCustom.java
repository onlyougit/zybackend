package com.rttmall.shopbackend.app.agent.pojo;

import com.rttmall.shopbackend.enums.AgentStatus;

/**
 * Created by wangweibin on 2017/12/14.
 */
public class AgentCustom extends Agent {

    private AgentStatus agentStatusEnum;

    private Business business;

    private String userName;

    private String resetPw;

    private String agentExtensionLink;

    public void setAgentExtensionLink(String agentExtensionLink) {
        this.agentExtensionLink = agentExtensionLink;
    }

    public String getAgentExtensionLink() {

        return agentExtensionLink;
    }

    public void setResetPw(String resetPw) {
        this.resetPw = resetPw;
    }

    public String getResetPw() {

        return resetPw;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserName() {

        return userName;
    }

    public void setBusiness(Business business) {
        this.business = business;
    }

    public Business getBusiness() {

        return business;
    }

    public void setAgentStatusEnum(AgentStatus agentStatusEnum) {
        this.agentStatusEnum = agentStatusEnum;
    }

    public AgentStatus getAgentStatusEnum() {

        return agentStatusEnum;
    }
}
