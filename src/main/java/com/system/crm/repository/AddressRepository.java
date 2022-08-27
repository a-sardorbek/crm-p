package com.system.crm.repository;

import com.system.crm.domain.entity.Address;
import com.system.crm.domain.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AddressRepository extends JpaRepository<Address,Long> {

    @Query(value = "SELECT IF(COUNT(*)=12, 'true', 'false') as countAddress FROM address",nativeQuery = true)
    boolean checkExists();

    @Query(value = "SELECT * FROM address c where c.status=true ",nativeQuery = true)
    List<Address> findAvailableAddresses();

    @Query(value = "SELECT * FROM address c where c.address_name like %?1%",nativeQuery = true)
    List<Address> findByAddressNameLike(String address);
}
