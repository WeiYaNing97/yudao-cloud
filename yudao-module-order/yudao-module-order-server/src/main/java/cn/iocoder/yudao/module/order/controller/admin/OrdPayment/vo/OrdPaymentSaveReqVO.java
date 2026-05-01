package cn.iocoder.yudao.module.order.controller.admin.OrdPayment.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.validation.constraints.*;

@Schema(description = "管理后台 - 支付流水新增/修改 Request VO")
@Data
public class OrdPaymentSaveReqVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "8438")
    private Long id;

    @Schema(description = "关联订单ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "9871")
    @NotNull(message = "关联订单ID不能为空")
    private Long orderId;

    @Schema(description = "支付流水号", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "支付流水号不能为空")
    private String paymentSn;

    @Schema(description = "第三方交易号", example = "25050")
    private String transactionId;

    @Schema(description = "金额(分)", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "金额(分)不能为空")
    private Long amount;

    @Schema(description = "状态:0-未支付;1-成功", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "状态:0-未支付;1-成功不能为空")
    private Integer status;

}