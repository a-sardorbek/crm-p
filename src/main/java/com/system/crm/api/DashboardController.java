package com.system.crm.api;

import com.system.crm.dto.dashboard.DashboardResponse;
import com.system.crm.service.DashboardService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequestMapping("api/dashboard")
public class DashboardController {
    private DashboardService dashboardService;

    public DashboardController(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    @PreAuthorize("hasAnyAuthority('super_admin','admin')")
    @GetMapping
    public DashboardResponse getDashboardData(@RequestParam(required = false) boolean day,
                                              @RequestParam(required = false) boolean week,
                                              @RequestParam(required = false) boolean month,
                                              @RequestParam(required = false) String start_date,
                                              @RequestParam(required = false) String end_date) {


        LocalDate now = LocalDate.now();
        LocalDate yesterday = now.minusDays(1);
        LocalDate lastWeek = now.minusWeeks(1);
        LocalDate lastMonth = now.minusMonths(1);

        if(day) return dashboardService.getDashboardAnalysis(yesterday.toString(), now.toString());
        if(week) return dashboardService.getDashboardAnalysis(lastWeek.toString(), now.toString());
        if(month) return dashboardService.getDashboardAnalysis(lastMonth.toString(), now.toString());
        return dashboardService.getDashboardAnalysis(start_date,end_date);
    }

}


















