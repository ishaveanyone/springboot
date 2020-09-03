/**
 * Date: 2020-09-02 10:19
 * Author: xupp
 */

package com.xupp.springbootmybatis.service;
import com.xupp.springbootmybatis.config.MultipleDataSourceHelper;
import com.xupp.springbootmybatis.entity.UserEntity;
import com.xupp.springbootmybatis.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(propagation = Propagation.REQUIRED)
public class UserService {
    @Autowired
    private UserMapper userMapper;

    public  Object save(String name){
        MultipleDataSourceHelper.set(MultipleDataSourceHelper.MASTER);
        UserEntity userEntity =new UserEntity();
        userEntity.setName(name);
        int result=userMapper.insert(userEntity);
        return result;
    }

    //通过从节点进行数据的查询
    public  Object getAll(){
        MultipleDataSourceHelper.set(MultipleDataSourceHelper.SLAVE);
        return userMapper.selectList(null);
    }


}
