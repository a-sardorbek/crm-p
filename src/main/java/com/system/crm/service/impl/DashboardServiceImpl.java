package com.system.crm.service.impl;

import com.system.crm.dto.dashboard.DashboardResponse;
import com.system.crm.repository.ClientRepository;
import com.system.crm.repository.DashboardClientQuery;
import com.system.crm.repository.SystemUserRepository;
import com.system.crm.service.DashboardService;
import org.springframework.stereotype.Service;

@Service
public class DashboardServiceImpl implements DashboardService {

    private ClientRepository clientRepository;
    private SystemUserRepository systemUserRepository;

    public DashboardServiceImpl(ClientRepository clientRepository, SystemUserRepository systemUserRepository) {
        this.clientRepository = clientRepository;
        this.systemUserRepository = systemUserRepository;
    }

    @Override
    public DashboardResponse getDashboardAnalysis(String start_date, String end_date) {
        if(start_date==null||end_date==null) {
            start_date = clientRepository.startDateQuery();
            end_date = clientRepository.endDateQuery();
        }
        DashboardResponse dashboardResponse = new DashboardResponse();
        dashboardResponse.setAdminCount(systemUserRepository.getSystemUserCount());
        DashboardClientQuery clientAnalysis = clientRepository.getClientAnalysis(start_date, end_date);
        dashboardResponse.setClientCount(clientAnalysis.getCount());
        dashboardResponse.setClientFixedCount(clientAnalysis.getFixed());
        dashboardResponse.setClientUnfixedCount(clientAnalysis.getUnfixed());
        return dashboardResponse;
    }
}
