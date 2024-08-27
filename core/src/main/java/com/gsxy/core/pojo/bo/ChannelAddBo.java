package com.gsxy.core.pojo.bo;

import lombok.*;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
@ToString
public class ChannelAddBo implements Serializable {

    private Long id;
    /**
     * 通道名称
     */
    private String name;
    /**
     * 社团id
     */
    private Long communityId;

}
