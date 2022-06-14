package dtos;

import entities.Role;
import entities.User;

import java.util.ArrayList;
import java.util.List;

public class UserDTO
{
    String userName;
    List<String> roles;
    String userPass;

    public UserDTO(User user)
    {
        this.userName = user.getUserName();
        this.userPass = user.getUserPass();
        this.roles = getRoles(user.getRoleList());
    }

    public static List<UserDTO> getDTOs(List<User> users){
        List<UserDTO> userDTOS = new ArrayList<>();
        users.forEach(user->userDTOS.add(new UserDTO(user)));
        return userDTOS;
    }
    
    public User toUser () {
        return new User(this.userName, this.userPass);
    }

    public List<String> getRoles(List<Role> roles){
        List<String> stringRoles = new ArrayList<>();
        for (Role role : roles)
        {
            stringRoles.add(role.getRoleName());
        }
        return stringRoles;
    }
    
    
}
