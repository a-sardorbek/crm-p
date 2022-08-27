package com.system.crm.api;

import com.system.crm.dto.client.ClientDto;
import com.system.crm.dto.client.ClientResponse;
import com.system.crm.dto.client.ClientUpdateDto;
import com.system.crm.dto.client.UpdateClientStatus;
import com.system.crm.service.ClientService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("api/client")
public class ClientController {

    private ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }


    @PreAuthorize("hasAnyAuthority('super_admin','admin')")
    @PutMapping(value = "/update",produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> updateClient(@Valid @RequestBody ClientUpdateDto clientDto){
        clientService.updateClientFromSuperAdmin(clientDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PreAuthorize("hasAnyAuthority('super_admin','admin')")
    @PutMapping(value = "/updateStatus",produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> updateClientStatus(@Valid @RequestBody UpdateClientStatus updateClientStatus){
        clientService.updateClientFromSuperAndAdminStatus(updateClientStatus);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('super_admin')")
    @DeleteMapping(value = "/delete",produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> deleteClient(@RequestParam("clientId") String clientId,
                                          @RequestParam("userId") String userId,
                                          @RequestHeader(name = "Authorization") String requestHeader){
        clientService.deleteClient(clientId,userId,requestHeader);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @PreAuthorize("hasAnyAuthority('admin','super_admin')")
    @PostMapping(produces = {MediaType.APPLICATION_JSON_VALUE},consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> newClient(@Valid @RequestBody ClientDto clientDto){
        clientService.createClient(clientDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PreAuthorize("hasAnyAuthority('admin','super_admin')")
    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> getClientByID(@RequestParam("clientId") String clientId){
        return new ResponseEntity<>(clientService.getClientById(clientId),HttpStatus.OK);
    }


    @PreAuthorize("hasAnyAuthority('admin','super_admin')")
    @GetMapping(value = "/filter",produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> filterDataui(@RequestParam("houseNum") String houseNum,
                                          @RequestParam("flatNum") String flatNum,
                                          @RequestParam("regionId") String regionId,
                                          @RequestParam("name") String name){
        List<ClientResponse> clientResponses = clientService.filterClientHouse(houseNum,flatNum,regionId,name);
        return new ResponseEntity<>(clientResponses,HttpStatus.OK);
    }


    @PreAuthorize("hasAnyAuthority('super_admin','admin')")
    @GetMapping(value = "/all-pageable", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> getClientsPaging(@RequestParam(value = "pageNum") String pageNum,
                                        @RequestParam(value = "pageSize") String pageSize){
        return new ResponseEntity<>(clientService.allClients(pageNum,pageSize),HttpStatus.OK);
    }


    @PreAuthorize("hasAnyAuthority('super_admin','admin')")
    @GetMapping(value = "/all", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> getClients(){
        return new ResponseEntity<>(clientService.allClientsNoPage(),HttpStatus.OK);
    }

}
