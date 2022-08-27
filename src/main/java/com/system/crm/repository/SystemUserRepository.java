package com.system.crm.repository;

import com.system.crm.domain.entity.SystemUser;
import com.system.crm.dto.userSystem.SystemUserInfoDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface SystemUserRepository extends JpaRepository<SystemUser,Long> {

    @Modifying
    @Transactional
    @Query(value = "update system_user s set s.first_name=?2, s.last_name=?3, s.phone_number=?4" +
            " where s.id = ?1",nativeQuery = true)
    int updateSystemUser(Long id, String firstName, String lastName, String phoneNumber);

    @Modifying
    @Transactional
    @Query(value = "update system_user s set s.password=?2 where s.phone_number = ?1",nativeQuery = true)
    int updatePassword(String phone, String newPassword);

    @Query(value = "select new com.system.crm.dto.userSystem.SystemUserInfoDto(s.id,s.firstName,s.lastName,s.phoneNumber, s.localDate, s.isActive) from SystemUser s where s.isDeleted=false")
    List<SystemUserInfoDto> findUsers();

    @Query(value = "select * from system_user s where s.phone_number=?1",nativeQuery = true)
    SystemUser findByPhoneNumber(String phone);

    @Query(value = "select exists(select s.phone_number from system_user s where s.phone_number=?1)",nativeQuery = true)
    int checkUserExists(String phoneNumber);

    @Query(value = "select exists(select s.id from system_user s where s.id=?1)",nativeQuery = true)
    int checkUserExistsById(Long id);

    @Modifying
    @Transactional
    @Query(value = "update system_user s set s.is_active=?1 where s.id = ?2",nativeQuery = true)
    void updateActivenessOfUser(boolean parseBoolean, Long id);
    @Query(nativeQuery = true, value="select count(*) from system_user")
    int getSystemUserCount();

    @Query(value = "select (select role from system_user where phone_number=?1)= 'super_admin' isSuperAdmin",nativeQuery = true)
    int checkPhone(String phoneSuperAdmin);

    @Query(value = "SELECT IF(COUNT(*)=0, 'true', 'false') as countUser FROM system_user",nativeQuery = true)
    boolean checkUserCountIsZero();

    @Modifying
    @Transactional
    @Query(value = "update system_user s set s.is_deleted=true where s.id = ?1",nativeQuery = true)
    void deleteByIdMakeFalse(long parseLong);
}
