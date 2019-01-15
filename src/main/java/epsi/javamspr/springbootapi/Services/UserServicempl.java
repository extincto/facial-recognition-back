package epsi.javamspr.springbootapi.Services;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import epsi.javamspr.springbootapi.Models.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class UserServicempl implements UserService {
    public Map<String, Object> data;
    UserService service;

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
    public Object postImage(User user) throws  Exception {
        Number Id = user.getId();
        String ImageUrl = user.getImageUrl();
        String path = "Users";
        Firestore db = FirestoreClient.getFirestore();
        // Update an existing document
        DocumentReference docRef = db.collection(path).document( Id.toString());
        // (async) Update one field
        ApiFuture<WriteResult> future = docRef.update("ImageUrl", ImageUrl);
        WriteResult result = future.get();
        System.out.println("Write result: " + result);
        return user;
    }


}