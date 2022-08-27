package com.system.crm.dto.dashboard;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DashboardResponse {
    private int clientCount;
    private int clientFixedCount;
    private int clientUnfixedCount;
    private int adminCount;
}
