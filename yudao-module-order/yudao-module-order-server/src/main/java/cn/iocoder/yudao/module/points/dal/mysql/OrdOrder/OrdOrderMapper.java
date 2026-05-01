package cn.iocoder.yudao.module.points.dal.mysql.OrdOrder;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.module.points.dal.dataobject.OrdOrder.OrdOrderDO;
import org.apache.ibatis.annotations.Mapper;
import cn.iocoder.yudao.module.points.controller.admin.OrdOrder.vo.*;

/**
 * 订单主表 Mapper
 *
 * @author 芋道源码
 */
@Mapper
public interface OrdOrderMapper extends BaseMapperX<OrdOrderDO> {

    default PageResult<OrdOrderDO> selectPage(OrdOrderPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<OrdOrderDO>()
                .eqIfPresent(OrdOrderDO::getOrderSn, reqVO.getOrderSn())
                .eqIfPresent(OrdOrderDO::getMemberId, reqVO.getMemberId())
                .eqIfPresent(OrdOrderDO::getStatus, reqVO.getStatus())
                .eqIfPresent(OrdOrderDO::getTotalAmount, reqVO.getTotalAmount())
                .eqIfPresent(OrdOrderDO::getPayAmount, reqVO.getPayAmount())
                .eqIfPresent(OrdOrderDO::getPromotionAmount, reqVO.getPromotionAmount())
                .eqIfPresent(OrdOrderDO::getFreightAmount, reqVO.getFreightAmount())
                .eqIfPresent(OrdOrderDO::getPaymentType, reqVO.getPaymentType())
                .betweenIfPresent(OrdOrderDO::getPaymentTime, reqVO.getPaymentTime())
                .betweenIfPresent(OrdOrderDO::getExpireTime, reqVO.getExpireTime())
                .eqIfPresent(OrdOrderDO::getVersion, reqVO.getVersion())
                .betweenIfPresent(OrdOrderDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(OrdOrderDO::getId));
    }

}