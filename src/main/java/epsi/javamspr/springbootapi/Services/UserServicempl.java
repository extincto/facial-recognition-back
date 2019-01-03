package epsi.javamspr.springbootapi.Services;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;

@Service
public class UserServicempl implements UserService {
    public Map<String, Object> data;

    @Override
    public Map<String, Object> getUsers() throws Exception {
            Firestore db = FirestoreClient.getFirestore();
            ApiFuture<QuerySnapshot> future = db.collection("Users").get();
            List<QueryDocumentSnapshot> documents = future.get().getDocuments();
            for (QueryDocumentSnapshot document : documents) {
                System.out.println(document.getData());
                data = document.getData();
        }
            return data;
    }
    public Object getUser() throws Exception {
        Firestore db = FirestoreClient.getFirestore();
        DocumentReference docRef = db.collection("Users").document("1");
        ApiFuture<DocumentSnapshot> future = docRef.get();
        DocumentSnapshot document = future.get();
        if (document.exists()) {
            System.out.println("User found : " + document.getData());
            data = document.getData();
            return data;
        } else {
            System.out.println("No such users!");
        }
        return null;
    }
}