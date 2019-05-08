package com.benxiaopao.biz.order.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 订单VO类
 *
 * Created by liupoyang
 * 2019-04-21
 */
@Data
public class OrderVO {
    private String orderId;
    private int payType;
    private double totalAmout;
    private double realTotalAmout;
    private double shipmentExpense;
    private int orderStatus;
    private int orderType;
    private int orderFrom;
    private String pOrderId;
    private int buyerId;
    private String consignee;
    private int province;
    private int city;
    private int district;
    private String address;
    private String tel;
    private int restaurantId;
    private Date createTime;
    private Date updateTime;
}
