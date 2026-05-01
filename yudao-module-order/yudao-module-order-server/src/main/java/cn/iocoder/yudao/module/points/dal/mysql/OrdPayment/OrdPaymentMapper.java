package cn.iocoder.yudao.module.points.dal.mysql.OrdPayment;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.module.points.dal.dataobject.OrdPayment.OrdPaymentDO;
import org.apache.ibatis.annotations.Mapper;
import cn.iocoder.yudao.module.points.controller.admin.OrdPayment.vo.*;

/**
 * 支付流水 Mapper
 *
 * @author 芋道源码
 */
@Mapper
public interface OrdPaymentMapper extends BaseMapperX<OrdPaymentDO> {

    default PageResult<OrdPaymentDO> selectPage(OrdPaymentPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<OrdPaymentDO>()
                .eqIfPresent(OrdPaymentDO::getOrderId, reqVO.getOrderId())
                .eqIfPresent(OrdPaymentDO::getPaymentSn, reqVO.getPaymentSn())
                .eqIfPresent(OrdPaymentDO::getTransactionId, reqVO.getTransactionId())
                .eqIfPresent(OrdPaymentDO::getAmount, reqVO.getAmount())
                .eqIfPresent(OrdPaymentDO::getStatus, reqVO.getStatus())
                .betweenIfPresent(OrdPaymentDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(OrdPaymentDO::getId));
    }

}