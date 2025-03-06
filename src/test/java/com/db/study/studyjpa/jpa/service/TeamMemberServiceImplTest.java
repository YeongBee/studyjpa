//package com.db.study.studyjpa.jpa.service;
//
//import jakarta.transaction.Transactional;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.annotation.Rollback;
//
//@SpringBootTest
//@Transactional
//@Rollback(false)
//class TeamMemberServiceImplTest {
//
//    @Autowired
//    private TeamRepository teamRepository;
//
//    @Autowired
//    private MemberRepository memberRepository;
//
//    @Test
//    void updateTest() {
//
//
//        Team updateTeam2 = teamRepository.findById(2L)
//                .orElseThrow(() -> new RuntimeException("Team 2 not found"));
//
//        System.out.println("updateTeam2 = " + updateTeam2);
//
//        Member newMember = new Member("Team2 New Member", updateTeam2);
//
//        updateTeam2.getMembers().add(newMember);
//
//        Team updateTeam22 = teamRepository.findById(2L)
//                .orElse(null);
//
//
//        System.out.println("updateTeam22 = " + updateTeam22);
//
//    }
//
//
//    @Test
//    void updateTest2(){
//
//        Team updateTeam2 = teamRepository.findById(2L)
//                .orElse(null);
//        System.out.println("updateTeam2 = " + updateTeam2);
//
//        Member member = memberRepository.findById(13L).orElse(null);
//        member.updateUserName("Team2 update Member");       // 업데이트 메서드, setter
//
//        Team updateTeam22 = teamRepository.findById(2L)
//                .orElse(null);
//        System.out.println("updateTeam22 = " + updateTeam22);
//
//    }
//
//
//}