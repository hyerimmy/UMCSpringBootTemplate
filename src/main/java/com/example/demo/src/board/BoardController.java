package com.example.demo.src.board;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.board.model.PostBoardReq;
import com.example.demo.src.board.model.PostBoardRes;
import com.example.demo.src.user.model.PostUserReq;
import com.example.demo.src.user.model.PostUserRes;
import com.example.demo.utils.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static com.example.demo.config.BaseResponseStatus.POST_USERS_EMPTY_EMAIL;
import static com.example.demo.config.BaseResponseStatus.POST_USERS_INVALID_EMAIL;
import static com.example.demo.utils.ValidationRegex.isRegexEmail;


@RestController
//@RequestMapping("/app/board")

public class BoardController {
    @Autowired
    private final BoardProvider boardProvider;
    @Autowired
    private final BoardService boardService;
    @Autowired
//    private final JwtService jwtService; // JWT부분은 7주차에 다루므로 모르셔도 됩니다!

    public BoardController(BoardProvider boardProvider, BoardService boardService) {
        this.boardProvider = boardProvider;
        this.boardService = boardService;
//        this.jwtService = jwtService; // JWT부분은 7주차에 다루므로 모르셔도 됩니다!
    }

    @ResponseBody
    @GetMapping("/logham")
    public String hahaha() {
        System.out.println("테스트");
//        trace, debug 레벨은 Console X, 파일 로깅 X
//        logger.trace("TRACE Level 테스트");
//        logger.debug("DEBUG Level 테스트");


        return "Success Test ham";
    }

    @ResponseBody
    @PostMapping("/board")    // POST 방식의 요청을 매핑하기 위한 어노테이션
    public BaseResponse<PostBoardRes> createBoard(@RequestBody PostBoardReq postBoardReq) {
        try {
            PostBoardRes postBoardRes = boardService.createBoard(postBoardReq);
            return new BaseResponse<>(postBoardRes);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

}
