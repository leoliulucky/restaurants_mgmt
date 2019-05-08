package com.benxiaopao.biz.category.controller;

import com.benxiaopao.biz.category.service.ProductCategoryService;
import com.benxiaopao.biz.category.vo.ProductCategoryVO;
import com.benxiaopao.biz.restaurant.service.RestaurantService;
import com.benxiaopao.biz.restaurant.vo.RestaurantVO;
import com.benxiaopao.common.supers.BaseController;
import com.benxiaopao.common.util.ViewResult;
import com.benxiaopao.sysadmin.user.constant.UserConstant;
import com.benxiaopao.sysadmin.user.vo.SysUserVo;
import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import java.util.List;

/**
 * 品类模块请求控制层
 *
 * Created by liupoyang
 * 2019-04-20
 */
@Controller
@RequestMapping("/biz/category")
@Slf4j
public class ProductCategoryController extends BaseController {
    @Autowired
    private ProductCategoryService productCategoryService;
    @Autowired
    private RestaurantService restaurantService;

    /**
     * 品类列表页
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/list", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView listProductCategoryByWhere(ProductCategoryVO productCategory, @RequestParam(defaultValue="1")String page) throws Exception{
        try{
            int pageNum = Integer.parseInt(page);
            List<ProductCategoryVO> productCategoryVOList = productCategoryService.listProductCategoryByWherePage(productCategory, pageNum, UserConstant.DEFAULT_PAGE_SIZE);
            log.info("# productCategoryVOList.size()={}", productCategoryVOList.size());

            return ViewResult.newInstance()
                    .code(1).msg("进品类列表页成功")
                    .put("productCategorys", productCategoryVOList)
                    .put("productCategory", productCategory)
                    .view("biz/productCategory/listProductCategory");
        } catch (Exception e) {
            log.info("#品类列表页出错：" + e.getMessage());
            return ViewResult.newInstance().code(-1).msg(e.getMessage()).view("biz/productCategory/listProductCategory");
        }
    }

    /**
     * 品类新增页
     * @return
     * @throws Exception
     */
    @GetMapping("/insert")
    public ModelAndView preInsertProductCategory() throws Exception{
        SysUserVo user = (SysUserVo) currentUser();
        RestaurantVO restaurant = restaurantService.getRestaurantById(user.getOrgId());
        return ViewResult.newInstance()
                .code(1).msg("进品类新增页成功")
                .put("restaurant", restaurant)
                .view("biz/productCategory/addProductCategory");
    }

    /**
     * 新增品类请求<br />异步请求
     * @return
     * @throws Exception
     */
    @PostMapping("/doInsert")
    @ResponseBody
    public String insertProductCategory(ProductCategoryVO productCategory) throws Exception {
        try{
            Preconditions.checkNotNull(productCategory, "品类数据不能为空");
            Preconditions.checkArgument(!Strings.isNullOrEmpty(productCategory.getCategoryName()), "品类名称不能为空");
            Preconditions.checkArgument(productCategory.getCategoryType() > 0, "品类编号不能为空");
            Preconditions.checkArgument(productCategory.getRestaurantId() > 0, "所属餐馆非法");

            productCategoryService.insertProductCategory(productCategory);
            return ViewResult.newInstance().code(1).msg("新增品类成功").json();
        } catch (Exception e) {
            log.info("#新增品类出错：" + e.getMessage());
            return ViewResult.newInstance().code(-1).msg(e.getMessage()).json();
        }
    }

    /**
     * 品类修改页
     * @return
     * @throws Exception
     */
    @GetMapping("/update")
    public ModelAndView preUpdateProductCategory(@RequestParam(name="categoryId") int productCategoryId) throws Exception {
        try{
            Preconditions.checkArgument(productCategoryId > 0, "品类ID非法");

            ProductCategoryVO productCategory = productCategoryService.getProductCategoryById(productCategoryId);
            SysUserVo user = (SysUserVo) currentUser();
            RestaurantVO restaurant = restaurantService.getRestaurantById(user.getOrgId());
            return ViewResult.newInstance()
                    .code(1).msg("进品类修改页成功")
                    .put("productCategory", productCategory)
                    .put("restaurant", restaurant)
                    .view("biz/productCategory/editProductCategory");
        } catch (Exception e) {
            log.info("#进品类修改页出错：" + e.getMessage());
            return ViewResult.newInstance().code(-1).msg(e.getMessage()).view("biz/productCategory/editProductCategory");
        }
    }

    /**
     * 修改品类请求<br />异步请求
     * @return
     * @throws Exception
     */
    @PostMapping("/doUpdate")
    @ResponseBody
    public String updateProductCategory(ProductCategoryVO productCategory) throws Exception{
        try{
            Preconditions.checkNotNull(productCategory, "品类数据不能为空");
            Preconditions.checkArgument( productCategory.getCategoryId() > 0, "品类ID非法");
            Preconditions.checkArgument(!Strings.isNullOrEmpty(productCategory.getCategoryName()), "品类名称不能为空");
            Preconditions.checkArgument(productCategory.getCategoryType() > 0, "品类编号不能为空");
            Preconditions.checkArgument(productCategory.getRestaurantId() > 0, "所属餐馆非法");

            productCategoryService.updateProductCategory(productCategory);
            return ViewResult.newInstance().code(1).msg("修改品类信息成功!").json();
        } catch (Exception e) {
            log.info("#修改品类出错：" + e.getMessage());
            return ViewResult.newInstance().code(-1).msg(e.getMessage()).json();
        }
    }

    /**
     * 删除品类请求<br />异步请求
     * @return
     * @throws Exception
     */
    @PostMapping("/delete")
    @ResponseBody
    public String deleteProductCategory(@RequestParam(name="categoryId") int productCategoryId) throws Exception{
        try{
            Preconditions.checkArgument(productCategoryId > 0, "品类ID非法");

            productCategoryService.deleteProductCategory(productCategoryId);
            return ViewResult.newInstance().code(1).msg("删除品类成功").json();
        } catch (Exception e) {
            log.info("#删除品类出错：" + e.getMessage());
            return ViewResult.newInstance().code(-1).msg(e.getMessage()).json();
        }
    }
}
