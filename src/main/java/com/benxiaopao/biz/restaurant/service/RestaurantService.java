package com.benxiaopao.biz.restaurant.service;

import com.benxiaopao.biz.restaurant.vo.RestaurantVO;
import com.benxiaopao.common.supers.BaseService;
import com.benxiaopao.common.util.Pagination;
import com.benxiaopao.provider.dao.model.Role;
import com.benxiaopao.provider.dao.model.SysUser;
import com.benxiaopao.sysadmin.user.constant.UserConstant;
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
import org.springframework.util.CollectionUtils;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;

/**
 * 餐馆业务逻辑服务处理类
 *
 * Created by liupoyang
 * 2019-04-20
 */
@Service
@Slf4j
public class RestaurantService extends BaseService {
    @Autowired
    private ThriftClient thriftClient;

    /**
     * 根据条件获取餐馆列表，带分页
     * @param restaurant 条件参数餐馆对象
     * @param pageNum 页码
     * @param pageSize 页面大小
     * @return List<UserVO> 成员列表
     */
    public List<RestaurantVO> listRestaurantByWherePage(RestaurantVO restaurant, int pageNum, int pageSize) throws Exception {
        if(restaurant == null){
            restaurant = new RestaurantVO();
        }

        Pagination pagination = Pagination.currentPagination(pageNum, pageSize);

        TPListRestaurant tpListRestaurant = new TPListRestaurant();
        TPPagination tpPagination = new TPPagination();
        tpPagination.setPageSize(pageSize);
        tpPagination.setPageNo(pageNum);
        tpListRestaurant.setPagination(tpPagination);

        TPRestaurant tpRestaurant = new TPRestaurant();
        BeanUtils.copyProperties(restaurant, tpRestaurant, "createTime", "updateTime");
        tpListRestaurant.setRestaurant(tpRestaurant);

        List<RestaurantVO> result = Lists.newArrayList();
        try {
            thriftClient.open();
            TRListRestaurant trListRestaurant = thriftClient.getThriftService().listRestaurantByPage(tpListRestaurant);
            if(trListRestaurant == null || !trListRestaurant.response.isSuccess()){
                return result;
            }

            pagination.setTotalCount(trListRestaurant.getPagination().getTotalCount());

            result =  FluentIterable.from(trListRestaurant.getRestaurantList()).transform(new Function<TRRestaurant, RestaurantVO>() {
                @Override
                public RestaurantVO apply(TRRestaurant trRestaurant) {
                    RestaurantVO restaurantVO = new RestaurantVO();
                    BeanUtils.copyProperties(trRestaurant, restaurantVO, "createTime", "updateTime");
                    restaurantVO.setCreateTime(new Date(trRestaurant.getCreateTime()));
                    restaurantVO.setUpdateTime(new Date(trRestaurant.getUpdateTime()));
                    return restaurantVO;
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
     * 根据条件获取餐馆列表
     * @param restaurant 条件参数餐馆对象
     * @return List<UserVO> 成员列表
     */
    public List<RestaurantVO> listRestaurantByWhere(RestaurantVO restaurant) throws Exception {
        if(restaurant == null){
            restaurant = new RestaurantVO();
        }

        TPListRestaurant tpListRestaurant = new TPListRestaurant();
        TPRestaurant tpRestaurant = new TPRestaurant();
        BeanUtils.copyProperties(restaurant, tpRestaurant, "createTime", "updateTime");
        tpListRestaurant.setRestaurant(tpRestaurant);

        List<RestaurantVO> result = Lists.newArrayList();
        try {
            thriftClient.open();
            TRListRestaurant trListRestaurant = thriftClient.getThriftService().listRestaurant(tpListRestaurant);
            if(trListRestaurant == null || !trListRestaurant.response.isSuccess()){
                return result;
            }

            result =  FluentIterable.from(trListRestaurant.getRestaurantList()).transform(new Function<TRRestaurant, RestaurantVO>() {
                @Override
                public RestaurantVO apply(TRRestaurant trRestaurant) {
                    RestaurantVO restaurantVO = new RestaurantVO();
                    BeanUtils.copyProperties(trRestaurant, restaurantVO, "createTime", "updateTime");
                    restaurantVO.setCreateTime(new Date(trRestaurant.getCreateTime()));
                    restaurantVO.setUpdateTime(new Date(trRestaurant.getUpdateTime()));
                    return restaurantVO;
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
     * 新增餐馆
     * @param restaurant 要插入的餐馆数据对象
     */
    public void insertRestaurant(RestaurantVO restaurant) throws Exception {
        int records = 0;

        TPRestaurant tpRestaurant = new TPRestaurant();
        BeanUtils.copyProperties(restaurant, tpRestaurant, "createTime", "updateTime");

        try {
            thriftClient.open();
            records = thriftClient.getThriftService().insertRestaurant(tpRestaurant);
        } catch (Exception e) {
            log.error("# RPC调用失败", e);
        } finally {
            thriftClient.close();
        }
        Preconditions.checkArgument(records > 0, "新增餐馆失败");
    }

    /**
     * 修改餐馆
     * @param restaurant 要更新的餐馆数据对象
     */
    public void updateRestaurant(RestaurantVO restaurant) throws Exception {
        int records = 0;

        TPRestaurant tpRestaurant = new TPRestaurant();
        BeanUtils.copyProperties(restaurant, tpRestaurant, "createTime", "updateTime");

        try {
            thriftClient.open();
            records = thriftClient.getThriftService().updateRestaurant(tpRestaurant);
        } catch (Exception e) {
            log.error("# RPC调用失败", e);
        } finally {
            thriftClient.close();
        }
        Preconditions.checkArgument(records > 0, "修改餐馆失败");
    }

    /**
     * 删除餐馆
     * @param restaurantId 餐馆id
     */
    public void deleteRestaurant(int restaurantId) throws Exception {
        int records = 0;
        try {
            thriftClient.open();
            records = thriftClient.getThriftService().deleteRestaurant(restaurantId);
        } catch (Exception e) {
            log.error("# RPC调用失败", e);
        } finally {
            thriftClient.close();
        }
        Preconditions.checkArgument(records > 0, "删除餐馆失败");
    }

    /**
     * 根据餐馆id获取餐馆对象
     * @param restaurantId 餐馆id
     */
    public RestaurantVO getRestaurantById(int restaurantId) throws Exception {
        RestaurantVO restaurant = null;
        try {
            thriftClient.open();
            TRRestaurant trRestaurant = thriftClient.getThriftService().getRestaurantById(restaurantId);
            restaurant = new RestaurantVO();
            BeanUtils.copyProperties(trRestaurant, restaurant, "createTime", "updateTime");
            restaurant.setCreateTime(new Date(trRestaurant.getCreateTime()));
            restaurant.setUpdateTime(new Date(trRestaurant.getUpdateTime()));
        } catch (Exception e) {
            log.error("# RPC调用失败", e);
        } finally {
            thriftClient.close();
        }
        return restaurant;
    }
}
