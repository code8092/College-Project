package com.rooms.RoomKhojo.repository;

import com.rooms.RoomKhojo.Entity.ResidentialProperty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ResidentialPropertyRepository extends JpaRepository<ResidentialProperty, Long> {
    // Custom query to search by Location fields (State, City, Zip Code)
    // Custom query to search by Location fields (State and City)
    @Query("SELECT p FROM ResidentialProperty p WHERE " +
            "(:state IS NULL OR p.location.state LIKE %:state%) AND " +
            "(:city IS NULL OR p.location.city LIKE %:city%)")
    List<ResidentialProperty> searchByLocation(String state, String city);


    List<ResidentialProperty> findByOwnerId(Long ownerId);
}
