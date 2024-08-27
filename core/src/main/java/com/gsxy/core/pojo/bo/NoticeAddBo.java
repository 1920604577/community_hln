package com.gsxy.core.pojo.bo;

import com.gsxy.core.pojo.enums.NoticeTypeEnum;
import lombok.*;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class NoticeAddBo implements Serializable {

    /**
     * 标题
     */
    private String title;
    /**
     * 内容
     */
    private String message;
    /**
     * 社团id
     */
    private Long communityId;
    /**
     * 枚举类型（预留）
     */
    private NoticeTypeEnum type;
    /**
     * 接收人id
     */
    private Long receiveUserId;

}
