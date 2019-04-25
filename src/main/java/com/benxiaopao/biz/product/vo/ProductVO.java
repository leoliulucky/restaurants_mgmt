package com.benxiaopao.biz.product.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 菜品VO类
 *
 * Created by liupoyang
 * 2019-04-21
 */
@Data
public class ProductVO {
    private String productId;
    private String productName;
    private double price;
    private int stock;
    private String description;
    private String icon;
    private byte status;
    private int categoryType;
    private int restaurantId;
    private Date createTime;
    private Date updateTime;
}
