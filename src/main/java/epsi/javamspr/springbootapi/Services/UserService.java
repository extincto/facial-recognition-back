package epsi.javamspr.springbootapi.Services;


import java.util.Map;

public interface UserService {
    Map<String, Object> getUsers() throws Exception;
    Object getUser () throws Exception;
    Object postImage(String imageUrl) throws  Exception;
    int getId() throws  Exception;
    Object AndroidGetText(String Path, String photo) throws  Exception;
}