package com.comnet.CNProject;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReturnMessage {
    private String role;//출제자면 host 맞추는 사람은 client
    private String sender;//이름
    private String profile; //프로필 바이너리 데이터
    private String content;
    private int count;
}
