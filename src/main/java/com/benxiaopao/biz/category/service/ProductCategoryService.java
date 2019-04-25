package com.benxiaopao.biz.category.service;

import com.benxiaopao.biz.category.vo.ProductCategoryVO;
import com.benxiaopao.biz.product.vo.ProductVO;
import com.benxiaopao.biz.restaurant.vo.RestaurantVO;
import com.benxiaopao.common.supers.BaseService;
import com.benxiaopao.common.util.Pagination;
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
 * 品类业务逻辑服务处理类
 *
 * Created by liupoyang
 * 2019-04-20
 */
@Service
@Slf4j
public class ProductCategoryService extends BaseService {
    @Autowired
    private ThriftClient thriftClient;

    /**
     * 根据条件获取品类列表，带分页
     * @param productCategory 条件参数品类对象
     * @param pageNum 页码
     * @param pageSize 页面大小
     * @return List<ProductCategoryVO> 品类列表
     */
    public List<ProductCategoryVO> listProductCategoryByWherePage(ProductCategoryVO productCategory, int pageNum, int pageSize) throws Exception {
        if(productCategory == null){
            productCategory = new ProductCategoryVO();
        }

        Pagination pagination = Pagination.currentPagination(pageNum, pageSize);

        TPListProductCategory tpListProductCategory = new TPListProductCategory();
        TPPagination tpPagination = new TPPagination();
        tpPagination.setPageSize(pageSize);
        tpPagination.setPageNo(pageNum);
        tpListProductCategory.setPagination(tpPagination);

        TPProductCategory tpProductCategory = new TPProductCategory();
        BeanUtils.copyProperties(productCategory, tpProductCategory, "createTime", "updateTime");
        tpListProductCategory.setProductCategory(tpProductCategory);

        List<ProductCategoryVO> result = Lists.newArrayList();
        try {
            thriftClient.open();
            TRListProductCategory trListProductCategory = thriftClient.getThriftService().listProductCategoryByPage(tpListProductCategory);
            if(trListProductCategory == null || !trListProductCategory.response.isSuccess()){
                return result;
            }

            pagination.setTotalCount(trListProductCategory.getPagination().getTotalCount());

            result =  FluentIterable.from(trListProductCategory.getProductCategoryList()).transform(new Function<TRProductCategory, ProductCategoryVO>() {
                @Override
                public ProductCategoryVO apply(TRProductCategory trProductCategory) {
                    ProductCategoryVO productCategoryVO = new ProductCategoryVO();
                    BeanUtils.copyProperties(trProductCategory, productCategoryVO, "createTime", "updateTime");
                    productCategoryVO.setCreateTime(new Date(trProductCategory.getCreateTime()));
                    productCategoryVO.setUpdateTime(new Date(trProductCategory.getUpdateTime()));
                    return productCategoryVO;
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
     * 根据条件获取品类列表
     * @param productCategory 条件参数品类对象
     * @return List<ProductCategoryVO> 品类列表
     */
    public List<ProductCategoryVO> listProductCategoryByWhere(ProductCategoryVO productCategory) throws Exception {
        if(productCategory == null){
            productCategory = new ProductCategoryVO();
        }

        TPListProductCategory tpListProductCategory = new TPListProductCategory();

        TPProductCategory tpProductCategory = new TPProductCategory();
        BeanUtils.copyProperties(productCategory, tpProductCategory, "createTime", "updateTime");
        tpListProductCategory.setProductCategory(tpProductCategory);

        List<ProductCategoryVO> result = Lists.newArrayList();
        try {
            thriftClient.open();
            TRListProductCategory trListProductCategory = thriftClient.getThriftService().listProductCategory(tpListProductCategory);
            if(trListProductCategory == null || !trListProductCategory.response.isSuccess()){
                return result;
            }

            result =  FluentIterable.from(trListProductCategory.getProductCategoryList()).transform(new Function<TRProductCategory, ProductCategoryVO>() {
                @Override
                public ProductCategoryVO apply(TRProductCategory trProductCategory) {
                    ProductCategoryVO productCategoryVO = new ProductCategoryVO();
                    BeanUtils.copyProperties(trProductCategory, productCategoryVO, "createTime", "updateTime");
                    productCategoryVO.setCreateTime(new Date(trProductCategory.getCreateTime()));
                    productCategoryVO.setUpdateTime(new Date(trProductCategory.getUpdateTime()));
                    return productCategoryVO;
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
     * 新增品类
     * @param productCategory 要插入的品类数据对象
     */
    public void insertProductCategory(ProductCategoryVO productCategory) throws Exception {
        int records = 0;

        TPProductCategory tpProductCategory = new TPProductCategory();
        BeanUtils.copyProperties(productCategory, tpProductCategory, "createTime", "updateTime");

        try {
            thriftClient.open();
            records = thriftClient.getThriftService().insertProductCategory(tpProductCategory);
        } catch (Exception e) {
            log.error("# RPC调用失败", e);
        } finally {
            thriftClient.close();
        }
        Preconditions.checkArgument(records > 0, "新增品类失败");
    }

    /**
     * 修改品类
     * @param productCategory 要更新的品类数据对象
     */
    public void updateProductCategory(ProductCategoryVO productCategory) throws Exception {
        int records = 0;

        TPProductCategory tpProductCategory = new TPProductCategory();
        BeanUtils.copyProperties(productCategory, tpProductCategory, "createTime", "updateTime");

        try {
            thriftClient.open();
            records = thriftClient.getThriftService().updateProductCategory(tpProductCategory);
        } catch (Exception e) {
            log.error("# RPC调用失败", e);
        } finally {
            thriftClient.close();
        }
        Preconditions.checkArgument(records > 0, "修改品类失败");
    }

    /**
     * 删除品类
     * @param productCategoryId 品类id
     */
    public void deleteProductCategory(int productCategoryId) throws Exception {
        int records = 0;
        try {
            thriftClient.open();
            records = thriftClient.getThriftService().deleteProductCategory(productCategoryId);
        } catch (Exception e) {
            log.error("# RPC调用失败", e);
        } finally {
            thriftClient.close();
        }
        Preconditions.checkArgument(records > 0, "删除品类失败");
    }

    /**
     * 根据品类id获取品类对象
     * @param productCategoryId 品类id
     */
    public ProductCategoryVO getProductCategoryById(int productCategoryId) throws Exception {
        ProductCategoryVO productCategory = null;
        try {
            thriftClient.open();
            TRProductCategory trProductCategory = thriftClient.getThriftService().getProductCategoryById(productCategoryId);
            productCategory = new ProductCategoryVO();
            BeanUtils.copyProperties(trProductCategory, productCategory, "createTime", "updateTime");
            productCategory.setCreateTime(new Date(trProductCategory.getCreateTime()));
            productCategory.setUpdateTime(new Date(trProductCategory.getUpdateTime()));
        } catch (Exception e) {
            log.error("# RPC调用失败", e);
        } finally {
            thriftClient.close();
        }
        return productCategory;
    }
}
