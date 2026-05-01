package cn.iocoder.yudao.module.points.controller.admin.OrdItem.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import cn.idev.excel.annotation.*;

@Schema(description = "管理后台 - 订单明细 Response VO")
@Data
@ExcelIgnoreUnannotated
public class OrdItemRespVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "7713")
    @ExcelProperty("主键")
    private Long id;

    @Schema(description = "关联订单ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "9576")
    @ExcelProperty("关联订单ID")
    private Long orderId;

    @Schema(description = "商品ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "29479")
    @ExcelProperty("商品ID")
    private Long productId;

    @Schema(description = "商品名称快照", requiredMode = Schema.RequiredMode.REQUIRED, example = "李四")
    @ExcelProperty("商品名称快照")
    private String productName;

    @Schema(description = "SKU编码快照", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("SKU编码快照")
    private String productSkuCode;

    @Schema(description = "单价(分)", requiredMode = Schema.RequiredMode.REQUIRED, example = "1424")
    @ExcelProperty("单价(分)")
    private Long productPrice;

    @Schema(description = "数量", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("数量")
    private Integer productQuantity;

    @Schema(description = "总价(分)", requiredMode = Schema.RequiredMode.REQUIRED, example = "25195")
    @ExcelProperty("总价(分)")
    private Long productTotalPrice;

}
