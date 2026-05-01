package cn.iocoder.yudao.module.points.dal.dataobject.OrdPayment;

import lombok.*;
import com.baomidou.mybatisplus.annotation.*;
import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;

/**
 * 支付流水 DO
 *
 * @author 芋道源码
 */
@TableName("ord_payment")
@KeySequence("ord_payment_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrdPaymentDO extends BaseDO {

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
     * 支付流水号
     */
    private String paymentSn;
    /**
     * 第三方交易号
     */
    private String transactionId;
    /**
     * 金额(分)
     */
    private Long amount;
    /**
     * 状态:0-未支付;1-成功
     */
    private Integer status;


}
