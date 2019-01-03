package epsi.javamspr.springbootapi.Services;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;

@Service
public class ToolServicempl implements ToolService {
    public Map<String, Object> data;

    @Override
    public Map<String, Object> getTools() throws Exception {
        Firestore db = FirestoreClient.getFirestore();
        ApiFuture<QuerySnapshot> future = db.collection("Tools").get();
        List<QueryDocumentSnapshot> documents = future.get().getDocuments();
        for (QueryDocumentSnapshot document : documents) {
            data = document.getData();
            System.out.println(document.getData());
        }
        return data;
    }
    public Object getTool() throws Exception {
        Firestore db = FirestoreClient.getFirestore();
        DocumentReference docRef = db.collection("Tools").document("1");
        ApiFuture<DocumentSnapshot> future = docRef.get();
        DocumentSnapshot document = future.get();
        if (document.exists()) {
            System.out.println("Material found: " + document.getData());
            data = document.getData();
            return data;
        } else {
            System.out.println("No such Material!");
        }
        return null;
    }
}