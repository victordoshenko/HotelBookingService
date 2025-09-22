package com.hotelbooking.repository;

import com.hotelbooking.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long>, JpaSpecificationExecutor<Room> {

    @Query("SELECT r FROM Room r WHERE r.hotel.id = :hotelId")
    List<Room> findByHotelId(@Param("hotelId") Long hotelId);

    @Query("SELECT r FROM Room r WHERE r.roomNumber = :roomNumber")
    Room findByRoomNumber(@Param("roomNumber") String roomNumber);

    @Query("SELECT r FROM Room r WHERE r.hotel.id = :hotelId AND " +
           "NOT EXISTS (SELECT b FROM Booking b WHERE b.room = r AND " +
           "((b.checkInDate <= :checkInDate AND b.checkOutDate > :checkInDate) OR " +
           "(b.checkInDate < :checkOutDate AND b.checkOutDate >= :checkOutDate) OR " +
           "(b.checkInDate >= :checkInDate AND b.checkOutDate <= :checkOutDate)))")
    List<Room> findAvailableRooms(@Param("hotelId") Long hotelId, 
                                 @Param("checkInDate") LocalDate checkInDate, 
                                 @Param("checkOutDate") LocalDate checkOutDate);
}
