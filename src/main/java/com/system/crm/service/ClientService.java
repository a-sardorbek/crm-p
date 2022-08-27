package com.system.crm.service;

import com.system.crm.domain.entity.Client;
import com.system.crm.dto.client.ClientResponse;
import com.system.crm.dto.client.ClientDto;
import com.system.crm.dto.client.ClientUpdateDto;
import com.system.crm.dto.client.ClientUpdateDtoForAdmin;
import com.system.crm.dto.client.UpdateClientStatus;

import java.util.List;

public interface ClientService {

    void createClient(ClientDto clientDto);
    ClientResponse getClientById(String id);
    List<ClientResponse> allClients(String pageNum, String pageSize);

    void updateClientFromSuperAdmin(ClientUpdateDto clientDto);

    void deleteClient(String clientId, String userId, String token);

    List<ClientResponse> allClientsNoPage();

    List<ClientResponse> filterClientHouse(String houseNum, String flatNum,String regionId, String name);

    void updateClientFromSuperAndAdminStatus(UpdateClientStatus updateClientStatus);
}
