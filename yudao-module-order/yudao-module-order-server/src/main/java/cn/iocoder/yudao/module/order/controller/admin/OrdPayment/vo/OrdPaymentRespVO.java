package cn.iocoder.yudao.module.order.controller.admin.OrdPayment.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;
import cn.idev.excel.annotation.*;

@Schema(description = "管理后台 - 支付流水 Response VO")
@Data
@ExcelIgnoreUnannotated
public class OrdPaymentRespVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "8438")
    @ExcelProperty("主键")
    private Long id;

    @Schema(description = "关联订单ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "9871")
    @ExcelProperty("关联订单ID")
    private Long orderId;

    @Schema(description = "支付流水号", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("支付流水号")
    private String paymentSn;

    @Schema(description = "第三方交易号", example = "25050")
    @ExcelProperty("第三方交易号")
    private String transactionId;

    @Schema(description = "金额(分)", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("金额(分)")
    private Long amount;

    @Schema(description = "状态:0-未支付;1-成功", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @ExcelProperty("状态:0-未支付;1-成功")
    private Integer status;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}
