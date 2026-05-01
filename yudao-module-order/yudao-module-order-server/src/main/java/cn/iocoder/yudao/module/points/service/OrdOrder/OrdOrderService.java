package cn.iocoder.yudao.module.points.service.OrdOrder;

import java.util.*;
import javax.validation.*;
import cn.iocoder.yudao.module.points.controller.admin.OrdOrder.vo.*;
import cn.iocoder.yudao.module.points.dal.dataobject.OrdOrder.OrdOrderDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

/**
 * 订单主表 Service 接口
 *
 * @author 芋道源码
 */
public interface OrdOrderService {

    /**
     * 创建订单主表
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createOrdOrder(@Valid OrdOrderSaveReqVO createReqVO);

    /**
     * 更新订单主表
     *
     * @param updateReqVO 更新信息
     */
    void updateOrdOrder(@Valid OrdOrderSaveReqVO updateReqVO);

    /**
     * 删除订单主表
     *
     * @param id 编号
     */
    void deleteOrdOrder(Long id);

    /**
    * 批量删除订单主表
    *
    * @param ids 编号
    */
    void deleteOrdOrderListByIds(List<Long> ids);

    /**
     * 获得订单主表
     *
     * @param id 编号
     * @return 订单主表
     */
    OrdOrderDO getOrdOrder(Long id);

    /**
     * 获得订单主表分页
     *
     * @param pageReqVO 分页查询
     * @return 订单主表分页
     */
    PageResult<OrdOrderDO> getOrdOrderPage(OrdOrderPageReqVO pageReqVO);

}