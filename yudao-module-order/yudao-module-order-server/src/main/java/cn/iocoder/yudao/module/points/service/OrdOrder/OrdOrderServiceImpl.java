package cn.iocoder.yudao.module.points.service.OrdOrder;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.module.points.controller.admin.OrdOrder.vo.OrdOrderPageReqVO;
import cn.iocoder.yudao.module.points.controller.admin.OrdOrder.vo.OrdOrderSaveReqVO;
import cn.iocoder.yudao.module.points.dal.dataobject.OrdOrder.OrdOrderDO;
import cn.iocoder.yudao.module.points.dal.mysql.OrdOrder.OrdOrderMapper;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.util.List;

/**
 * 订单主表 Service 实现类
 *
 * @author 芋道源码
 */
@Service
@Validated
public class OrdOrderServiceImpl implements OrdOrderService {

    @Resource
    private OrdOrderMapper ordOrderMapper;

    @Override
    public Long createOrdOrder(OrdOrderSaveReqVO createReqVO) {
        // 插入
        OrdOrderDO ordOrder = BeanUtils.toBean(createReqVO, OrdOrderDO.class);
        ordOrderMapper.insert(ordOrder);

        // 返回
        return ordOrder.getId();
    }

    @Override
    public void updateOrdOrder(OrdOrderSaveReqVO updateReqVO) {
        // 校验存在
        validateOrdOrderExists(updateReqVO.getId());
        // 更新
        OrdOrderDO updateObj = BeanUtils.toBean(updateReqVO, OrdOrderDO.class);
        ordOrderMapper.updateById(updateObj);
    }

    @Override
    public void deleteOrdOrder(Long id) {
        // 校验存在
        validateOrdOrderExists(id);
        // 删除
        ordOrderMapper.deleteById(id);
    }

    @Override
        public void deleteOrdOrderListByIds(List<Long> ids) {
        // 删除
        ordOrderMapper.deleteByIds(ids);
        }


    private void validateOrdOrderExists(Long id) {
        if (ordOrderMapper.selectById(id) == null) {
            /*throw exception(ORD_ORDER_NOT_EXISTS);*/
        }
    }

    @Override
    public OrdOrderDO getOrdOrder(Long id) {
        return ordOrderMapper.selectById(id);
    }

    @Override
    public PageResult<OrdOrderDO> getOrdOrderPage(OrdOrderPageReqVO pageReqVO) {
        return ordOrderMapper.selectPage(pageReqVO);
    }

}