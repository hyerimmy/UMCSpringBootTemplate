package com.example.demo.src.following;

import com.example.demo.config.BaseException;
import com.example.demo.src.board.BoardDao;
import com.example.demo.src.board.model.GetBoardRes;
import com.example.demo.src.following.model.Following;
import com.example.demo.src.user.model.GetUserRes;
import com.example.demo.utils.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.demo.config.BaseResponseStatus.DATABASE_ERROR;

@Service

public class FollowingProvider {

    private final FollowingDao followingDao;
    private final JwtService jwtService; // JWT부분은 7주차에 다루므로 모르셔도 됩니다!


    @Autowired //readme 참고
    public FollowingProvider(FollowingDao followingDao, JwtService jwtService) {
        this.followingDao = followingDao;
        this.jwtService = jwtService; // JWT부분은 7주차에 다루므로 모르셔도 됩니다!
    }

    // 전체 조회
    public List<Following> getFollowingsAll() throws BaseException {
        try {
            List<Following> getFollowingsAllRes = followingDao.getFollowingsAll();
            return getFollowingsAllRes;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    // 단일 팔로잉 조회 0 - followingIdx
    public List<Following> getFollowings(int followingIdx) throws BaseException {
        try {
            List<Following> getFollowingsRes = followingDao.getFollowings(followingIdx);
            return getFollowingsRes;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }
    // 단일 팔로잉 조회 1 - follower : 내가 팔로우 하는 사람 보기
    public List<Following> getFollowingsByFollower(int followerIdx) throws BaseException {
        try {
            List<Following> getFollowingsByFollowerRes = followingDao.getFollowingsByFollower(followerIdx);
            return getFollowingsByFollowerRes;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }
    // 단일 팔로잉 조회 2 - followee : 나를 팔로우 하는 사람 보기
    public List<Following> getFollowingsByFollowee(int followeeIdx) throws BaseException {
        try {
            List<Following> getFollowingsByFolloweeRes = followingDao.getFollowingsByFollowee(followeeIdx);
            return getFollowingsByFolloweeRes;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }
}
