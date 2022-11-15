package com.example.demo.src.board;

import com.example.demo.config.BaseException;
import com.example.demo.config.secret.Secret;
import com.example.demo.src.board.model.PostBoardReq;
import com.example.demo.src.board.model.PostBoardRes;
import com.example.demo.src.user.UserDao;
import com.example.demo.src.user.UserProvider;
import com.example.demo.src.user.model.PostUserReq;
import com.example.demo.src.user.model.PostUserRes;
import com.example.demo.utils.AES128;
import com.example.demo.utils.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.example.demo.config.BaseResponseStatus.*;

@Service

public class BoardService {

    private final BoardDao boardDao;
    private final BoardProvider boardProvider;
//    private final JwtService jwtService; // JWT부분은 7주차에 다루므로 모르셔도 됩니다!

    @Autowired //readme 참고
    public BoardService(BoardDao boardDao, BoardProvider boardProvider) {
        this.boardDao = boardDao;
        this.boardProvider = boardProvider;
//        this.jwtService = jwtService; // JWT부분은 7주차에 다루므로 모르셔도 됩니다!
    }

    // ******************************************************************************
    // 게시물 생성(POST)
    public PostBoardRes createBoard(PostBoardReq postBoardReq) throws BaseException {
        int boardIdx = boardDao.createBoard(postBoardReq);
        return new PostBoardRes(boardIdx);
    }


}
