package cn.iocoder.yudao.module.order.controller.admin.OrdItem.vo;

import lombok.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.iocoder.yudao.framework.common.pojo.PageParam;

@Schema(description = "管理后台 - 订单明细分页 Request VO")
@Data
public class OrdItemPageReqVO extends PageParam {

    @Schema(description = "关联订单ID", example = "9576")
    private Long orderId;

    @Schema(description = "商品ID", example = "29479")
    private Long productId;

    @Schema(description = "商品名称快照", example = "李四")
    private String productName;

    @Schema(description = "SKU编码快照")
    private String productSkuCode;

    @Schema(description = "单价(分)", example = "1424")
    private Long productPrice;

    @Schema(description = "数量")
    private Integer productQuantity;

    @Schema(description = "总价(分)", example = "25195")
    private Long productTotalPrice;

}