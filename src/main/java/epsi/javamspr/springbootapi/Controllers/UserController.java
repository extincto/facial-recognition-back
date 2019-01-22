package epsi.javamspr.springbootapi.Controllers;
import epsi.javamspr.springbootapi.Models.User;
import epsi.javamspr.springbootapi.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    @Autowired
    UserService service;
    private final static String getUrl = "/login";

    @RequestMapping(method = RequestMethod.GET, value = "/users")
    public void getUsers() throws Exception {
        service.getUsers();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/user")
    public void getUser() throws Exception {
        service.getUser();
    }

    @PostMapping(getUrl)
    public Object postImage(@RequestBody String imageUrl) throws Exception {
        Object userData = service.postImage(imageUrl);
        return userData;
    }

//    @PostMapping(getUrl)
//    public ResponseEntity<User> postImage(@RequestBody User user){
//        System.out.println("get image url: " + user.getImageUrl());
//        return ResponseEntity.ok(user);
//    }

}