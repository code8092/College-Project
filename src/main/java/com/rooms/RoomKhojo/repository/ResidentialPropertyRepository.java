package com.rooms.RoomKhojo.repository;

import com.rooms.RoomKhojo.Entity.ResidentialProperty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ResidentialPropertyRepository extends JpaRepository<ResidentialProperty, Long> {
    @Query("SELECT p FROM ResidentialProperty p " +
            "WHERE " +
            "(:keyword IS NULL OR " +
            "(LOWER(p.location.state) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "OR LOWER(p.location.city) LIKE LOWER(CONCAT('%', :keyword, '%'))))")
    List<ResidentialProperty> searchByKeyword(String keyword);

    List<ResidentialProperty> findByOwnerId(Long ownerId);
}
