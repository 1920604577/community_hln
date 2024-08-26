package com.gsxy.core.pojo;

import lombok.*;

import java.io.Serializable;
import java.util.Date;

/**
 * 社团成员表(CommunityUser)实体类
 *
 * @author makejava
 * @since 2024-08-26 16:33:50
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class CommunityUser implements Serializable {
    private static final long serialVersionUID = -10493858528969932L;

    private Long id;
/**
     * 社团id（关联）
     */
    private Long communityId;
/**
     * 用户id
     */
    private Long userId;
/**
     * 创建人（社长id）
     */
    private Long createdBy;
/**
     * 创建时间（该操作的创建时间，不是社团的创建时间）
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
     * 逻辑删除（0：存在，1：删除）
     */
    private Integer delFlag;

}

