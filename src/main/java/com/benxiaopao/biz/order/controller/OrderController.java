package com.benxiaopao.biz.order.controller;

import com.benxiaopao.biz.order.service.OrderService;
import com.benxiaopao.biz.order.vo.OrderVO;
import com.benxiaopao.biz.restaurant.service.RestaurantService;
import com.benxiaopao.biz.restaurant.vo.RestaurantVO;
import com.benxiaopao.common.aspect.ExcludeAuthorize;
import com.benxiaopao.common.supers.BaseController;
import com.benxiaopao.common.util.ViewResult;
import com.benxiaopao.sysadmin.user.constant.UserConstant;
import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * 订单模块请求控制层
 *
 * Created by liupoyang
 * 2019-04-20
 */
@Controller
@RequestMapping("/biz/order")
@Slf4j
public class OrderController extends BaseController {
    @Autowired
    private OrderService orderService;

    /**
     * 订单列表页
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/list", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView listOrderByWhere(OrderVO order, @RequestParam(defaultValue="1")String page) throws Exception{
        try{
            int pageNum = Integer.parseInt(page);
            List<OrderVO> orderVOList = orderService.listOrderByWherePage(order, pageNum, UserConstant.DEFAULT_PAGE_SIZE);
            log.info("# orderVOList.size()={}", orderVOList.size());

            return ViewResult.newInstance()
                    .code(1).msg("进订单列表页成功")
                    .put("orders", orderVOList)
                    .put("order", order)
                    .view("biz/order/listOrder");
        } catch (Exception e) {
            log.info("#订单列表页出错：" + e.getMessage());
            return ViewResult.newInstance().code(-1).msg(e.getMessage()).view("biz/order/listOrder");
        }
    }

    /**
     * 关闭订单请求<br />异步请求
     * @return
     * @throws Exception
     */
    @PostMapping("/close")
    @ResponseBody
    public String closeOrder(String orderId) throws Exception{
        try{
            Preconditions.checkArgument(!Strings.isNullOrEmpty(orderId), "订单ID非法");

            orderService.closeOrder(orderId);
            return ViewResult.newInstance().code(1).msg("关闭订单成功").json();
        } catch (Exception e) {
            log.error("#关闭订单出错：", e);
            return ViewResult.newInstance().code(-1).msg(e.getMessage()).json();
        }
    }

    /**
     * 通知订单请求<br />异步请求
     * @return
     * @throws Exception
     */
    @PostMapping("/notify")
    @ResponseBody
    @ExcludeAuthorize
    public String notifyOrder(String orderId) throws Exception{
        try{
            Preconditions.checkArgument(!Strings.isNullOrEmpty(orderId), "订单ID非法");

            orderService.notifyOrder(orderId);
            return ViewResult.newInstance().code(1).msg("通知订单成功").json();
        } catch (Exception e) {
            log.error("#通知订单出错：", e);
            return ViewResult.newInstance().code(-1).msg(e.getMessage()).json();
        }
    }
}
