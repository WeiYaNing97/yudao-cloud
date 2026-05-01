package cn.iocoder.yudao.module.points.controller.admin.OrdItem;

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

import cn.iocoder.yudao.module.points.controller.admin.OrdItem.vo.*;
import cn.iocoder.yudao.module.points.dal.dataobject.OrdItem.OrdItemDO;
import cn.iocoder.yudao.module.points.service.OrdItem.OrdItemService;

@Tag(name = "管理后台 - 订单明细")
@RestController
@RequestMapping("/order/ord-item")
@Validated
public class OrdItemController {

    @Resource
    private OrdItemService ordItemService;

    @PostMapping("/create")
    @Operation(summary = "创建订单明细")
    @PreAuthorize("@ss.hasPermission('order:ord-item:create')")
    public CommonResult<Long> createOrdItem(@Valid @RequestBody OrdItemSaveReqVO createReqVO) {
        return success(ordItemService.createOrdItem(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新订单明细")
    @PreAuthorize("@ss.hasPermission('order:ord-item:update')")
    public CommonResult<Boolean> updateOrdItem(@Valid @RequestBody OrdItemSaveReqVO updateReqVO) {
        ordItemService.updateOrdItem(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除订单明细")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('order:ord-item:delete')")
    public CommonResult<Boolean> deleteOrdItem(@RequestParam("id") Long id) {
        ordItemService.deleteOrdItem(id);
        return success(true);
    }

    @DeleteMapping("/delete-list")
    @Parameter(name = "ids", description = "编号", required = true)
    @Operation(summary = "批量删除订单明细")
                @PreAuthorize("@ss.hasPermission('order:ord-item:delete')")
    public CommonResult<Boolean> deleteOrdItemList(@RequestParam("ids") List<Long> ids) {
        ordItemService.deleteOrdItemListByIds(ids);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得订单明细")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('order:ord-item:query')")
    public CommonResult<OrdItemRespVO> getOrdItem(@RequestParam("id") Long id) {
        OrdItemDO ordItem = ordItemService.getOrdItem(id);
        return success(BeanUtils.toBean(ordItem, OrdItemRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得订单明细分页")
    @PreAuthorize("@ss.hasPermission('order:ord-item:query')")
    public CommonResult<PageResult<OrdItemRespVO>> getOrdItemPage(@Valid OrdItemPageReqVO pageReqVO) {
        PageResult<OrdItemDO> pageResult = ordItemService.getOrdItemPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, OrdItemRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出订单明细 Excel")
    @PreAuthorize("@ss.hasPermission('order:ord-item:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportOrdItemExcel(@Valid OrdItemPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<OrdItemDO> list = ordItemService.getOrdItemPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "订单明细.xls", "数据", OrdItemRespVO.class,
                        BeanUtils.toBean(list, OrdItemRespVO.class));
    }

}