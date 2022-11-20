package com.example.demo.src.following;

import com.example.demo.config.BaseException;
import com.example.demo.src.board.BoardDao;
import com.example.demo.src.board.BoardProvider;
import com.example.demo.src.board.model.PostBoardReq;
import com.example.demo.src.board.model.PostBoardRes;
import com.example.demo.src.following.model.DeleteFollowingReq;
import com.example.demo.src.following.model.PostFollowingReq;
import com.example.demo.src.following.model.PostFollowingRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.example.demo.config.BaseResponseStatus.DATABASE_ERROR;
import static com.example.demo.config.BaseResponseStatus.MODIFY_FAIL_USERNAME;

@Service

public class FollowingService {

    private final FollowingDao followingDao;
    private final FollowingProvider followingProvider;

    @Autowired //readme 참고
    public FollowingService(FollowingDao followingDao, FollowingProvider followingProvider) {
        this.followingDao = followingDao;
        this.followingProvider = followingProvider;
    }

    // ******************************************************************************
    // 게시물 생성(POST)
    public PostFollowingRes createFollowing(PostFollowingReq postFollowingReq) throws BaseException {
        int followingIdx = followingDao.createFollowing(postFollowingReq);
        return new PostFollowingRes(followingIdx);
    }

    // 게시물 삭제 (DELETE)
    public void deleteFollowing(DeleteFollowingReq deleteFollowingReq) throws BaseException {
        try {
            int result = followingDao.deleteFollowing(deleteFollowingReq); // 해당 과정이 무사히 수행되면 True(1), 그렇지 않으면 False(0)입니다.
            if (result == 0) { // result값이 0이면 과정이 실패한 것이므로 에러 메서지를 보냅니다.
                throw new BaseException(MODIFY_FAIL_USERNAME);
            }
        } catch (Exception exception) { // DB에 이상이 있는 경우 에러 메시지를 보냅니다.
            throw new BaseException(DATABASE_ERROR);
        }
    }
}
