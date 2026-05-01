package cn.iocoder.yudao.module.points.service.OrdPayment;

import java.util.*;
import javax.validation.*;
import cn.iocoder.yudao.module.points.controller.admin.OrdPayment.vo.*;
import cn.iocoder.yudao.module.points.dal.dataobject.OrdPayment.OrdPaymentDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

/**
 * 支付流水 Service 接口
 *
 * @author 芋道源码
 */
public interface OrdPaymentService {

    /**
     * 创建支付流水
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createOrdPayment(@Valid OrdPaymentSaveReqVO createReqVO);

    /**
     * 更新支付流水
     *
     * @param updateReqVO 更新信息
     */
    void updateOrdPayment(@Valid OrdPaymentSaveReqVO updateReqVO);

    /**
     * 删除支付流水
     *
     * @param id 编号
     */
    void deleteOrdPayment(Long id);

    /**
    * 批量删除支付流水
    *
    * @param ids 编号
    */
    void deleteOrdPaymentListByIds(List<Long> ids);

    /**
     * 获得支付流水
     *
     * @param id 编号
     * @return 支付流水
     */
    OrdPaymentDO getOrdPayment(Long id);

    /**
     * 获得支付流水分页
     *
     * @param pageReqVO 分页查询
     * @return 支付流水分页
     */
    PageResult<OrdPaymentDO> getOrdPaymentPage(OrdPaymentPageReqVO pageReqVO);

}