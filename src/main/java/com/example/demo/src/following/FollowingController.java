package com.example.demo.src.following;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.board.model.PostBoardRes;
import com.example.demo.src.following.model.DeleteFollowingReq;
import com.example.demo.src.following.model.Following;
import com.example.demo.src.following.model.PostFollowingReq;
import com.example.demo.src.following.model.PostFollowingRes;
import com.example.demo.src.user.model.GetUserRes;
import com.example.demo.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("following")

public class FollowingController {

    // *********************** 동작에 있어 필요한 요소들을 불러옵니다. *************************

    final Logger logger = LoggerFactory.getLogger(this.getClass()); // Log를 남기기: 일단은 모르고 넘어가셔도 무방합니다.

    @Autowired  // 객체 생성을 스프링에서 자동으로 생성해주는 역할. 주입하려 하는 객체의 타입이 일치하는 객체를 자동으로 주입한다.
    // IoC(Inversion of Control, 제어의 역전) / DI(Dependency Injection, 의존관계 주입)에 대한 공부하시면, 더 깊이 있게 Spring에 대한 공부를 하실 수 있을 겁니다!(일단은 모르고 넘어가셔도 무방합니다.)
    // IoC 간단설명,  메소드나 객체의 호출작업을 개발자가 결정하는 것이 아니라, 외부에서 결정되는 것을 의미
    // DI 간단설명, 객체를 직접 생성하는 게 아니라 외부에서 생성한 후 주입 시켜주는 방식
    private final FollowingProvider followingProvider;
    @Autowired
    private final FollowingService followingService;
    @Autowired
    private final JwtService jwtService; // JWT부분은 7주차에 다루므로 모르셔도 됩니다!


    public FollowingController(FollowingProvider followingProvider, FollowingService followingService, JwtService jwtService) {
        this.followingProvider = followingProvider;
        this.followingService = followingService;
        this.jwtService = jwtService; // JWT부분은 7주차에 다루므로 모르셔도 됩니다!
    }

    // ******************************************************************************

    // 팔로우 생성 API
    @ResponseBody
    @PostMapping("")
    public BaseResponse<PostFollowingRes> createFollowing(@RequestBody PostFollowingReq postFollowingReq){
        try {
            PostFollowingRes postFollowingRes = followingService.createFollowing(postFollowingReq);
            return new BaseResponse<>(postFollowingRes);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    // 팔로우 삭제 API
    @ResponseBody
    @DeleteMapping("")
    public BaseResponse<String> deleteFollowing(@RequestBody DeleteFollowingReq deleteFollowingReq){
        try {
            followingService.deleteFollowing(deleteFollowingReq);

            String result = "팔로잉이 취소되었습니다.";
            return new BaseResponse<>(result);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    // 팔로우 전체조회, followingIdx 단일조회
    @ResponseBody
    @GetMapping("")
    public BaseResponse<List<Following>> getFollowingsAll(@RequestParam(required = false) String followingIdx) {
        try {
            if (followingIdx == null) { // query string인 nickname이 없을 경우, 그냥 전체 유저정보를 불러온다.
                List<Following> getFollowingsRes = followingProvider.getFollowingsAll();
                return new BaseResponse<>(getFollowingsRes);
            }
            // query string인 nickname이 있을 경우, 조건을 만족하는 유저정보들을 불러온다.
            List<Following> getFollowingsRes = followingProvider.getFollowings( Integer.parseInt(followingIdx));
            return new BaseResponse<>(getFollowingsRes);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

//    // 팔로우 조회 0 - followIdx
//    @ResponseBody
//    @GetMapping("")
//    public BaseResponse<List<Following>> getFollowing(@RequestParam(required = false) int followingIdx){
//        try {
//            List<Following> getFollowingByFollowingRes = followingProvider.getFollowings(followingIdx);
//            return new BaseResponse<>(getFollowingByFollowingRes);
//        } catch (BaseException exception) {
//            return new BaseResponse<>((exception.getStatus()));
//        }
//    }
    // 팔로우 조회 1 - follower
    @ResponseBody
    @GetMapping("/2")
    public BaseResponse<List<Following>> getFollowingByFollower(@RequestParam(required = false) int followerIdx){
        try {
            List<Following> getFollowingByFollowerRes = followingProvider.getFollowingsByFollower(followerIdx);
            return new BaseResponse<>(getFollowingByFollowerRes);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }
    // 팔로우 조회 2 - followee
    @ResponseBody
    @GetMapping("/3")
    public BaseResponse<List<Following>> getFollowingByFollowee(@RequestParam(required = false) int followeeIdx){
        try {
            List<Following> getFollowingByFolloweeRes = followingProvider.getFollowingsByFollowee(followeeIdx);
            return new BaseResponse<>(getFollowingByFolloweeRes);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }
}
