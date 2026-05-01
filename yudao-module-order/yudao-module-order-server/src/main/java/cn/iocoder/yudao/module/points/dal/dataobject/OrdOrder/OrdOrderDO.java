package cn.iocoder.yudao.module.points.dal.dataobject.OrdOrder;

import lombok.*;

import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.*;
import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;

/**
 * 订单主表 DO
 *
 * @author 芋道源码
 */
@TableName("ord_order")
@KeySequence("ord_order_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrdOrderDO extends BaseDO {

    /**
     * 主键ID
     */
    @TableId
    private Long id;
    /**
     * 订单编号
     */
    private String orderSn;
    /**
     * 用户ID
     */
    private Long memberId;
    /**
     * 状态:0-待支付;1-待发货;2-已发货;3-已完成;4-已关闭
     */
    private Integer status;
    /**
     * 总金额(分)
     */
    private Long totalAmount;
    /**
     * 应付金额(分)
     */
    private Long payAmount;
    /**
     * 优惠金额(分)
     */
    private Long promotionAmount;
    /**
     * 运费(分)
     */
    private Long freightAmount;
    /**
     * 支付方式:1-微信;2-支付宝
     */
    private Integer paymentType;
    /**
     * 支付时间
     */
    private LocalDateTime paymentTime;
    /**
     * 过期时间
     */
    private LocalDateTime expireTime;
    /**
     * 乐观锁版本号
     */
    private Integer version;


}
