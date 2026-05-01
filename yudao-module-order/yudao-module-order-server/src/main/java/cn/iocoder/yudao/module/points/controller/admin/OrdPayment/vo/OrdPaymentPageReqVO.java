package cn.iocoder.yudao.module.points.controller.admin.OrdPayment.vo;

import lombok.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 支付流水分页 Request VO")
@Data
public class OrdPaymentPageReqVO extends PageParam {

    @Schema(description = "关联订单ID", example = "9871")
    private Long orderId;

    @Schema(description = "支付流水号")
    private String paymentSn;

    @Schema(description = "第三方交易号", example = "25050")
    private String transactionId;

    @Schema(description = "金额(分)")
    private Long amount;

    @Schema(description = "状态:0-未支付;1-成功", example = "1")
    private Integer status;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}