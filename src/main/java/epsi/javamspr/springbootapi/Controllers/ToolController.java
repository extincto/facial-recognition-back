package epsi.javamspr.springbootapi.Controllers;
import epsi.javamspr.springbootapi.Models.Tool;
import epsi.javamspr.springbootapi.Services.ToolServicempl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

@RestController
public class ToolController {

    @Autowired
    ToolServicempl service;
    private final static String posttools = "/posttools";

    @RequestMapping(method = RequestMethod.GET, value = "/tools", produces = "application/json; charset=UTF-8")
    @ResponseBody
    public List<Tool> getTools() throws Exception {

        List<Tool> ToolList = (List<Tool>) service.getTools();

        return ToolList;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/tool")
    public void getTool() throws Exception {
        service.getTool();
    }

    @PostMapping(posttools)
    public ArrayList<Integer> postTools(@RequestBody ArrayList<Integer> toolist) throws Exception {
        ArrayList<Integer> tool_list = service.postTools(toolist);
        return tool_list;
    }
}

//    @PostMapping(getUrl)
//    public Object postImage(@RequestBody String imageUrl) throws Exception {
//        public Object postImage(@RequestBody String imageUrl) throws Exception {
//        return userData;
//    }
