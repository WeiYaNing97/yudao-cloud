package cn.iocoder.yudao.module.order.controller.admin.OrdItem.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.validation.constraints.*;

@Schema(description = "管理后台 - 订单明细新增/修改 Request VO")
@Data
public class OrdItemSaveReqVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "7713")
    private Long id;

    @Schema(description = "关联订单ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "9576")
    @NotNull(message = "关联订单ID不能为空")
    private Long orderId;

    @Schema(description = "商品ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "29479")
    @NotNull(message = "商品ID不能为空")
    private Long productId;

    @Schema(description = "商品名称快照", requiredMode = Schema.RequiredMode.REQUIRED, example = "李四")
    @NotEmpty(message = "商品名称快照不能为空")
    private String productName;

    @Schema(description = "SKU编码快照", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "SKU编码快照不能为空")
    private String productSkuCode;

    @Schema(description = "单价(分)", requiredMode = Schema.RequiredMode.REQUIRED, example = "1424")
    @NotNull(message = "单价(分)不能为空")
    private Long productPrice;

    @Schema(description = "数量", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "数量不能为空")
    private Integer productQuantity;

    @Schema(description = "总价(分)", requiredMode = Schema.RequiredMode.REQUIRED, example = "25195")
    @NotNull(message = "总价(分)不能为空")
    private Long productTotalPrice;

}