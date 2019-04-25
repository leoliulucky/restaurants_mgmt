package com.benxiaopao.biz.category.vo;

import lombok.Data;

import java.util.Date;

/**
 * 品类VO类
 *
 * Created by liupoyang
 * 2019-04-21
 */
@Data
public class ProductCategoryVO {
    private int categoryId;
    private String categoryName;
    private int categoryType;
    private int restaurantId;
    private Date createTime;
    private Date updateTime;
    private String restaurantName;
}
