package cn.iocoder.yudao.module.points.service.OrdItem;

import java.util.*;
import javax.validation.*;
import cn.iocoder.yudao.module.points.controller.admin.OrdItem.vo.*;
import cn.iocoder.yudao.module.points.dal.dataobject.OrdItem.OrdItemDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

/**
 * 订单明细 Service 接口
 *
 * @author 芋道源码
 */
public interface OrdItemService {

    /**
     * 创建订单明细
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createOrdItem(@Valid OrdItemSaveReqVO createReqVO);

    /**
     * 更新订单明细
     *
     * @param updateReqVO 更新信息
     */
    void updateOrdItem(@Valid OrdItemSaveReqVO updateReqVO);

    /**
     * 删除订单明细
     *
     * @param id 编号
     */
    void deleteOrdItem(Long id);

    /**
    * 批量删除订单明细
    *
    * @param ids 编号
    */
    void deleteOrdItemListByIds(List<Long> ids);

    /**
     * 获得订单明细
     *
     * @param id 编号
     * @return 订单明细
     */
    OrdItemDO getOrdItem(Long id);

    /**
     * 获得订单明细分页
     *
     * @param pageReqVO 分页查询
     * @return 订单明细分页
     */
    PageResult<OrdItemDO> getOrdItemPage(OrdItemPageReqVO pageReqVO);

}