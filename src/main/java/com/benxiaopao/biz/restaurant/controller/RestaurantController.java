package com.benxiaopao.biz.restaurant.controller;

import com.benxiaopao.biz.restaurant.service.RestaurantService;
import com.benxiaopao.biz.restaurant.vo.RestaurantVO;
import com.benxiaopao.common.supers.BaseController;
import com.benxiaopao.common.util.ViewResult;
import com.benxiaopao.sysadmin.user.constant.UserConstant;
import com.google.common.base.Preconditions;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import java.util.List;

/**
 * 餐馆模块请求控制层
 *
 * Created by liupoyang
 * 2019-04-20
 */
@Controller
@RequestMapping("/biz/restaurant")
@Slf4j
public class RestaurantController extends BaseController {
    @Autowired
    private RestaurantService restaurantService;

    /**
     * 餐馆列表页
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/list", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView listRestaurantByWhere(RestaurantVO restaurant, @RequestParam(defaultValue="1")String page) throws Exception{
        try{
            int pageNum = Integer.parseInt(page);
            List<RestaurantVO> restaurantVOList = restaurantService.listRestaurantByWherePage(restaurant, pageNum, UserConstant.DEFAULT_PAGE_SIZE);
            log.info("# restaurantVOList.size()={}", restaurantVOList.size());

            return ViewResult.newInstance()
                    .code(1).msg("进餐馆列表页成功")
                    .put("restaurants", restaurantVOList)
                    .put("restaurant", restaurant)
                    .view("biz/restaurant/listRestaurant");
        } catch (Exception e) {
            log.info("#餐馆列表页出错：" + e.getMessage());
            return ViewResult.newInstance().code(-1).msg(e.getMessage()).view("biz/restaurant/listRestaurant");
        }
    }

    /**
     * 餐馆新增页
     * @return
     * @throws Exception
     */
    @GetMapping("/insert")
    public String preInsertRestaurant() throws Exception{
        return "biz/restaurant/addRestaurant";
    }

    /**
     * 新增餐馆请求<br />异步请求
     * @return
     * @throws Exception
     */
    @PostMapping("/doInsert")
    @ResponseBody
    public String insertRestaurant(RestaurantVO restaurant) throws Exception {
        try{
            Preconditions.checkNotNull(restaurant, "餐馆数据不能为空");
//            Preconditions.checkArgument(!Strings.isNullOrEmpty(user.getEmail()), "成员邮箱不能为空");
//            Preconditions.checkArgument(!Strings.isNullOrEmpty(user.getPassword()), "密码不能为空");
//            Preconditions.checkArgument(user.getRoleId() != null && user.getRoleId() > 0, "成员角色数据非法");

            restaurantService.insertRestaurant(restaurant);
            return ViewResult.newInstance().code(1).msg("新增餐馆成功").json();
        } catch (Exception e) {
            log.info("#新增餐馆出错：" + e.getMessage());
            return ViewResult.newInstance().code(-1).msg(e.getMessage()).json();
        }
    }

    /**
     * 餐馆修改页
     * @return
     * @throws Exception
     */
    @GetMapping("/update")
    public ModelAndView preUpdateRestaurant(int restaurantId) throws Exception {
        try{
            Preconditions.checkArgument(restaurantId > 0, "餐馆ID非法");

            RestaurantVO restaurant = restaurantService.getRestaurantById(restaurantId);
            return ViewResult.newInstance()
                    .code(1).msg("进餐馆修改页成功")
                    .put("restaurant", restaurant)
                    .view("biz/restaurant/editRestaurant");
        } catch (Exception e) {
            log.info("#进餐馆修改页出错：" + e.getMessage());
            return ViewResult.newInstance().code(-1).msg(e.getMessage()).view("biz/restaurant/editRestaurant");
        }
    }

    /**
     * 修改餐馆请求<br />异步请求
     * @return
     * @throws Exception
     */
    @PostMapping("/doUpdate")
    @ResponseBody
    public String updateRestaurant(RestaurantVO restaurant) throws Exception{
        try{
            Preconditions.checkNotNull(restaurant, "餐馆数据不能为空");
            Preconditions.checkArgument(restaurant.getRestaurantId() > 0, "餐馆ID非法");
//            Preconditions.checkArgument(!Strings.isNullOrEmpty(user.getEmail()), "成员邮箱不能为空");
//            Preconditions.checkArgument(user.getRoleId() != null && user.getRoleId() > 0, "成员角色数据非法");

            restaurantService.updateRestaurant(restaurant);
            return ViewResult.newInstance().code(1).msg("修改餐馆信息成功!").json();
        } catch (Exception e) {
            log.info("#修改餐馆出错：" + e.getMessage());
            return ViewResult.newInstance().code(-1).msg(e.getMessage()).json();
        }
    }

    /**
     * 删除餐馆请求<br />异步请求
     * @return
     * @throws Exception
     */
    @PostMapping("/delete")
    @ResponseBody
    public String deleteRestaurant(int restaurantId) throws Exception{
        try{
            Preconditions.checkArgument(restaurantId > 0, "餐馆ID非法");

            restaurantService.deleteRestaurant(restaurantId);
            return ViewResult.newInstance().code(1).msg("删除餐馆成功").json();
        } catch (Exception e) {
            log.info("#删除餐馆出错：" + e.getMessage());
            return ViewResult.newInstance().code(-1).msg(e.getMessage()).json();
        }
    }
}
