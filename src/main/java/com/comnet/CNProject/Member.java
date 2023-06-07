package com.comnet.CNProject;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.socket.WebSocketSession;
@Getter
@Setter
public class Member {
    private String name; //이름
    private String profile; //이미지 바이너리 데이터
    private String role; //역할 master:출제자 slave:맞추는 자
}
