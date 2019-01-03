package epsi.javamspr.springbootapi.Controllers;
import org.springframework.beans.factory.annotation.Autowired;
import epsi.javamspr.springbootapi.Services.UserService;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    @Autowired
    UserService service;
    @RequestMapping(method = RequestMethod.GET, value = "/users")
    public void getUsers() throws Exception {
        service.getUsers();
    }


    @RequestMapping(method = RequestMethod.GET, value = "/user")
    public void getUser() throws Exception {
        service.getUser();
    }

}