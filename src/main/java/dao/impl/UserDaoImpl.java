package dao.impl;

import entity.Role;
import entity.User;
import entity.User.UserQueries;
import utils.MD5HashPassword;

//import javax.jws.soap.SOAPBinding;
import java.util.List;


public class UserDaoImpl extends DaoCRUDAbsImpl<User> {

    public UserDaoImpl() {
        super();
        init();
    }

    protected void init() {
        for (UserQueries userQueries : User.UserQueries.values()) {
            sqlQueries.put(userQueries.getSqlQuery(), userQueries);
        }
    }

    protected User createInstance(String[] args) {
//        return new User(
//                Long.parseLong(args[0] == null ? "0" : args[0]),
//                args[1] == null ? "" : args[1],
//                args[2] == null ? "" : args[2],
//                args[3] == null ? "" : args[3],
//                args[4] == null ? "" : args[4],
//                args[5] == null ? "" : args[5]
//        );

        return  User.builder()
                .id(Long.parseLong(args[0] == null ? "0" : args[0]))
                .firstName(args[1] == null ? "" : args[1])
                .lastName(args[2] == null ? "" : args[2])
                .username(args[3] == null ? "" : args[3])
                .email(args[4] == null ? "" : args[4])
                .userRole(args[5] == null ? Role.USER : Role.valueOf(args[5]))
                .build();
    }

    protected String[] getUpdateFields(User user) {
        String[] result = new String[6];
        String[] allFields = getFields(user);
        result[0] = allFields[0]; // first_name
        result[1] = allFields[1]; // last_name
        result[2] = allFields[2]; // username
        result[3] = allFields[3]; // email
        result[4] = allFields[4]; // password
        result[5] = user.getId().toString();
        return result;
    }

    protected String[] getFields(User user) {

        String[] fields = new String[5];

        fields[0] = user.getFirstName();
        fields[1] = user.getLastName();
        fields[2] = user.getUsername();
        fields[3] = user.getEmail();
        fields[4] = MD5HashPassword.hashPassword(user.getPassword());
       // fields[5] = user.getUserRole().toString();

        return fields;
    }
}
