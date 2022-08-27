package com.system.crm.api;

import com.system.crm.domain.entity.Address;
import com.system.crm.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
@Validated
@RestController
@RequestMapping("api/city")
public class AddressController {

    private AddressService addressService;

    @Autowired
    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }

    @PreAuthorize("hasAuthority('super_admin')")
    @PutMapping(value = "/update",produces = {MediaType.APPLICATION_JSON_VALUE},consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> updateCity(@Valid @RequestBody List<Address> cityList){
        List<Address> updatedCity = addressService.updateCity(cityList);
        return new ResponseEntity<>(updatedCity, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('super_admin')")
    @GetMapping(value = "all",produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> getDefaultFalseCities(){
        return new ResponseEntity<>(addressService.allDefaultFalseCities(),HttpStatus.OK);
    }


    @PreAuthorize("hasAnyAuthority('super_admin','admin')")
    @GetMapping(value = "all-available", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<List<Address>> allCities(){
        List<Address> cityList = addressService.getAllCities();
        return new ResponseEntity<>(cityList,HttpStatus.OK);
    }

    @PreAuthorize("hasAnyAuthority('super_admin','admin')")
    @GetMapping(value = "find", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<List<Address>> findCities(@RequestParam("address") String address){
        List<Address> cityList = addressService.findAddresses(address);
        return new ResponseEntity<>(cityList,HttpStatus.OK);
    }

}
