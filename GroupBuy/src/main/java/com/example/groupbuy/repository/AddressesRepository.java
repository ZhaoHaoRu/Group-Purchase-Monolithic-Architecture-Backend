package com.example.groupbuy.repository;

import com.example.groupbuy.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressesRepository extends JpaRepository<Address,Integer>, JpaSpecificationExecutor<Address> {

    Address findByAddressId(Integer AddressId);

}
