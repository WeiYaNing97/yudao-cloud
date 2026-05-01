package cn.iocoder.yudao.module.points.service.OrdItem;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.module.points.controller.admin.OrdItem.vo.OrdItemPageReqVO;
import cn.iocoder.yudao.module.points.controller.admin.OrdItem.vo.OrdItemSaveReqVO;
import cn.iocoder.yudao.module.points.dal.dataobject.OrdItem.OrdItemDO;
import cn.iocoder.yudao.module.points.dal.mysql.OrdItem.OrdItemMapper;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.util.List;

/**
 * 订单明细 Service 实现类
 *
 * @author 芋道源码
 */
@Service
@Validated
public class OrdItemServiceImpl implements OrdItemService {

    @Resource
    private OrdItemMapper ordItemMapper;

    @Override
    public Long createOrdItem(OrdItemSaveReqVO createReqVO) {
        // 插入
        OrdItemDO ordItem = BeanUtils.toBean(createReqVO, OrdItemDO.class);
        ordItemMapper.insert(ordItem);

        // 返回
        return ordItem.getId();
    }

    @Override
    public void updateOrdItem(OrdItemSaveReqVO updateReqVO) {
        // 校验存在
        validateOrdItemExists(updateReqVO.getId());
        // 更新
        OrdItemDO updateObj = BeanUtils.toBean(updateReqVO, OrdItemDO.class);
        ordItemMapper.updateById(updateObj);
    }

    @Override
    public void deleteOrdItem(Long id) {
        // 校验存在
        validateOrdItemExists(id);
        // 删除
        ordItemMapper.deleteById(id);
    }

    @Override
        public void deleteOrdItemListByIds(List<Long> ids) {
        // 删除
        ordItemMapper.deleteByIds(ids);
        }


    private void validateOrdItemExists(Long id) {
        if (ordItemMapper.selectById(id) == null) {
            /*throw exception(ORD_ITEM_NOT_EXISTS);*/
        }
    }

    @Override
    public OrdItemDO getOrdItem(Long id) {
        return ordItemMapper.selectById(id);
    }

    @Override
    public PageResult<OrdItemDO> getOrdItemPage(OrdItemPageReqVO pageReqVO) {
        return ordItemMapper.selectPage(pageReqVO);
    }

}