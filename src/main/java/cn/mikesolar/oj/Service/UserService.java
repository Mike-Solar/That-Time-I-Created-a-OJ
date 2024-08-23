package cn.mikesolar.oj.Service;

import cn.hutool.crypto.digest.DigestAlgorithm;
import cn.hutool.crypto.digest.Digester;
import cn.mikesolar.oj.Entity.UserPo;
import cn.mikesolar.oj.Enum.UserOperateState;
import cn.mikesolar.oj.Mappers.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    public boolean registerUser(String userName, String password, String phone, String ip) {
        UserPo userPo = new UserPo();
        userPo.setUsername(userName);
        String salt = UUID.randomUUID().toString().replace("-","");
        String passwordWithSalt=salt + password;
        Digester sha256 = new Digester(DigestAlgorithm.SHA256);
        String hashedPassword = sha256.digestHex(passwordWithSalt);
        userPo.setPassword(hashedPassword);
        userPo.setSalt(salt);
        userPo.setPhoneNumber(phone);
        userPo.setRegisterTime(new Date());
        userPo.setRegisterIp(ip);
        userPo.setDeleted(false);
        userPo.setDisabled(false);
        return userMapper.insert(userPo) > 0;
    }

    public UserOperateState login(String userNameOrPhoneNumber, String password, String ip, boolean isUsername) {
        UserPo userPo=getUser(userNameOrPhoneNumber,isUsername);
        if(userPo == null) {
            return UserOperateState.INVALID_USER;
        }
        String salt=userPo.getSalt();
        Digester sha256 = new Digester(DigestAlgorithm.SHA256);
        String digestHex = sha256.digestHex(salt+password);
        if(digestHex.equals(userPo.getPassword())){
            userPo.setLastLogin(new Date());
            userPo.setLastLoginIp(ip);
            userMapper.updateById(userPo);
            return UserOperateState.SUCCESS;
        }
        else{
            return UserOperateState.WRONG_PASSWORD;
        }
    }
    public UserOperateState changePassword(String userNameOrPhoneNumber, String oldPassword, String newPassword, boolean isUsername) {
        UserPo userPo=getUser(userNameOrPhoneNumber,isUsername);
        if(userPo == null) {
            return UserOperateState.INVALID_USER;
        }
        String salt = userPo.getSalt();
        Digester sha256 = new Digester(DigestAlgorithm.SHA256);
        String hashedPassword = sha256.digestHex(salt+oldPassword);
        if(hashedPassword.equals(userPo.getPassword())){
            String passwordWithSalt=salt + userPo.getPassword();
            String hashedNewPasswordWithSalt=sha256.digestHex(passwordWithSalt);
            userPo.setPassword(hashedNewPasswordWithSalt);
            if(userMapper.updateById(userPo) > 0){
                return UserOperateState.SUCCESS;
            }else{
                return UserOperateState.UNKNOWN_ERROR;
            }
        }
        else{
            return UserOperateState.WRONG_PASSWORD;
        }
    }

    public UserOperateState changeUsername(String userNameOrPhoneNumber, String newUsername, boolean isUsername) {
        UserPo userPo=getUser(userNameOrPhoneNumber,isUsername);
        if(userPo == null) {
            return UserOperateState.INVALID_USER;
        }
        userPo.setUsername(newUsername);
        if(userMapper.updateById(userPo) > 0){
            return UserOperateState.SUCCESS;
        }
        else {
            return UserOperateState.UNKNOWN_ERROR;
        }
    }

    public UserOperateState changePhoneNumber(String userNameOrPhoneNumber, String newPhoneNumber, boolean isUsername) {
        UserPo userPo=getUser(userNameOrPhoneNumber,isUsername);
        if(userPo == null) {
            return UserOperateState.INVALID_USER;
        }
        userPo.setPhoneNumber(newPhoneNumber);
        if(userMapper.updateById(userPo) > 0){
            return UserOperateState.SUCCESS;
        }else {
            return UserOperateState.UNKNOWN_ERROR;
        }
    }
    public boolean disableUser(String userNameOrPhoneNumber, String ip, boolean isUsername) {
        UserPo userPo=getUser(userNameOrPhoneNumber,isUsername);
        if(userPo == null) {
            return false;
        }
        userPo.setDisabled(true);
        return userMapper.updateById(userPo) > 0;
    }

    public boolean deleteUser(String userNameOrPhoneNumber, String ip, boolean isUsername) {
        UserPo userPo=getUser(userNameOrPhoneNumber,isUsername);
        userPo.setDeleted(true);
        return userMapper.updateById(userPo) > 0;
    }

    public boolean deleteUserPermantly(String userNameOrPhoneNumber, String ip, boolean isUsername) {
        UserPo userPo=getUser(userNameOrPhoneNumber,isUsername);
        if(userPo == null) {
            return true;
        }
        return userMapper.deleteById(userPo.getId()) > 0;
    }
    private UserPo getUser(String userNameOrPhoneNumber, boolean isUsername) {
        UserPo userPo;
        if(isUsername){
            userPo = userMapper.getUserByName(userNameOrPhoneNumber);
        }
        else {
            userPo = userMapper.getUserByPhoneNumber(userNameOrPhoneNumber);
        }
        return userPo;
    }
    public void cleanupUser(){
        userMapper.cleanupUser();
    }
    private boolean addUser(UserPo user) {
        return userMapper.insert(user) > 0;
    }

    private boolean updateUser(UserPo user) {
        return userMapper.updateById(user) > 0;
    }

}
