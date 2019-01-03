package epsi.javamspr.springbootapi.Controllers;
import epsi.javamspr.springbootapi.Services.ToolServicempl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class ToolController {

    @Autowired
    ToolServicempl service;

    @RequestMapping(method = RequestMethod.GET, value = "/tools")
    public void getTools() throws Exception {
        service.getTools();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/tool")
    public void getTool() throws Exception {
        service.getTool();
    }
}


