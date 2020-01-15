package service.impl;


import db.ConnectionManager;
import dto.UserDto;
import dto.UserLoginDto;
import entity.Role;
import entity.User;
import exception.Message;
import exception.NotFoundException;
import service.UserService;

//import javax.jws.soap.SOAPBinding;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

public class UserLoginService implements Message {

    private UserService userService;

    public UserLoginService() {
        userService=new UserServiceImpl();
    }

    public boolean isValid(UserLoginDto userLoginDto) {
        boolean status;
        String username = userLoginDto.getUsername();
        String password = userLoginDto.getPassword();

        if (username == null || password == null ||
                username.length() == 0 || password.length() == 0) {
           status= false;
        } else {
            Connection conn = ConnectionManager.getInstance().getConnection();
            try {
                UserDto user=getUserDto(userLoginDto);
                status= true;
            } catch (NotFoundException e) {
//                e.printStackTrace();
//                e.getMessage();
                status= false;
            }
        }
        return status;
    }


    public UserDto getUserDto(UserLoginDto userLoginDto){
        return userService.getByField(userLoginDto.getUsername());
    }

}
