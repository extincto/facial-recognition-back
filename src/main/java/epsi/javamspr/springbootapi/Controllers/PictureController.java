package epsi.javamspr.springbootapi.Controllers;

import epsi.javamspr.springbootapi.Services.PictureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PictureController {

    @Autowired
    PictureService service;

    @RequestMapping(method = RequestMethod.GET, value = "/pictures")
    public void getPictures() throws Exception {
        service.getPictures();
    }
}
