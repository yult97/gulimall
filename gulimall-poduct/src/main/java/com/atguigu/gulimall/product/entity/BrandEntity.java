package com.atguigu.gulimall.product.entity;

import com.atguigu.common.valid.AddGroup;
import com.atguigu.common.valid.ListenerVal;
import com.atguigu.common.valid.UpdateGroup;
import com.atguigu.common.valid.UpdateStatusGroup;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;

import lombok.Data;
import org.hibernate.validator.constraints.URL;
import org.springframework.lang.NonNull;

import javax.validation.constraints.*;

/**
 * 品牌
 *
 * @author yubo
 * @email 1904282367@gmail.com
 * @date 2021-12-18 23:49:40
 */
@Data
@TableName("pms_brand")
public class BrandEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 品牌id
     */
    @TableId
    @Null(groups = AddGroup.class, message = "新增品牌编号不可以指定id")
    @NotNull(groups = UpdateGroup.class, message = "修改品牌编号可以指定id")
    private Long brandId;
    /**
     * 品牌名
     */
    @NotEmpty(message = "品牌名称不能为空", groups = {AddGroup.class, UpdateGroup.class})
    private String name;
    /**
     * 品牌logo地址(路径地址规范,新增和修改都需进行校验)
     */
    @URL(message = "logo必须是一个合法地址", groups = {AddGroup.class, UpdateGroup.class})
    @NotEmpty(groups = AddGroup.class, message = "新增品牌logo不可以为空")
    private String logo;
    /**
     * 介绍
     */
    private String descript;
    /**
     * 显示状态[0-不显示；1-显示]
     */
    @ListenerVal(val = {0, 1}, groups = {AddGroup.class, UpdateStatusGroup.class})
    private Integer showStatus;
    /**
     * 检索首字母
     */
    @Pattern(regexp = "^[a-zA-Z]$", message = "检索首字母必须是一个字母")
    @NotEmpty
    private String firstLetter;
    /**
     * 排序
     */
    @NotNull(groups = AddGroup.class, message = "新增品牌排序不可以为空")
    @Min(value = 0, message = "必须大于等于0", groups = {AddGroup.class, UpdateGroup.class})
    private Integer sort;

}
