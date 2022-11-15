package com.example.demo.src.board.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter // 해당 클래스에 대한 접근자 생성
@Setter // 해당 클래스에 대한 설정자 생성
@AllArgsConstructor // 해당 클래스의 모든 멤버 변수(userIdx, nickname, email, password)를 받는 생성자를 생성

public class PostBoardRes {
    private int boardIdx;
//    해당 부분은 7주차 - JWT 수업 후 주석해제 및 대체해주세요!
//    private String jwt;
}
