package com.gsxy.core.pojo.enums;

public enum ApplyEnum {

    // ---------------- 审批的状态
    AWAIT, //待审批
    PASS, //通过

    // ------------------审批的类型(apply_flow)
    COMMUNITY_APPLY, //创建社团申请
    JOIN_COMMUNITY, //加入社团
    DESTROY_COMMUNITY, //注销社团
    QUIT_COMMUNITY, //退出社团
    CHANGE_COMMUNITY, //修改社团信息
    ;

}
