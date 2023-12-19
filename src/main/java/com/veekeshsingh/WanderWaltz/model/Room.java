package com.veekeshsingh.WanderWaltz.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.RandomStringUtils;

import java.math.BigDecimal;
import java.sql.Blob;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Entity
@Getter
@Setter
@AllArgsConstructor
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;  // roomId

    @Column(name = "room_Type")
    private String roomType; // roomType

    @Column(name = "roomPrice")
    private BigDecimal roomPrice; //priceOfRoom

    @Column(name = "booked")
    private boolean isBooked = false; //is available or not

    @OneToMany(mappedBy="room", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<BookedRoom> bookings; //History of room booking

    @Lob
    private Blob photo;
    public Room() {
        this.bookings = new ArrayList<>();
        /*
        Purpose of this construct is that when the new room os been is added to database
        at that initial stage the room had not been booked before , if we tend to query
        all room at that moment we are going to run into null pointer exception if we initialise
        this one here as an empty array when the room is created, i.e. at initial stage bookings
        is going to hold an empty Arraylist.
        */
    }

    public void addBooking(BookedRoom booking){
        if (bookings == null){
            /*
              bookings = new ArrayList<>() to avoid null
              point exception.
             */
            bookings = new ArrayList<>();
        }
         /*
          Interlocking the room from both sides
         */
        bookings.add(booking);
        booking.setRoom(this);
        isBooked = true;
        String bookingCode = RandomStringUtils.randomNumeric(10);
        booking.setBookingConfirmationCode(bookingCode);
    }
}
