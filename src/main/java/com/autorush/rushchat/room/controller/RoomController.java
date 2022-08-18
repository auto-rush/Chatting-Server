package com.autorush.rushchat.room.controller;

import com.autorush.rushchat.common.response.ApiResponse;
import com.autorush.rushchat.common.response.EmptyJsonResponse;
import com.autorush.rushchat.room.dto.CreateRoomDto;
import com.autorush.rushchat.room.dto.GetAllRoomDto;
import com.autorush.rushchat.room.dto.InOutRoomDto;
import com.autorush.rushchat.room.dto.UpdateRoomDto;
import com.autorush.rushchat.room.entity.Room;
import com.autorush.rushchat.room.service.RoomService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/room")
public class RoomController {
    private final RoomService roomService;

    @PostMapping("")
    public ApiResponse<EmptyJsonResponse> createRoom(@RequestBody @Valid CreateRoomDto dto) {
        log.info("방 생성");
        roomService.createRoom(dto);
        return ApiResponse.success();
    }

    @GetMapping("")
    public ApiResponse<List<GetAllRoomDto>> getAllRoom() {
        log.info("모든 방 조회");
        return ApiResponse.success(roomService.getAllRoom());
    }

    @GetMapping("/{roomId}")
    public ApiResponse<Room> getRoom (@PathVariable Long roomId) {
        log.info("특정 방 조회");
        return ApiResponse.success(roomService.getRoom(roomId));
    }

    @PutMapping("/{roomId}")
    public ApiResponse<EmptyJsonResponse> updateRoom(@PathVariable Long roomId,
                                                     @RequestBody @Valid UpdateRoomDto dto) {
        log.info("특정 방 수정");
        Room room = roomService.getRoom(roomId);

        // TODO - 현재 로그인한 member가 room owner인지 검사하는 기능 추가해야 합니다.

        roomService.updateRoom(room, dto);

        return ApiResponse.success();
    }

    @DeleteMapping("/{roomId}")
    public ApiResponse<EmptyJsonResponse> deleteRoom(@PathVariable Long roomId) {
        log.info("특정 방 삭제");

        // TODO - 현재 로그인한 member가 room owner인지 검사하는 기능 추가해야 합니다.

        roomService.deleteRoom(roomId);
        return ApiResponse.success();
    }

    @PostMapping("/in")
    public ApiResponse<EmptyJsonResponse> inRoom(@RequestBody @Valid InOutRoomDto dto) {
        log.info("특정 방 입장");
        roomService.inRoom(dto);
        return ApiResponse.success();
    }

    @PostMapping("/out")
    public ApiResponse<EmptyJsonResponse> outRoom(@RequestBody @Valid InOutRoomDto dto) {
        log.info("특정 방 퇴장");
        roomService.outRoom(dto);
        return ApiResponse.success();
    }
}
