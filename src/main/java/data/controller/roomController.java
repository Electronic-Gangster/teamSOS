package data.controller;

import java.util.Timer;
import java.util.TimerTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import data.dto.roomDto;
import data.service.roomService;
@RestController
@RequestMapping("/room")
public class roomController {
    @Autowired
    roomService roomService;

    @GetMapping("/info/{id}")
    public roomDto getInfo(@PathVariable String id) {
        roomDto room = roomService.getRoom(id);
        if (room != null) {
            // 방 정보를 가져왔을 때만 타이머를 시작하고, 10초 후에 방을 삭제하도록 설정
            Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    roomService.deleteRoom(id);
                    timer.cancel(); // 타이머 종료
                }
            }, 600 * 1000); // 10초 (밀리초)
        }
        return room;
    }
}