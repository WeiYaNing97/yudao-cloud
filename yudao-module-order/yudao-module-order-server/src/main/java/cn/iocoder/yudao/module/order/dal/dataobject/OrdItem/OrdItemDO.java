package cn.iocoder.yudao.module.order.dal.dataobject.OrdItem;

import lombok.*;
import com.baomidou.mybatisplus.annotation.*;
import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;

/**
 * 订单明细 DO
 *
 * @author 芋道源码
 */
@TableName("ord_item")
@KeySequence("ord_item_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrdItemDO extends BaseDO {

    /**
     * 主键
     */
    @TableId
    private Long id;
    /**
     * 关联订单ID
     */
    private Long orderId;
    /**
     * 商品ID
     */
    private Long productId;
    /**
     * 商品名称快照
     */
    private String productName;
    /**
     * SKU编码快照
     */
    private String productSkuCode;
    /**
     * 单价(分)
     */
    private Long productPrice;
    /**
     * 数量
     */
    private Integer productQuantity;
    /**
     * 总价(分)
     */
    private Long productTotalPrice;


}
