package com.benxiaopao.biz.order.service;

import com.benxiaopao.biz.order.vo.OrderVO;
import com.benxiaopao.common.component.WebSocket;
import com.benxiaopao.common.supers.BaseService;
import com.benxiaopao.common.util.Pagination;
import com.benxiaopao.sysadmin.user.vo.SysUserVo;
import com.benxiaopao.thrift.ThriftClient;
import com.benxiaopao.thrift.model.*;
import com.google.common.base.Function;
import com.google.common.base.Preconditions;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 订单业务逻辑服务处理类
 *
 * Created by liupoyang
 * 2019-04-20
 */
@Service
@Slf4j
public class OrderService extends BaseService {
    @Autowired
    private ThriftClient thriftClient;
    @Autowired
    private WebSocket webSocket;

    /**
     * 根据条件获取订单列表，带分页
     * @param order 条件参数订单对象
     * @param pageNum 页码
     * @param pageSize 页面大小
     * @return List<OrderVO> 订单列表
     */
    public List<OrderVO> listOrderByWherePage(OrderVO order, int pageNum, int pageSize) throws Exception {
        if(order == null){
            order = new OrderVO();
        }
        SysUserVo user = (SysUserVo) currentUser();
        order.setRestaurantId(user.getOrgId());

        Pagination pagination = Pagination.currentPagination(pageNum, pageSize);

        TPListOrder tpListOrder = new TPListOrder();
        TPPagination tpPagination = new TPPagination();
        tpPagination.setPageSize(pageSize);
        tpPagination.setPageNo(pageNum);
        tpListOrder.setPagination(tpPagination);

        TPOrder tpOrder = new TPOrder();
        BeanUtils.copyProperties(order, tpOrder, "createTime", "updateTime");
        tpListOrder.setOrder(tpOrder);

        List<OrderVO> result = Lists.newArrayList();
        try {
            thriftClient.open();
            TRListOrder trListOrder = thriftClient.getThriftService().listOrderByPage(tpListOrder);
            if(trListOrder == null || !trListOrder.response.isSuccess()){
                return result;
            }

            pagination.setTotalCount(trListOrder.getPagination().getTotalCount());

            result =  FluentIterable.from(trListOrder.getOrderList()).transform(new Function<TROrder, OrderVO>() {
                @Override
                public OrderVO apply(TROrder trOrder) {
                    OrderVO orderVO = new OrderVO();
                    BeanUtils.copyProperties(trOrder, orderVO, "createTime", "updateTime");
                    orderVO.setCreateTime(new Date(trOrder.getCreateTime()));
                    orderVO.setUpdateTime(new Date(trOrder.getUpdateTime()));
                    return orderVO;
                }
            }).toList();
        } catch (Exception e) {
            log.error("# RPC调用失败", e);
        } finally {
            thriftClient.close();
        }

        return result;
    }

    /**
     * 关闭订单
     * @param orderId 订单id
     */
    public void closeOrder(String orderId) throws Exception {
        int records = 0;
        try {
            thriftClient.open();
            records = thriftClient.getThriftService().closeOrderById(orderId);
        } catch (Exception e) {
            log.error("# RPC调用失败", e);
        } finally {
            thriftClient.close();
        }
        Preconditions.checkArgument(records > 0, "关闭订单失败");
    }

    /**
     * 通知订单
     * @param orderId 订单id
     */
    public void notifyOrder(String orderId) throws Exception {
        //发送websocket消息
        webSocket.sendMessage(orderId);
        log.info("# notifyOrder success! orderId={}", orderId);
    }
}
