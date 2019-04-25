package com.benxiaopao.biz.product.controller;

import com.benxiaopao.biz.product.service.ProductService;
import com.benxiaopao.biz.product.vo.ProductVO;
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
 * 菜品模块请求控制层
 *
 * Created by liupoyang
 * 2019-04-20
 */
@Controller
@RequestMapping("/biz/product")
@Slf4j
public class ProductController extends BaseController {
    @Autowired
    private ProductService productService;

    /**
     * 菜品列表页
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/list", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView listProductByWhere(ProductVO product, @RequestParam(defaultValue="1")String page) throws Exception{
        try{
            int pageNum = Integer.parseInt(page);
            List<ProductVO> productVOList = productService.listProductByWherePage(product, pageNum, UserConstant.DEFAULT_PAGE_SIZE);
            log.info("# productVOList.size()={}", productVOList.size());

            return ViewResult.newInstance()
                    .code(1).msg("进菜品列表页成功")
                    .put("products", productVOList)
                    .put("product", product)
                    .view("biz/product/listProduct");
        } catch (Exception e) {
            log.info("#菜品列表页出错：" + e.getMessage());
            return ViewResult.newInstance().code(-1).msg(e.getMessage()).view("biz/product/listProduct");
        }
    }

    /**
     * 菜品新增页
     * @return
     * @throws Exception
     */
    @GetMapping("/insert")
    public String preInsertProduct() throws Exception{
        return "biz/product/addProduct";
    }

    /**
     * 新增菜品请求<br />异步请求
     * @return
     * @throws Exception
     */
    @PostMapping("/doInsert")
    @ResponseBody
    public String insertProduct(ProductVO product) throws Exception {
        try{
            Preconditions.checkNotNull(product, "菜品数据不能为空");
//            Preconditions.checkArgument(!Strings.isNullOrEmpty(user.getEmail()), "成员邮箱不能为空");
//            Preconditions.checkArgument(!Strings.isNullOrEmpty(user.getPassword()), "密码不能为空");
//            Preconditions.checkArgument(user.getRoleId() != null && user.getRoleId() > 0, "成员角色数据非法");

            productService.insertProduct(product);
            return ViewResult.newInstance().code(1).msg("新增菜品成功").json();
        } catch (Exception e) {
            log.info("#新增菜品出错：" + e.getMessage());
            return ViewResult.newInstance().code(-1).msg(e.getMessage()).json();
        }
    }

    /**
     * 菜品修改页
     * @return
     * @throws Exception
     */
    @GetMapping("/update")
    public ModelAndView preUpdateProduct(String productId) throws Exception {
        try{
            Preconditions.checkArgument(!Strings.isNullOrEmpty(productId), "菜品ID非法");

            ProductVO product = productService.getProductById(productId);
            return ViewResult.newInstance()
                    .code(1).msg("进菜品修改页成功")
                    .put("product", product)
                    .view("biz/product/editProduct");
        } catch (Exception e) {
            log.info("#进菜品修改页出错：" + e.getMessage());
            return ViewResult.newInstance().code(-1).msg(e.getMessage()).view("biz/product/editProduct");
        }
    }

    /**
     * 修改菜品请求<br />异步请求
     * @return
     * @throws Exception
     */
    @PostMapping("/doUpdate")
    @ResponseBody
    public String updateProduct(ProductVO product) throws Exception{
        try{
            Preconditions.checkNotNull(product, "菜品数据不能为空");
            Preconditions.checkArgument(!Strings.isNullOrEmpty(product.getProductId()), "菜品ID非法");
//            Preconditions.checkArgument(!Strings.isNullOrEmpty(user.getEmail()), "成员邮箱不能为空");
//            Preconditions.checkArgument(user.getRoleId() != null && user.getRoleId() > 0, "成员角色数据非法");

            productService.updateProduct(product);
            return ViewResult.newInstance().code(1).msg("修改菜品信息成功!").json();
        } catch (Exception e) {
            log.info("#修改菜品出错：" + e.getMessage());
            return ViewResult.newInstance().code(-1).msg(e.getMessage()).json();
        }
    }

    /**
     * 删除菜品请求<br />异步请求
     * @return
     * @throws Exception
     */
    @PostMapping("/delete")
    @ResponseBody
    public String deleteProduct(String productId) throws Exception{
        try{
            Preconditions.checkArgument(!Strings.isNullOrEmpty(productId), "菜品ID非法");

            productService.deleteProduct(productId);
            return ViewResult.newInstance().code(1).msg("删除菜品成功").json();
        } catch (Exception e) {
            log.info("#删除菜品出错：" + e.getMessage());
            return ViewResult.newInstance().code(-1).msg(e.getMessage()).json();
        }
    }
}
