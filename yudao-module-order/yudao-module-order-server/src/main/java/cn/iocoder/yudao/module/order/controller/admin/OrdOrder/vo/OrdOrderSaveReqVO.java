package cn.iocoder.yudao.module.order.controller.admin.OrdOrder.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.validation.constraints.*;
import java.time.LocalDateTime;

@Schema(description = "管理后台 - 订单主表新增/修改 Request VO")
@Data
public class OrdOrderSaveReqVO {

    @Schema(description = "主键ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "7133")
    private Long id;

    @Schema(description = "订单编号", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "订单编号不能为空")
    private String orderSn;

    @Schema(description = "用户ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "19120")
    @NotNull(message = "用户ID不能为空")
    private Long memberId;

    @Schema(description = "状态:0-待支付;1-待发货;2-已发货;3-已完成;4-已关闭", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "状态:0-待支付;1-待发货;2-已发货;3-已完成;4-已关闭不能为空")
    private Integer status;

    @Schema(description = "总金额(分)", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "总金额(分)不能为空")
    private Long totalAmount;

    @Schema(description = "应付金额(分)", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "应付金额(分)不能为空")
    private Long payAmount;

    @Schema(description = "优惠金额(分)", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "优惠金额(分)不能为空")
    private Long promotionAmount;

    @Schema(description = "运费(分)", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "运费(分)不能为空")
    private Long freightAmount;

    @Schema(description = "支付方式:1-微信;2-支付宝", example = "2")
    private Integer paymentType;

    @Schema(description = "支付时间")
    private LocalDateTime paymentTime;

    @Schema(description = "过期时间")
    private LocalDateTime expireTime;

    @Schema(description = "乐观锁版本号", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "乐观锁版本号不能为空")
    private Integer version;

}