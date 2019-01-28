package epsi.javamspr.springbootapi.Services;
import epsi.javamspr.springbootapi.Models.Tool;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public interface ToolService {
    List<Tool> getTools () throws Exception;
    Object getTool () throws Exception;
    ArrayList<Integer> postTools(ArrayList<Integer> toolist) throws  Exception;
    ArrayList<Integer> retourTools(ArrayList<Integer> toolist) throws  Exception;
}