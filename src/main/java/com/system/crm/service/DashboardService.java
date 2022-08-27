package com.system.crm.service;

import com.system.crm.dto.dashboard.DashboardResponse;

public interface DashboardService {
     DashboardResponse getDashboardAnalysis(String start_date, String end_date);
}
