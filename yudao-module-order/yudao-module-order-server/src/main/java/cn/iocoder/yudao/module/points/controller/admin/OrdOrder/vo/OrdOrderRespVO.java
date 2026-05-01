package cn.iocoder.yudao.module.points.controller.admin.OrdOrder.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;
import cn.idev.excel.annotation.*;

@Schema(description = "管理后台 - 订单主表 Response VO")
@Data
@ExcelIgnoreUnannotated
public class OrdOrderRespVO {

    @Schema(description = "主键ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "7133")
    @ExcelProperty("主键ID")
    private Long id;

    @Schema(description = "订单编号", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("订单编号")
    private String orderSn;

    @Schema(description = "用户ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "19120")
    @ExcelProperty("用户ID")
    private Long memberId;

    @Schema(description = "状态:0-待支付;1-待发货;2-已发货;3-已完成;4-已关闭", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @ExcelProperty("状态:0-待支付;1-待发货;2-已发货;3-已完成;4-已关闭")
    private Integer status;

    @Schema(description = "总金额(分)", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("总金额(分)")
    private Long totalAmount;

    @Schema(description = "应付金额(分)", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("应付金额(分)")
    private Long payAmount;

    @Schema(description = "优惠金额(分)", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("优惠金额(分)")
    private Long promotionAmount;

    @Schema(description = "运费(分)", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("运费(分)")
    private Long freightAmount;

    @Schema(description = "支付方式:1-微信;2-支付宝", example = "2")
    @ExcelProperty("支付方式:1-微信;2-支付宝")
    private Integer paymentType;

    @Schema(description = "支付时间")
    @ExcelProperty("支付时间")
    private LocalDateTime paymentTime;

    @Schema(description = "过期时间")
    @ExcelProperty("过期时间")
    private LocalDateTime expireTime;

    @Schema(description = "乐观锁版本号", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("乐观锁版本号")
    private Integer version;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}
