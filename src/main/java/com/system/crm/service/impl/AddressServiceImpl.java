package com.system.crm.service.impl;

import com.system.crm.domain.entity.Address;
import com.system.crm.domain.entity.Address;
import com.system.crm.exception.custom.CustomNotFoundException;
import com.system.crm.exception.custom.SuccessResponse;
import com.system.crm.repository.AddressRepository;
import com.system.crm.service.AddressService;
import com.system.crm.utils.ServiceUtils;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AddressServiceImpl implements AddressService {

    private AddressRepository addressRepository;

    public AddressServiceImpl(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    @Override
    public void deleteCity(Long id) {
        Optional<Address> city = addressRepository.findById(id);
            if(city.isPresent()){
                addressRepository.deleteById(id);
                throw new SuccessResponse("City '"+city.get().getAddressName()+"' has been deleted!");
            }else{
                throw new CustomNotFoundException("City not found by id: "+id);
        }
    }

    @Override
    public List<Address> allDefaultFalseCities() {
        return addressRepository.findAll();
    }

    @Override
    public List<Address> findAddresses(String address) {
        return addressRepository.findByAddressNameLike(address);
    }

    @Override
    public void createCity(Address city) {
        Address savedCity = addressRepository.save(city);
      if(savedCity!=null){
          throw new SuccessResponse("City created");
      }
    }

    @Override
    public List<Address> updateCity(List<Address> cityList) {
        return addressRepository.saveAll(cityList);
    }

    @Override
    public Optional<Address> getCityById(String id) {
        Optional<Address> city;
        if(ServiceUtils.checkIsNumeric(id)) {
            city = addressRepository.findById(Long.parseLong(id));
            if(city.isPresent()){
                return city;
            }
        }
        throw new CustomNotFoundException("City not found by id: "+id);
    }

    @Override
    public List<Address> getAllCities() {
        return addressRepository.findAvailableAddresses();
    }

    @PostConstruct
    public void insertCities(){
        List<Address> cityList = new ArrayList<>();
        cityList.add(new Address("Yuqori Sebzor",false));
        cityList.add(new Address("O'rta sebzor",false));
        cityList.add(new Address("Yangi Sebzor",false));
        cityList.add(new Address("Yakkasaroy",false));
        cityList.add(new Address("Yunusobod",false));
        cityList.add(new Address("Chilonzor",false));
        cityList.add(new Address("Bektemir",false));
        cityList.add(new Address("Mirobod",false));
        cityList.add(new Address("Mirzo Ulugbek",false));
        cityList.add(new Address("Olmazor",false));
        cityList.add(new Address("Sergeli",false));
        cityList.add(new Address("Shayhontohur",false));
        cityList.add(new Address("Uchtepa",false));
        boolean checkCitiesExist = addressRepository.checkExists();
        if(!checkCitiesExist){
            addressRepository.saveAll(cityList);
        }
    }
}
