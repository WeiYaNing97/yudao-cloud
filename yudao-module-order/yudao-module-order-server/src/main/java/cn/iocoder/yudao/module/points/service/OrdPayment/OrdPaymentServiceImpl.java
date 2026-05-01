package cn.iocoder.yudao.module.points.service.OrdPayment;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.module.points.controller.admin.OrdPayment.vo.OrdPaymentPageReqVO;
import cn.iocoder.yudao.module.points.controller.admin.OrdPayment.vo.OrdPaymentSaveReqVO;
import cn.iocoder.yudao.module.points.dal.dataobject.OrdPayment.OrdPaymentDO;
import cn.iocoder.yudao.module.points.dal.mysql.OrdPayment.OrdPaymentMapper;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import javax.annotation.Resource;
import java.util.List;

/**
 * 支付流水 Service 实现类
 *
 * @author 芋道源码
 */
@Service
@Validated
public class OrdPaymentServiceImpl implements OrdPaymentService {

    @Resource
    private OrdPaymentMapper ordPaymentMapper;

    @Override
    public Long createOrdPayment(OrdPaymentSaveReqVO createReqVO) {
        // 插入
        OrdPaymentDO ordPayment = BeanUtils.toBean(createReqVO, OrdPaymentDO.class);
        ordPaymentMapper.insert(ordPayment);

        // 返回
        return ordPayment.getId();
    }

    @Override
    public void updateOrdPayment(OrdPaymentSaveReqVO updateReqVO) {
        // 校验存在
        validateOrdPaymentExists(updateReqVO.getId());
        // 更新
        OrdPaymentDO updateObj = BeanUtils.toBean(updateReqVO, OrdPaymentDO.class);
        ordPaymentMapper.updateById(updateObj);
    }

    @Override
    public void deleteOrdPayment(Long id) {
        // 校验存在
        validateOrdPaymentExists(id);
        // 删除
        ordPaymentMapper.deleteById(id);
    }

    @Override
        public void deleteOrdPaymentListByIds(List<Long> ids) {
        // 删除
        ordPaymentMapper.deleteByIds(ids);
        }


    private void validateOrdPaymentExists(Long id) {
        if (ordPaymentMapper.selectById(id) == null) {
            /*throw exception(ORD_PAYMENT_NOT_EXISTS);*/
        }
    }

    @Override
    public OrdPaymentDO getOrdPayment(Long id) {
        return ordPaymentMapper.selectById(id);
    }

    @Override
    public PageResult<OrdPaymentDO> getOrdPaymentPage(OrdPaymentPageReqVO pageReqVO) {
        return ordPaymentMapper.selectPage(pageReqVO);
    }

}