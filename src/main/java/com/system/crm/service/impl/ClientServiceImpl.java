package com.system.crm.service.impl;

import com.system.crm.constant.SecurityConstant;
import com.system.crm.domain.entity.Address;
import com.system.crm.domain.entity.Client;
import com.system.crm.domain.entity.ResponsibleProfession;
import com.system.crm.domain.entity.SystemUser;
import com.system.crm.dto.client.ClientResponse;
import com.system.crm.dto.client.ClientDto;
import com.system.crm.dto.client.ClientUpdateDto;
import com.system.crm.dto.client.ClientUpdateDtoForAdmin;
import com.system.crm.dto.client.UpdateClientStatus;
import com.system.crm.dto.userSystem.SystemUserInfoProfileDto;
import com.system.crm.exception.custom.CustomNotFoundException;
import com.system.crm.exception.custom.JwtAuthenticationException;
import com.system.crm.exception.custom.NotCorrectException;
import com.system.crm.exception.custom.SuccessResponse;
import com.system.crm.jwt.JwtTokenProvider;
import com.system.crm.repository.AddressRepository;
import com.system.crm.repository.ClientRepository;
import com.system.crm.repository.ResponsibleProfessionRepository;
import com.system.crm.repository.SystemUserRepository;
import com.system.crm.service.ClientService;
import com.system.crm.utils.ServiceUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ClientServiceImpl implements ClientService {

    private ClientRepository clientRepository;
    private SystemUserRepository systemUserRepository;
    private ResponsibleProfessionRepository responsibleProfessionRepository;
    private AddressRepository addressRepository;

    private JwtTokenProvider jwtTokenProvider;

    public ClientServiceImpl(ClientRepository clientRepository,
                             SystemUserRepository systemUserRepository,
                             ResponsibleProfessionRepository responsibleProfessionRepository,
                             AddressRepository addressRepository,
                             JwtTokenProvider jwtTokenProvider) {
        this.clientRepository = clientRepository;
        this.systemUserRepository = systemUserRepository;
        this.responsibleProfessionRepository = responsibleProfessionRepository;
        this.addressRepository = addressRepository;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public void createClient(ClientDto clientDto) {
        Optional<SystemUser> systemUser = systemUserRepository.findById(clientDto.getSystemUserId());
        Optional<ResponsibleProfession> responsibleProfession = responsibleProfessionRepository.findById(clientDto.getProfessionId());
        Optional<Address> foundCity = addressRepository.findById(clientDto.getAddressId());
        if(systemUser.isPresent() && responsibleProfession.isPresent() && foundCity.isPresent()) {
            Client client = new Client();
            client.setFirstName(clientDto.getFirstName());
            client.setLastName(clientDto.getLastName());
            client.setPhoneNumber(Integer.valueOf(clientDto.getPhoneNumber()));
            client.setProblem(clientDto.getProblem());
            client.setAddress(foundCity.get());
            client.setSystemUser(systemUser.get());
            client.setResponsibleProfession(responsibleProfession.get());
            client.setStatus(false);
            client.setCreated_date(LocalDate.now());
            client.setCreated_time(LocalTime.now(Clock.system(ZoneId.of("Asia/Tashkent"))));
            client.setHouseNum(clientDto.getHouseNum());
            client.setFlatNum(clientDto.getFlatNum());
            clientRepository.save(client);
            throw new SuccessResponse("Client added successfully!");
        }
       throw new CustomNotFoundException("Not found by given id!");
    }

    @Override
    public List<ClientResponse> allClients(String pageNum, String pageSize) {
        if(ServiceUtils.checkIsNumeric(pageNum) && ServiceUtils.checkIsNumeric(pageSize)) {
            int p = Integer.parseInt(pageNum);
            int s = Integer.parseInt(pageSize);
            if(p<0) p=1;
            if(s<0) s=10;
            Pageable paging = PageRequest.of(p, s, Sort.by("id").descending());
            return getListsToResponse(clientRepository.findAll(paging));
        }
            throw new NotCorrectException("Page number or size not correct, make sure they are not equal to zero!");
    }

    @Override
    public void updateClientFromSuperAdmin(ClientUpdateDto clientDto) {
        Optional<Client> client = clientRepository.findById(clientDto.getClientId());
        if(client.isPresent()) {
                clientRepository.updateClient(
                        clientDto.getClientId(),
                        clientDto.getFirstName(),
                        clientDto.getLastName(),
                        Integer.parseInt(clientDto.getPhoneNumber()),
                        clientDto.getProblem(),
                        clientDto.getAddressId(),
                        clientDto.getProfessionId(),
                        Boolean.parseBoolean(clientDto.getStatus()),
                        clientDto.getFlatNum(),
                        clientDto.getHouseNum()
                );
                throw new SuccessResponse("Client updated successfully");
            }
        throw new CustomNotFoundException("Client not found by given id");

    }

// only admins
//    @Override
//    public void updateClient(ClientUpdateDto clientDto) {
//        Optional<Client> client = clientRepository.findById(clientDto.getClientId());
//        if(client.isPresent()) {
//            Long idUserInClient =client.get().getSystemUser().getId();
//            Long idUserFromBody = clientDto.getSystemUserId();
//            if (idUserInClient == idUserFromBody) {
//                clientRepository.updateClient(
//                        clientDto.getClientId(),
//                        clientDto.getFirstName(),
//                        clientDto.getLastName(),
//                        Integer.parseInt(clientDto.getPhoneNumber()),
//                        clientDto.getAddress(),
//                        clientDto.getProblem(),
//                        clientDto.getCityId(),
//                        clientDto.getSystemUserId(),
//                        clientDto.getProfessionId(),
//                        Boolean.parseBoolean(clientDto.getStatus())
//                );
//                throw new SuccessResponse("Client updated successfully");
//            }
//            throw new NotCorrectException("You are not allowed to update this info");
//        }
//        throw new CustomNotFoundException("Client not found by given id");
//
//    }


    @Override
    public void deleteClient(String clientId, String userId, String token) {
        try {
            if (ServiceUtils.checkIsNumeric(clientId) && ServiceUtils.checkIsNumeric(userId)) {
                Optional<Client> client = clientRepository.findById(Long.parseLong(clientId));
                String phoneSuperAdmin = getPhoneFromCheckedToken(token);
                int checkPhoneBelongToSuperAdmin = systemUserRepository.checkPhone(phoneSuperAdmin);
                if (client.isPresent()) {
                    Long userIdFromClient = client.get().getSystemUser().getId();
                    if (userIdFromClient == Long.parseLong(userId) || checkPhoneBelongToSuperAdmin == 1) {
                        clientRepository.deleteByGivenId(client.get().getId());
                        throw new SuccessResponse("Successfully deleted");
                    }
                    throw new NotCorrectException("You are not allowed to delete this client");
                }
                throw new CustomNotFoundException("Client not found by id: " + clientId);
            }
            throw new NotCorrectException("Client id or User id is not correct");
        }catch (NumberFormatException e){
            throw new NotCorrectException("Userid or ClientId cannot be empty or null");
        }
    }

    @Override
    public List<ClientResponse> allClientsNoPage() {
        List<Client> clients = clientRepository.findAll();
        List<ClientResponse> clientResponses = new ArrayList<>();
        for(Client client: clients){
            ClientResponse clientResponse = new ClientResponse(
                    client.getId(),
                    client.getFirstName(),
                    client.getLastName(),
                    client.getPhoneNumber(),
                    client.getAddress(),
                    client.getProblem(),
                    client.getResponsibleProfession(),
                    new SystemUserInfoProfileDto(client.getSystemUser().getId(),
                            client.getSystemUser().getFirstName(),
                            client.getSystemUser().getLastName(),
                            client.getSystemUser().getPhoneNumber()),
                    client.isStatus(),
                    client.getCreated_date(),
                    client.getCreated_time(),
                    client.getHouseNum(),
                    client.getFlatNum()
            );
            clientResponses.add(clientResponse);
        }

        return clientResponses;
    }

    @Override
    public List<ClientResponse> filterClientHouse(String houseNum, String flatNum,String regionId, String name) {


        List<Client> clients = clientRepository.findByHouseNumOrFlatNum(houseNum,flatNum,regionId,name);
        List<ClientResponse> clientResponses = new ArrayList<>();
        for(Client client: clients){
            ClientResponse clientResponse = new ClientResponse(
                    client.getId(),
                    client.getFirstName(),
                    client.getLastName(),
                    client.getPhoneNumber(),
                    client.getAddress(),
                    client.getProblem(),
                    client.getResponsibleProfession(),
                    new SystemUserInfoProfileDto(client.getSystemUser().getId(),
                            client.getSystemUser().getFirstName(),
                            client.getSystemUser().getLastName(),
                            client.getSystemUser().getPhoneNumber()),
                    client.isStatus(),
                    client.getCreated_date(),
                    client.getCreated_time(),
                    client.getHouseNum(),
                    client.getFlatNum()
            );
            clientResponses.add(clientResponse);
        }
        return clientResponses;
    }

    @Override
    public void updateClientFromSuperAndAdminStatus(UpdateClientStatus updateClientStatus) {
        if(!clientRepository.findById(updateClientStatus.getId()).isPresent()) {
            throw new CustomNotFoundException("Client not found by id: "+updateClientStatus.getId());
        }
        clientRepository.updateClientStatus(
                updateClientStatus.getId(), Boolean.parseBoolean(updateClientStatus.getStatus()));

    }

    public String getPhoneFromCheckedToken(String token){
        String tokenWithoutBearer = token.substring(SecurityConstant.TOKEN_PREFIX.length());
        String userNameSubject = jwtTokenProvider.getSubjectFromToken(tokenWithoutBearer);
        if(jwtTokenProvider.isValidToken(userNameSubject,tokenWithoutBearer)){
            return userNameSubject;
        }
        throw new JwtAuthenticationException("You need to log in to access this page");
    }

    private List<ClientResponse> getListsToResponse(Page<Client> pagedResult) {
        List<ClientResponse> clientResponseList = new ArrayList<>();
        for (Client client:pagedResult ) {
            clientResponseList.add(new ClientResponse(
                    client.getId(),
                    client.getFirstName(),
                    client.getLastName(),
                    client.getPhoneNumber(),
                    client.getAddress(),
                    client.getProblem(),
                    client.getResponsibleProfession(),
                    new SystemUserInfoProfileDto(
                            client.getSystemUser().getId(),
                            client.getSystemUser().getFirstName(),
                            client.getSystemUser().getLastName(),
                            client.getSystemUser().getPhoneNumber()
                    ),
                    client.isStatus(),
                    client.getCreated_date(),
                    client.getCreated_time(),
                    client.getHouseNum(),
                    client.getFlatNum()
            ));
        }
        return clientResponseList;

    }



    @Override
    public ClientResponse getClientById(String id) {
        Optional<Client> client;
        if (ServiceUtils.checkIsNumeric(id)){
            client = clientRepository.findById(Long.parseLong(id));
            if(client.isPresent()){
                return new ClientResponse(
                        client.get().getId(),
                        client.get().getFirstName(),
                        client.get().getLastName(),
                        client.get().getPhoneNumber(),
                        client.get().getAddress(),
                        client.get().getProblem(),
                        client.get().getResponsibleProfession(),
                        new SystemUserInfoProfileDto(
                                client.get().getSystemUser().getId(),
                                client.get().getSystemUser().getFirstName(),
                                client.get().getSystemUser().getLastName(),
                                client.get().getSystemUser().getPhoneNumber()
                        ),
                        client.get().isStatus(),
                        client.get().getCreated_date(),
                        client.get().getCreated_time(),
                        client.get().getHouseNum(),
                        client.get().getFlatNum()
                );
            }
        }
        throw new CustomNotFoundException("Client not found by id: "+id);
    }
}
