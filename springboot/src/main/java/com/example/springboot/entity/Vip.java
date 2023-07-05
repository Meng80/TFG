package com.example.springboot.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * <p>
 * 
 * </p>
 *
 * @author ada
 * @since 2023-06-29
 */
@Getter
@Setter
@TableName("sys_vip")
@ApiModel(value = "Vip对象", description = "")
@ToString
public class Vip implements Serializable {

    private static final long serialVersionUID = 1L;

      @ApiModelProperty("id")
      @TableId(value = "id", type = IdType.AUTO)
      private Integer id;

      @ApiModelProperty("name")
      private String name;

      @ApiModelProperty("phone")
      private String phone;

      @ApiModelProperty("email")
      private String email;

      @ApiModelProperty("address")
      private String address;

      @ApiModelProperty("create_time")
      private Date createTime;


}
