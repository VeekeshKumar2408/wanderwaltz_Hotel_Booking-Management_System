package com.veekeshsingh.WanderWaltz.controller;

import com.veekeshsingh.WanderWaltz.model.Room;
import com.veekeshsingh.WanderWaltz.response.RoomResponse;
import com.veekeshsingh.WanderWaltz.service.IRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.FileNameMap;
import java.sql.SQLException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/rooms")
public class RoomController {

    /*
    "final" because we are gonna inject it in out
    constructor injection.
     */
    private final IRoomService roomService;

    @PostMapping("/add/new-room")
    public ResponseEntity<RoomResponse> addNewRoom(
           @RequestParam("photo") MultipartFile photo,
           @RequestParam("roomType") String roomType,
           @RequestParam("roomPrice") BigDecimal roomPrice) throws SQLException, IOException
    {
        Room savedRoom = roomService.addNewRoom(photo,roomType,roomPrice);
        /*
        Below in the roomResponse we are getting the response back
        from the savedRoom to show it on the screen.
         */
        RoomResponse roomResponse = new RoomResponse(savedRoom.getId(),savedRoom.getRoomType(),savedRoom.getRoomPrice());
        return ResponseEntity.ok(roomResponse);
    }
}
