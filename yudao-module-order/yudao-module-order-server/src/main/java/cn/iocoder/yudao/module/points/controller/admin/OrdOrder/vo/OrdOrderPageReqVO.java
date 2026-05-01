package cn.iocoder.yudao.module.points.controller.admin.OrdOrder.vo;

import lombok.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 订单主表分页 Request VO")
@Data
public class OrdOrderPageReqVO extends PageParam {

    @Schema(description = "订单编号")
    private String orderSn;

    @Schema(description = "用户ID", example = "19120")
    private Long memberId;

    @Schema(description = "状态:0-待支付;1-待发货;2-已发货;3-已完成;4-已关闭", example = "1")
    private Integer status;

    @Schema(description = "总金额(分)")
    private Long totalAmount;

    @Schema(description = "应付金额(分)")
    private Long payAmount;

    @Schema(description = "优惠金额(分)")
    private Long promotionAmount;

    @Schema(description = "运费(分)")
    private Long freightAmount;

    @Schema(description = "支付方式:1-微信;2-支付宝", example = "2")
    private Integer paymentType;

    @Schema(description = "支付时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] paymentTime;

    @Schema(description = "过期时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] expireTime;

    @Schema(description = "乐观锁版本号")
    private Integer version;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}