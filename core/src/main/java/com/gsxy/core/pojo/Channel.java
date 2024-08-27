package com.gsxy.core.pojo;

import lombok.*;

import java.io.Serializable;
import java.util.Date;

/**
 * 通道表 （该表用以区分各个学期以及指定周期的通知内容以及活动内容）
(Channel)实体类
 *
 * @author makejava
 * @since 2024-08-27 14:40:26
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class Channel implements Serializable {
    private static final long serialVersionUID = 594003275959615112L;

    private Long id;
/**
     * 通道名称
     */
    private String name;
/**
     * 社团id
     */
    private Long communityId;
/**
     * 创建人
     */
    private Long createdBy;
/**
     * 创建时间
     */
    private Date createdTime;
/**
     * 修改人
     */
    private Long updatedBy;
/**
     * 修改时间
     */
    private Date updatedTime;
/**
     * 逻辑删除（0：存在，1删除）
     */
    private Integer delFlag;

}

