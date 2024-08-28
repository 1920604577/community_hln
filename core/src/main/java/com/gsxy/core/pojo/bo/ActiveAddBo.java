package com.gsxy.core.pojo.bo;

import com.gsxy.core.pojo.enums.ActiveTypeEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
@ToString
public class ActiveAddBo implements Serializable {

    @ApiModelProperty(value = "活动id（用于修改）", example = "活动id（用于修改）")
    private Long id;
    @ApiModelProperty(value = "标题", example = "标题")
    private String title;
    @ApiModelProperty(value = "活动内容", example = "活动内容")
    private String message;
    @ApiModelProperty(value = "通道id", example = "通道id")
    private Long channelId;
    @ApiModelProperty(value = "附件文件路径", example = "附件文件路径")
    private String filePath;
    @ApiModelProperty(value = "附件文件路径（原有的）", example = "附件文件路径（原有的）")
    private String filePathOld;
    @ApiModelProperty(value = "开始时间", example = "开始时间")
    private Date startTime;
    @ApiModelProperty(value = "活动时间", example = "活动时间")
    private Date endTime;
    @ApiModelProperty(value = "类型（预留字段）", example = "类型（预留字段）")
    private String type;

}
