package com.example.demo.src.board;

import com.example.demo.src.board.model.GetBoardRes;
import com.example.demo.src.board.model.PatchBoardReq;
import com.example.demo.src.board.model.PostBoardReq;
import com.example.demo.src.user.model.PatchUserReq;
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

    // 해당 userIdx를 갖는 유저조회
    public GetBoardRes getBoard(int boardIdx) {
        String getBoardQuery = "select * from Board where boardIdx = ?"; // 해당 userIdx를 만족하는 유저를 조회하는 쿼리문
        int getBoardParams = boardIdx;
        return this.jdbcTemplate.queryForObject(getBoardQuery,
                (rs, rowNum) -> new GetBoardRes(
                        rs.getInt("boardIdx"),
                        rs.getString("nickname"),
                        rs.getString("content"),
                        rs.getInt("likeCnt"),
                        rs.getInt("commentCnt")), // RowMapper(위의 링크 참조): 원하는 결과값 형태로 받기
                getBoardParams); // 한 개의 회원정보를 얻기 위한 jdbcTemplate함수(Query, 객체 매핑 정보, Params)의 결과 반환
    }

    // 게시물 내용 변경
    public int modifyBoardContent(PatchBoardReq patchBoardReq) {
        String modifyBoardContentQuery = "update Board set content = ? where boardIdx = ? "; // 해당 userIdx를 만족하는 User를 해당 nickname으로 변경한다.
        Object[] modifyBoardContentParams = new Object[]{patchBoardReq.getContent(), patchBoardReq.getBoardIdx()}; // 주입될 값들(nickname, userIdx) 순

        return this.jdbcTemplate.update(modifyBoardContentQuery, modifyBoardContentParams); // 대응시켜 매핑시켜 쿼리 요청(생성했으면 1, 실패했으면 0)
    }
}
