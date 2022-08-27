package com.system.crm.repository;

import com.system.crm.domain.entity.Client;
import com.system.crm.dto.client.ClientResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface ClientRepository extends PagingAndSortingRepository<Client, Long>, JpaRepository<Client,Long> {
    @Query(nativeQuery = true, value = "select count(*) count, " +
            "       (select count(*) from client c1 where status = true and (c1.created_date>=?1 and c1.created_date<=?2)) fixed, " +
            "       (select count(*) from client c2 where status = false and (c2.created_date>=?1 and c2.created_date<=?2)) unfixed " +
            "from client c where c.created_date>=?1 and c.created_date<=?2")
    public DashboardClientQuery getClientAnalysis(String start_date, String end_date);

    @Query(value = "select min(created_date) from client limit 1",nativeQuery = true)
    String startDateQuery();
    @Query(value = "select max(created_date) from client limit 1",nativeQuery = true)
    String endDateQuery();

    @Modifying
    @Transactional
    @Query(value = "update client c set c.first_name=?2, c.last_name=?3, c.phone_number=?4," +
            " c.problem=?5, c.status=?8, c.address_id=(select id from address where id=?6)," +
            " c.profession_id=(select id from responsible_profession where id=?7), c.house_num=?10, c.flat_num=?9 where c.id=?1",nativeQuery = true)
    void updateClient(Long clientId, String firstName, String lastName, int phone,
                      String problem, Long addressId, Long professionId, boolean status, String flatNum, String houseNum);
    @Modifying
    @Transactional
    @Query(value = "delete from client where id=?1",nativeQuery = true)
    void deleteByGivenId(Long id);

    @Query(value = "select exists(select c.profession_id from client c where c.profession_id=?1)",nativeQuery = true)
    int checkConnectedToProfession(Long id);

    @Query(value = "select * from client c where c.house_num like %?1%  and " +
            "c.flat_num like %?2% and c.first_name like %?4% and c.last_name like %?3%",nativeQuery = true)
    List<Client> findByHouseNumOrFlatNum(String houseNum, String flatNum,String regionId, String name);


    @Modifying
    @Transactional
    @Query(value = "update client c set c.status = ?2 where id=?1",nativeQuery = true)
    void updateClientStatus(Long clientId, boolean status);
}
