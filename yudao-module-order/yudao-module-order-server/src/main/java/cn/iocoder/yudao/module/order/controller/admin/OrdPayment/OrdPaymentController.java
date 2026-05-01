package cn.iocoder.yudao.module.order.controller.admin.OrdPayment;

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

import cn.iocoder.yudao.module.order.controller.admin.OrdPayment.vo.*;
import cn.iocoder.yudao.module.order.dal.dataobject.OrdPayment.OrdPaymentDO;
import cn.iocoder.yudao.module.order.service.OrdPayment.OrdPaymentService;

@Tag(name = "管理后台 - 支付流水")
@RestController
@RequestMapping("/order/ord-payment")
@Validated
public class OrdPaymentController {

    @Resource
    private OrdPaymentService ordPaymentService;

    @PostMapping("/create")
    @Operation(summary = "创建支付流水")
    @PreAuthorize("@ss.hasPermission('order:ord-payment:create')")
    public CommonResult<Long> createOrdPayment(@Valid @RequestBody OrdPaymentSaveReqVO createReqVO) {
        return success(ordPaymentService.createOrdPayment(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新支付流水")
    @PreAuthorize("@ss.hasPermission('order:ord-payment:update')")
    public CommonResult<Boolean> updateOrdPayment(@Valid @RequestBody OrdPaymentSaveReqVO updateReqVO) {
        ordPaymentService.updateOrdPayment(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除支付流水")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('order:ord-payment:delete')")
    public CommonResult<Boolean> deleteOrdPayment(@RequestParam("id") Long id) {
        ordPaymentService.deleteOrdPayment(id);
        return success(true);
    }

    @DeleteMapping("/delete-list")
    @Parameter(name = "ids", description = "编号", required = true)
    @Operation(summary = "批量删除支付流水")
                @PreAuthorize("@ss.hasPermission('order:ord-payment:delete')")
    public CommonResult<Boolean> deleteOrdPaymentList(@RequestParam("ids") List<Long> ids) {
        ordPaymentService.deleteOrdPaymentListByIds(ids);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得支付流水")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('order:ord-payment:query')")
    public CommonResult<OrdPaymentRespVO> getOrdPayment(@RequestParam("id") Long id) {
        OrdPaymentDO ordPayment = ordPaymentService.getOrdPayment(id);
        return success(BeanUtils.toBean(ordPayment, OrdPaymentRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得支付流水分页")
    @PreAuthorize("@ss.hasPermission('order:ord-payment:query')")
    public CommonResult<PageResult<OrdPaymentRespVO>> getOrdPaymentPage(@Valid OrdPaymentPageReqVO pageReqVO) {
        PageResult<OrdPaymentDO> pageResult = ordPaymentService.getOrdPaymentPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, OrdPaymentRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出支付流水 Excel")
    @PreAuthorize("@ss.hasPermission('order:ord-payment:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportOrdPaymentExcel(@Valid OrdPaymentPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<OrdPaymentDO> list = ordPaymentService.getOrdPaymentPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "支付流水.xls", "数据", OrdPaymentRespVO.class,
                        BeanUtils.toBean(list, OrdPaymentRespVO.class));
    }

}