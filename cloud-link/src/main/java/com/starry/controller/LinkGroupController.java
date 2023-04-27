package com.starry.controller;

import com.starry.controller.request.LinkGroupAddRequest;
import com.starry.controller.request.LinkGroupUpdateRequest;
import com.starry.enums.BizCodeEnum;
import com.starry.service.LinkGroupService;
import com.starry.utils.JsonData;
import com.starry.vo.LinkGroupVO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/group/v1")
@RequiredArgsConstructor
public class LinkGroupController {

    private final LinkGroupService linkGroupService;

    /**
     * 创建分组
     *
     * @param addRequest
     * @return
     */
    @PostMapping("/add")
    public JsonData add(@RequestBody LinkGroupAddRequest addRequest) {

        int rows = linkGroupService.add(addRequest);

        return rows == 1 ? JsonData.buildSuccess() : JsonData.buildResult(BizCodeEnum.GROUP_ADD_FAIL);

    }


    /**
     * 根据id删除分组
     *
     * @param groupId
     * @return
     */
    @DeleteMapping("/del/{group_id}")
    public JsonData del(@PathVariable("group_id") Long groupId) {

        int rows = linkGroupService.del(groupId);
        return rows == 1 ? JsonData.buildSuccess() : JsonData.buildResult(BizCodeEnum.GROUP_NOT_EXIST);

    }


    /**
     * 根据id找详情
     *
     * @param groupId
     * @return
     */
    @GetMapping("detail/{group_id}")
    public JsonData detail(@PathVariable("group_id") Long groupId) {

        LinkGroupVO linkGroupVO = linkGroupService.detail(groupId);
        return JsonData.buildSuccess(linkGroupVO);

    }


    /**
     * 列出用户全部分组
     *
     * @return
     */
    @GetMapping("list")
    public JsonData findUserAllLinkGroup() {

        List<LinkGroupVO> list = linkGroupService.listAllGroup();

        return JsonData.buildSuccess(list);

    }


    /**
     * 列出用户全部分组
     *
     * @return
     */
    @PutMapping("update")
    public JsonData update(@RequestBody LinkGroupUpdateRequest request) {
        int rows = linkGroupService.updateById(request);
        return rows == 1 ? JsonData.buildSuccess() : JsonData.buildResult(BizCodeEnum.GROUP_OPER_FAIL);

    }


}