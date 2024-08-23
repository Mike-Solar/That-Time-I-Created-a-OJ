package cn.mikesolar.oj.Entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("user")
public class UserPo {

    @TableId(type = IdType.AUTO)
    private int id;

    private String username;

    private String password;

    private boolean isAdmin;

    private String phoneNumber;

    private Date lastLogin;

    private String lastLoginIp;

    private Date registerTime;

    private String registerIp;

    private String salt;

    private boolean isDisabled;

    private boolean isDeleted;
}
