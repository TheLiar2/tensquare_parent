package com.tensquare.friend.service;

import com.tensquare.friend.dao.FriendDao;
import com.tensquare.friend.dao.NoFriendDao;
import com.tensquare.friend.pojo.Friend;
import com.tensquare.friend.pojo.NoFriend;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author xiaokuli
 * @date 2019/6/3 - 15:33
 */
@Service
@Transactional
public class FriendService {

    @Autowired
    private FriendDao friendDao;

    @Autowired
    private NoFriendDao noFriendDao;

    //添加好友
    public int addFriend(String userid,String friendid){
        //先判断好友表中是否有该添加记录
        Friend friend = friendDao.findByUseridAndFriendid(userid, friendid);
        if(friend !=null){
            return 0;
        }
        //单项添加好友
        friend = new Friend();
        friend.setUserid(userid);
        friend.setFriendid(friendid);
        friend.setIslike("0");
        friendDao.save(friend);

        //判断是否添加的好友已添加你
        if(friendDao.findByUseridAndFriendid(friendid,userid)!=null){
            friendDao.updateIsLike("1",userid,friendid);
            friendDao.updateIsLike("1",friendid,userid);
        }
        return 1;
    }

    //添加非好友
    public int addNoFriend(String userid,String friendid){
        NoFriend noFriend = noFriendDao.findByUseridAndFriendid(userid, friendid);
        if(noFriend!=null){
            return 0;
        }
        noFriend = new NoFriend();
        noFriend.setUserid(userid);
        noFriend.setFriendid(friendid);
        noFriendDao.save(noFriend);
        return 1;
    }

    //删除好友
    public void deleteFriend(String userid,String friendid){
        //删除表中userid到friendid的记录
        friendDao.deleteFriend(userid,friendid);
        //更新friendid到userid的islike为0
        friendDao.updateIsLike("0",friendid,userid);
        //向非好友表添加记录
        NoFriend noFriend = new NoFriend();
        noFriend.setUserid(userid);
        noFriend.setFriendid(friendid);
        noFriendDao.save(noFriend);

    }

}
