package epsi.javamspr.springbootapi.Services;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import epsi.javamspr.springbootapi.Models.Tool;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ToolServicempl implements ToolService {
    /**
     * Return all documents in the cities collection.
     *
     * @return list of documents
     */
    public List<Tool> getTools() throws Exception {
        Firestore db = FirestoreClient.getFirestore();
        ApiFuture<QuerySnapshot> future = db.collection("Tools").get();
        List<Tool> ToolList = new ArrayList<>();
        List<QueryDocumentSnapshot> documents = future.get().getDocuments();
        for (QueryDocumentSnapshot document : documents) {
            ToolList.add(( document.toObject(Tool.class).withId(document.getId())));
            System.out.println(document.getId() + " => " + document.getData());
        }
        return ToolList;
    }
    public Object getTool() throws Exception {
        Firestore db = FirestoreClient.getFirestore();
        DocumentReference docRef = db.collection("Tools").document("1");
        ApiFuture<DocumentSnapshot> future = docRef.get();
        DocumentSnapshot document = future.get();
        if (document.exists()) {
            System.out.println("Material found: " + document.getData());
        } else {
            System.out.println("No such Material!");
        }
        return null;
    }
    public List<Tool> postTools() throws Exception {
        return null;
    }

}



//    @Override
//    public List<QueryDocumentSnapshot> getTools() throws Exception {
//        Firestore db = FirestoreClient.getFirestore();
//        ApiFuture<QuerySnapshot> future = db.collection("Tools").get();
//        List<QueryDocumentSnapshot> documents = future.get().getDocuments();
//        for (QueryDocumentSnapshot document : documents) {
//            System.out.println(document.getData());
//            document.toObject(Tool.class);
//        }
//        return documents;
//    }