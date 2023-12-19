package com.veekeshsingh.WanderWaltz.service;

import com.veekeshsingh.WanderWaltz.model.Room;
import com.veekeshsingh.WanderWaltz.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.rowset.serial.SerialBlob;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.SQLException;

@Service
@RequiredArgsConstructor
public class RoomServiceImpl implements IRoomService{

    private final RoomRepository roomRepository;

    //Adding new room to database
    @Override
    public Room addNewRoom(MultipartFile photo, String roomType, BigDecimal roomPrice) throws SQLException, IOException {
        Room room = new Room();
        room.setRoomType(roomType);
        room.setRoomPrice(roomPrice);
        if (!photo.isEmpty()){
            byte[] photoBytes = photo.getBytes();
            /*
            Converting byte to blob as photo was a blob type in Room class
             */
            Blob photoBlob = new SerialBlob(photoBytes);
            room.setPhoto(photoBlob);
        }
        return roomRepository.save(room);
    }
}
