package cn.iocoder.yudao.module.points.controller.admin.OrdOrder;

import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Operation;

import javax.validation.*;
import javax.servlet.http.*;
import java.util.*;
import java.io.IOException;

import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

import cn.iocoder.yudao.framework.excel.core.util.ExcelUtils;

import cn.iocoder.yudao.framework.apilog.core.annotation.ApiAccessLog;
import static cn.iocoder.yudao.framework.apilog.core.enums.OperateTypeEnum.*;

import cn.iocoder.yudao.module.points.controller.admin.OrdOrder.vo.*;
import cn.iocoder.yudao.module.points.dal.dataobject.OrdOrder.OrdOrderDO;
import cn.iocoder.yudao.module.points.service.OrdOrder.OrdOrderService;

@Tag(name = "管理后台 - 订单主表")
@RestController
@RequestMapping("/order/ord-order")
@Validated
public class OrdOrderController {

    @Resource
    private OrdOrderService ordOrderService;

    @PostMapping("/create")
    @Operation(summary = "创建订单主表")
    @PreAuthorize("@ss.hasPermission('order:ord-order:create')")
    public CommonResult<Long> createOrdOrder(@Valid @RequestBody OrdOrderSaveReqVO createReqVO) {
        return success(ordOrderService.createOrdOrder(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新订单主表")
    @PreAuthorize("@ss.hasPermission('order:ord-order:update')")
    public CommonResult<Boolean> updateOrdOrder(@Valid @RequestBody OrdOrderSaveReqVO updateReqVO) {
        ordOrderService.updateOrdOrder(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除订单主表")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('order:ord-order:delete')")
    public CommonResult<Boolean> deleteOrdOrder(@RequestParam("id") Long id) {
        ordOrderService.deleteOrdOrder(id);
        return success(true);
    }

    @DeleteMapping("/delete-list")
    @Parameter(name = "ids", description = "编号", required = true)
    @Operation(summary = "批量删除订单主表")
                @PreAuthorize("@ss.hasPermission('order:ord-order:delete')")
    public CommonResult<Boolean> deleteOrdOrderList(@RequestParam("ids") List<Long> ids) {
        ordOrderService.deleteOrdOrderListByIds(ids);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得订单主表")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('order:ord-order:query')")
    public CommonResult<OrdOrderRespVO> getOrdOrder(@RequestParam("id") Long id) {
        OrdOrderDO ordOrder = ordOrderService.getOrdOrder(id);
        return success(BeanUtils.toBean(ordOrder, OrdOrderRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得订单主表分页")
    @PreAuthorize("@ss.hasPermission('order:ord-order:query')")
    public CommonResult<PageResult<OrdOrderRespVO>> getOrdOrderPage(@Valid OrdOrderPageReqVO pageReqVO) {
        PageResult<OrdOrderDO> pageResult = ordOrderService.getOrdOrderPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, OrdOrderRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出订单主表 Excel")
    @PreAuthorize("@ss.hasPermission('order:ord-order:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportOrdOrderExcel(@Valid OrdOrderPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<OrdOrderDO> list = ordOrderService.getOrdOrderPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "订单主表.xls", "数据", OrdOrderRespVO.class,
                        BeanUtils.toBean(list, OrdOrderRespVO.class));
    }

}