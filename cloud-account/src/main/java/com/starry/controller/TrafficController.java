package com.starry.controller;

import com.starry.controller.request.TrafficPageRequest;
import com.starry.controller.request.UseTrafficRequest;
import com.starry.service.TrafficService;
import com.starry.utils.JsonData;
import com.starry.vo.TrafficVO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/traffic/v1")
public class TrafficController {

    private final TrafficService trafficService;


    /**
     * 使用流量包API
     *
     * @param useTrafficRequest
     * @param request
     * @return
     */
    @PostMapping("reduce")
    public JsonData useTraffic(@RequestBody UseTrafficRequest useTrafficRequest, HttpServletRequest request){
        return trafficService.reduce(useTrafficRequest);
    }



    /**
     * 分页查询流量包列表，查看可用的流量包
     * @param request
     * @return
     */
    @RequestMapping("page")
    public JsonData pageAvailable(@RequestBody TrafficPageRequest request){

        Map<String,Object> pageMap = trafficService.pageAvailable(request);

        return JsonData.buildSuccess(pageMap);

    }


    /**
     * 查找某个流量包详情
     * @param trafficId
     * @return
     */
    @GetMapping("/detail/{trafficId}")
    public JsonData detail(@PathVariable("trafficId") long trafficId){

        TrafficVO trafficVO = trafficService.detail(trafficId);

        return JsonData.buildSuccess(trafficVO);
    }




}