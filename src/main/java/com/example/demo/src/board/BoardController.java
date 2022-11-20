package com.example.demo.src.board;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.board.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


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
    @PostMapping("/board")    // POST 방식의 요청을 매핑하기 위한 어노테이션
    public BaseResponse<PostBoardRes> createBoard(@RequestBody PostBoardReq postBoardReq) {
        try {
            PostBoardRes postBoardRes = boardService.createBoard(postBoardReq);
            return new BaseResponse<>(postBoardRes);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

//    @ResponseBody
//    @GetMapping("/board") // (GET) 127.0.0.1:9000/app/users/:userIdx
//    public BaseResponse<GetBoardRes> getBoard() {
//        // @PathVariable RESTful(URL)에서 명시된 파라미터({})를 받는 어노테이션, 이 경우 userId값을 받아옴.
//        //  null값 or 공백값이 들어가는 경우는 적용하지 말 것
//        //  .(dot)이 포함된 경우, .을 포함한 그 뒤가 잘려서 들어감
//        // Get Users
//        try {
//            GetBoardRes getBoardRes = boardProvider.getBoard();
//            return new BaseResponse<>(getBoardRes);
//        } catch (BaseException exception) {
//            return new BaseResponse<>((exception.getStatus()));
//        }
//
//    }

    @ResponseBody
    @GetMapping("/board/{boardIdx}") // (GET) 127.0.0.1:9000/app/users/:userIdx
    public BaseResponse<GetBoardRes> getBoard(@PathVariable("boardIdx") int boardIdx) {
        // @PathVariable RESTful(URL)에서 명시된 파라미터({})를 받는 어노테이션, 이 경우 userId값을 받아옴.
        //  null값 or 공백값이 들어가는 경우는 적용하지 말 것
        //  .(dot)이 포함된 경우, .을 포함한 그 뒤가 잘려서 들어감
        // Get Users
        try {
            GetBoardRes getBoardRes = boardProvider.getBoard(boardIdx);
            return new BaseResponse<>(getBoardRes);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    @ResponseBody
    @PatchMapping("/board/{boardIdx}")
    public BaseResponse<String> modifyBoardContent(@PathVariable("boardIdx") int boardIdx, @RequestBody Board board) {
        try {
            PatchBoardReq patchBoardReq = new PatchBoardReq(boardIdx, board.getContent());
            boardService.modifyBoardContent(patchBoardReq);

            String result = "게시물 내용이 수정되었습니다.";
            return new BaseResponse<>(result);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }


}
