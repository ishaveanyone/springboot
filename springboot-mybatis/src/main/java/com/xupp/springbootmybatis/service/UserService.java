/**
 * Date: 2020-09-02 10:19
 * Author: xupp
 */

package com.xupp.springbootmybatis.service;
import com.xupp.springbootmybatis.entity.UserEntity;
import com.xupp.springbootmybatis.mapper.master.MasterUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(propagation = Propagation.REQUIRED)
public class UserService {
    @Autowired
    private MasterUserMapper masterUserMapper;


    public  Object save(String name){
        UserEntity userEntity =new UserEntity();
        userEntity.setName(name);
        int result=masterUserMapper.insert(userEntity);
        return result;
    }

    //通过从节点进行数据的查询
    public  Object getAll(){
//        return salveUserMapper.selectList(null);
        return null;
    }


}
