package epsi.javamspr.springbootapi.Services;

import epsi.javamspr.springbootapi.Models.User;

import java.util.Map;

public interface UserService {
    Map<String, Object> getUsers() throws Exception;
    Object getUser () throws Exception;
    String postImage(String imageUrl) throws  Exception;
}