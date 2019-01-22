package epsi.javamspr.springbootapi.Services;
import epsi.javamspr.springbootapi.Models.Tool;

import java.util.List;

public interface ToolService {
    List<Tool> getTools () throws Exception;
    Object getTool () throws Exception;
    List<Tool> postTools() throws  Exception;
}