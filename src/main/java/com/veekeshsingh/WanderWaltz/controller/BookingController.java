package com.veekeshsingh.WanderWaltz.controller;

import com.veekeshsingh.WanderWaltz.exception.InvalidBookingRequestException;
import com.veekeshsingh.WanderWaltz.exception.ResourceNotFoundException;
import com.veekeshsingh.WanderWaltz.model.BookedRoom;
import com.veekeshsingh.WanderWaltz.model.Room;
import com.veekeshsingh.WanderWaltz.response.BookingResponse;
import com.veekeshsingh.WanderWaltz.response.RoomResponse;
import com.veekeshsingh.WanderWaltz.service.IBookingService;
import com.veekeshsingh.WanderWaltz.service.IRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/bookings")
public class BookingController {

    private final IBookingService bookingService;
    private final IRoomService roomService;

    // Getting all bookings and details
    @GetMapping("/all-bookings")
    public ResponseEntity<List<BookingResponse>>getAllBookings(){
        List<BookedRoom> bookings = bookingService.getAllBookings();
        List<BookingResponse> bookingResponses = new ArrayList<>();

        for (BookedRoom booking : bookings){
            BookingResponse bookingResponse = getBookingResponse(booking);
            bookingResponses.add(bookingResponse);
        }
        return ResponseEntity.ok(bookingResponses);
    }

    private BookingResponse getBookingResponse(BookedRoom booking) {
        Room theRoom = roomService.getRoomById(booking.getRoom().getId()).get();
        RoomResponse room = new RoomResponse(theRoom.getId(),
                                             theRoom.getRoomType(),
                                             theRoom.getRoomPrice());
        return new BookingResponse(booking.getBookingId(), booking.getCheckInDate(), booking.getCheckOutDate(),
                                    booking.getGuestFullName(), booking.getGuestEmail(), booking.getNumOfAdults(),
                                    booking.getNumOfChildren(), booking.getTotalNumOfGuest(), booking.getBookingConfirmationCode(), room);
    }

    // Getting the booked room by confirmation code
    @GetMapping("/confirmation/{confirmationCode}")
    public ResponseEntity<?> getBookingByConfirmationCode(@PathVariable String confirmationCode){
        try{
            BookedRoom booking = bookingService.findByBookingConfirmationCode(confirmationCode);
            BookingResponse bookingResponse = getBookingResponse(booking);
            return ResponseEntity.ok(bookingResponse);
        }catch (ResourceNotFoundException ex){
           return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
    }

    //Saving new booking to the database
    @PostMapping("/room/{roomId}/booking")
    public ResponseEntity<?> saveBooking(@PathVariable Long roomId, @RequestBody BookedRoom bookingRequest){
        try{
            String confirmationCode = bookingService.saveBooking(roomId, bookingRequest);
            return ResponseEntity.ok("Room Booked Successfully. Your Booking Confirmation Code Is : " + confirmationCode);
        } catch (InvalidBookingRequestException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/booking/{bookingId}/delete")
    public void cancelBooking(@PathVariable Long bookingId){
        bookingService.cancelBooking(bookingId);
    }
}
