package cn.iocoder.yudao.module.points.dal.mysql.OrdItem;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.module.points.dal.dataobject.OrdItem.OrdItemDO;
import org.apache.ibatis.annotations.Mapper;
import cn.iocoder.yudao.module.points.controller.admin.OrdItem.vo.*;

/**
 * 订单明细 Mapper
 *
 * @author 芋道源码
 */
@Mapper
public interface OrdItemMapper extends BaseMapperX<OrdItemDO> {

    default PageResult<OrdItemDO> selectPage(OrdItemPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<OrdItemDO>()
                .eqIfPresent(OrdItemDO::getOrderId, reqVO.getOrderId())
                .eqIfPresent(OrdItemDO::getProductId, reqVO.getProductId())
                .likeIfPresent(OrdItemDO::getProductName, reqVO.getProductName())
                .eqIfPresent(OrdItemDO::getProductSkuCode, reqVO.getProductSkuCode())
                .eqIfPresent(OrdItemDO::getProductPrice, reqVO.getProductPrice())
                .eqIfPresent(OrdItemDO::getProductQuantity, reqVO.getProductQuantity())
                .eqIfPresent(OrdItemDO::getProductTotalPrice, reqVO.getProductTotalPrice())
                .orderByDesc(OrdItemDO::getId));
    }

}