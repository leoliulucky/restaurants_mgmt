package com.benxiaopao.biz.product.service;

import com.benxiaopao.biz.product.vo.ProductVO;
import com.benxiaopao.common.component.WebSocket;
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
 * 菜品业务逻辑服务处理类
 *
 * Created by liupoyang
 * 2019-04-20
 */
@Service
@Slf4j
public class ProductService extends BaseService {
    @Autowired
    private ThriftClient thriftClient;
    @Autowired
    private WebSocket webSocket;

    /**
     * 根据条件获取菜品列表，带分页
     * @param product 条件参数菜品对象
     * @param pageNum 页码
     * @param pageSize 页面大小
     * @return List<ProductVO> 菜品列表
     */
    public List<ProductVO> listProductByWherePage(ProductVO product, int pageNum, int pageSize) throws Exception {
        if(product == null){
            product = new ProductVO();
        }

        Pagination pagination = Pagination.currentPagination(pageNum, pageSize);

        TPListProduct tpListProduct = new TPListProduct();
        TPPagination tpPagination = new TPPagination();
        tpPagination.setPageSize(pageSize);
        tpPagination.setPageNo(pageNum);
        tpListProduct.setPagination(tpPagination);

        TPProduct tpProduct = new TPProduct();
        BeanUtils.copyProperties(product, tpProduct, "createTime", "updateTime");
        tpListProduct.setProduct(tpProduct);

        List<ProductVO> result = Lists.newArrayList();
        try {
            thriftClient.open();
            TRListProduct trListProduct = thriftClient.getThriftService().listProductByPage(tpListProduct);
            if(trListProduct == null || !trListProduct.response.isSuccess()){
                return result;
            }

            pagination.setTotalCount(trListProduct.getPagination().getTotalCount());

            result =  FluentIterable.from(trListProduct.getProductList()).transform(new Function<TRProduct, ProductVO>() {
                @Override
                public ProductVO apply(TRProduct trProduct) {
                    ProductVO productVO = new ProductVO();
                    BeanUtils.copyProperties(trProduct, productVO, "createTime", "updateTime");
                    productVO.setCreateTime(new Date(trProduct.getCreateTime()));
                    productVO.setUpdateTime(new Date(trProduct.getUpdateTime()));
                    return productVO;
                }
            }).toList();
        } catch (Exception e) {
            log.error("# RPC调用失败", e);
        } finally {
            thriftClient.close();
        }

        //发送websocket消息
        webSocket.sendMessage("xxxxx");

        return result;
    }

    /**
     * 新增菜品
     * @param product 要插入的菜品数据对象
     */
    public void insertProduct(ProductVO product) throws Exception {
        int records = 0;

        TPProduct tpProduct = new TPProduct();
        BeanUtils.copyProperties(product, tpProduct, "createTime", "updateTime");

        try {
            thriftClient.open();
            records = thriftClient.getThriftService().insertProduct(tpProduct);
        } catch (Exception e) {
            log.error("# RPC调用失败", e);
        } finally {
            thriftClient.close();
        }
        Preconditions.checkArgument(records > 0, "新增菜品失败");
    }

    /**
     * 修改菜品
     * @param product 要更新的菜品数据对象
     */
    public void updateProduct(ProductVO product) throws Exception {
        int records = 0;

        TPProduct tpProduct = new TPProduct();
        BeanUtils.copyProperties(product, tpProduct, "createTime", "updateTime");

        try {
            thriftClient.open();
            records = thriftClient.getThriftService().updateProduct(tpProduct);
        } catch (Exception e) {
            log.error("# RPC调用失败", e);
        } finally {
            thriftClient.close();
        }
        Preconditions.checkArgument(records > 0, "修改菜品失败");
    }

    /**
     * 删除菜品
     * @param productId 菜品id
     */
    public void deleteProduct(String productId) throws Exception {
        int records = 0;
        try {
            thriftClient.open();
            records = thriftClient.getThriftService().deleteProduct(productId);
        } catch (Exception e) {
            log.error("# RPC调用失败", e);
        } finally {
            thriftClient.close();
        }
        Preconditions.checkArgument(records > 0, "删除菜品失败");
    }

    /**
     * 根据菜品id获取菜品对象
     * @param productId 菜品id
     */
    public ProductVO getProductById(String productId) throws Exception {
        ProductVO product = null;
        try {
            thriftClient.open();
            TRProduct trProduct = thriftClient.getThriftService().getProductById(productId);
            product = new ProductVO();
            BeanUtils.copyProperties(trProduct, product, "createTime", "updateTime");
            product.setCreateTime(new Date(trProduct.getCreateTime()));
            product.setUpdateTime(new Date(trProduct.getUpdateTime()));
        } catch (Exception e) {
            log.error("# RPC调用失败", e);
        } finally {
            thriftClient.close();
        }
        return product;
    }
}
