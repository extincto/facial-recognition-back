package epsi.javamspr.springbootapi.Services;
import java.util.Map;

public interface ToolService {
    Map<String, Object> getTools () throws Exception;
    Object getTool () throws Exception;
}