package com.comnet.CNProject;

import org.springframework.stereotype.Repository;

import java.util.HashMap;
@Repository
public class MemberRepository {
    private HashMap<String, Member> userMap = new HashMap<>();

    public void addUser(Member member) {
        userMap.put(member.getName(), member);
    }

    public boolean isEmpty() {
        return userMap.isEmpty();
    }

    public Member getMember(String name) {
        return userMap.get(name);
    }
}
