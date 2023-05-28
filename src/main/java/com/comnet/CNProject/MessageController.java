package com.comnet.CNProject;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import java.util.HashMap;

@Controller
public class MessageController {
    private HashMap<String, Member> userMap = new HashMap<>();
    private String question; //맞춰야하는 정답.
    private int cnt=20;// 20번 질문 가능
    @MessageMapping("/enter")
    @SendTo("/simple/chat")
    public ReturnMessage enterMessage(Message message) { //처음 들어오면 실행. 아무도 없으면 자동으로 출제자. 있으면 문제 맞추는 사람.
        Member member = new Member();
        member.setName(message.getSender());
        if (userMap.isEmpty()) {//아무도 없으면 출제자가 됨
            member.setRole("host");
        }
        else{ //이미 있으면 문제 맞추는 사람
            member.setRole("client");
        }
        member.setProfile(message.getProfile());
        userMap.put(member.getName(), member);
        if (member.getRole() == "host") {
            message.setContent(message.getSender() + "님. 안녕하세요. 문제를 작성해주세요. ");

        }
        if (member.getRole() == "client") {
            message.setContent(message.getSender() + "님. 안녕하세요. 정답을 맞춰주세요. ");

        }

        ReturnMessage returnMessage = new ReturnMessage();
        returnMessage.setRole(member.getRole());
        returnMessage.setSender(message.getSender());
        returnMessage.setProfile(member.getProfile());
        returnMessage.setContent(message.getContent());
        returnMessage.setCount(20);
        return returnMessage;
    }

    @MessageMapping("/question-submit")
    @SendTo("/simple/chat") //출제자가 가장 처음 접속하면 문제를 내면 여기로 옴
    public ReturnMessage submitQuestion(Message message) {
        question= message.getContent();
        ReturnMessage returnMessage = new ReturnMessage();
        returnMessage.setRole("system");
        returnMessage.setSender("system");
        returnMessage.setProfile("none");
        returnMessage.setContent("문제가 제출되었습니다. 지금부터 질문을 하면 횟수가 차감됩니다.");
        returnMessage.setCount(20);
        return returnMessage;
    }

    @MessageMapping("/chat") //질문, 답변
    @SendTo("/simple/chat")
    public ReturnMessage sendMessage(Message message) {
        Member member = userMap.get(message.getSender());
        if (member.getRole()=="client"){
            if (question.equals(message.getContent())) {
                ReturnMessage returnMessage = new ReturnMessage();
                returnMessage.setRole("system");
                returnMessage.setSender("system");
                returnMessage.setProfile(member.getProfile());
                returnMessage.setContent(message.getSender()+"님께서 정답을 맞히셨습니다! 게임읠 종료합니다.");

                returnMessage.setCount(cnt);
                return returnMessage;
            }

            cnt -= 1; //문제 맞추는 사람이 맞추려고 시도하면 카운트 1 감소.
            if (cnt == 0) {
                ReturnMessage returnMessage = new ReturnMessage();
                returnMessage.setRole("system");
                returnMessage.setSender("system");
                returnMessage.setProfile(member.getProfile());
                returnMessage.setContent(message.getSender()+"문제를 맞히지 못했습니다. 게임을 종료합니다.");

                returnMessage.setCount(cnt);
                return returnMessage;

            }

        }


        ReturnMessage returnMessage = new ReturnMessage();
        returnMessage.setRole(member.getRole());
        returnMessage.setSender(message.getSender());
        returnMessage.setProfile(member.getProfile());
        returnMessage.setContent(message.getContent());
        returnMessage.setCount(cnt);
        return returnMessage;
    }

}
