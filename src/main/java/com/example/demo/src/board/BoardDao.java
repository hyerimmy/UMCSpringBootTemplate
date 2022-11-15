package com.example.demo.src.board;

import com.example.demo.src.board.model.PostBoardReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

@Repository //  [Persistence Layer에서 DAO를 명시하기 위해 사용]

public class BoardDao {
    private JdbcTemplate jdbcTemplate;

    @Autowired //readme 참고
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    // 게시물 생성
    public int createBoard(PostBoardReq postBoardReq) {
        String createBoardQuery = "insert into Board (nickname, content, likeCnt, commentCnt) VALUES (?,?,?,?)"; // 실행될 동적 쿼리문
        Object[] createBoardParams = new Object[]{
                postBoardReq.getNickname(),
                postBoardReq.getContent(),
                postBoardReq.getLikeCnt(),
                postBoardReq.getCommentCnt()}; // 동적 쿼리의 ?부분에 주입될 값
        this.jdbcTemplate.update(createBoardQuery, createBoardParams);

        String lastInserIdQuery = "select last_insert_id()"; // 가장 마지막에 삽입된(생성된) id값은 가져온다.
        return this.jdbcTemplate.queryForObject(lastInserIdQuery, int.class); // 해당 쿼리문의 결과 마지막으로 삽인된 유저의 userIdx번호를 반환한다.
    }
}
