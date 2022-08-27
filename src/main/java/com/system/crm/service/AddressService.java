package com.system.crm.service;

import com.system.crm.domain.entity.Address;

import java.util.List;
import java.util.Optional;

public interface AddressService {

    void createCity(Address city);
    List<Address> updateCity(List<Address> city);
    Optional<Address> getCityById(String id);
    List<Address> getAllCities();
    void deleteCity(Long id);

    List<Address> allDefaultFalseCities();

    List<Address> findAddresses(String address);
}
