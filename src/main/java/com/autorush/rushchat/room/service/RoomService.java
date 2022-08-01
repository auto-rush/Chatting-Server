package com.autorush.rushchat.room.service;

import com.autorush.rushchat.common.exception.CustomException;
import com.autorush.rushchat.common.exception.ErrorCode;
import com.autorush.rushchat.member.entity.Member;
import com.autorush.rushchat.member.repository.MemberRepository;
import com.autorush.rushchat.room.dto.CreateRoomDto;
import com.autorush.rushchat.room.dto.GetAllRoomDto;
import com.autorush.rushchat.room.dto.UpdateRoomDto;
import com.autorush.rushchat.room.entity.Room;
import com.autorush.rushchat.room.entity.RoomMember;
import com.autorush.rushchat.room.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class RoomService {
    private final RoomRepository roomRepository;
    private final MemberRepository memberRepository;

    private static final Long MAX_PARTICIPANTS = 1000L; // 수용 가능 인원 1000명

    @Transactional
    public void createRoom(CreateRoomDto dto) {
        if (dto.getMaxParticipants() > MAX_PARTICIPANTS)
            throw new CustomException(ErrorCode.EXCEEDED_MAX_PARTICIPANTS_VALUE);
        Member member = memberRepository.findByRegistrationIdAndOAuthId(dto.getOwnerRegisteredPlatform(), dto.getOwnerOAuthId()).orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_MEMBER));
        Room room = new Room(dto.getRoomName(), dto.getOwnerOAuthId(), dto.getOwnerRegisteredPlatform(), dto.getMaxParticipants(), member);
        roomRepository.save(room);
    }

    public List<GetAllRoomDto> getAllRoom() {
        List<Room> rooms = roomRepository.findAll();
        List<GetAllRoomDto> allRooms = new ArrayList<>();

        for (Room room : rooms) {
            GetAllRoomDto getAllRoomDto = new GetAllRoomDto(room);
            log.info("room : " + room);
            allRooms.add(getAllRoomDto);
        }

        return allRooms;
    }

    public Room getRoom(Long roomId) {
        return roomRepository.findById(roomId).orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_ROOM));
    }

    @Transactional
    public void updateRoom(Room room, UpdateRoomDto dto) {
        if (dto.getRoomName() != null) {
            log.info("방 이름 변경");
            room.setRoomName(dto.getRoomName());
        }

        if (dto.getMaxParticipants() != null) {
            log.info("방 제한 인원 변경");
            if (dto.getMaxParticipants() > MAX_PARTICIPANTS)
                throw new CustomException(ErrorCode.EXCEEDED_MAX_PARTICIPANTS_VALUE);
            room.setMaxParticipants(dto.getMaxParticipants());
        }

        if (dto.getOwnerOAuthId() != null && dto.getOwnerRegisteredPlatform() != null) {
            log.info("방 주인 변경");
            Member member = memberRepository.findByRegistrationIdAndOAuthId(dto.getOwnerRegisteredPlatform(), dto.getOwnerOAuthId()).orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_MEMBER));
//            if (!room.getRoomMembers().contains(member)) throw new CustomException(ErrorCode.NOT_FOUND_MEMBER_IN_ROOM);

            boolean isContain = false;
            for (RoomMember roomMember : room.getRoomMembers()) {
                Member roomMemberMember = roomMember.getMember();
                if (!roomMemberMember.getOAuthId().equals(member.getOAuthId())
                        && !roomMemberMember.getRegisteredPlatform().equals(member.getRegisteredPlatform()))
                    return;
                isContain = true;
            }
            if (!isContain) throw new CustomException(ErrorCode.NOT_FOUND_MEMBER_IN_ROOM);

            room.setOwnerOAuthId(dto.getOwnerOAuthId());
            room.setOwnerRegisteredPlatform(dto.getOwnerRegisteredPlatform());
        }

        roomRepository.save(room);
    }

    @Transactional
    public void deleteRoom(Long roomId) {
        roomRepository.deleteById(roomId);
    }

}
