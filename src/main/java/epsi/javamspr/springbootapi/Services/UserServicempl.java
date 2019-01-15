package epsi.javamspr.springbootapi.Services;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import epsi.javamspr.springbootapi.Controllers.CompareFaces;
import epsi.javamspr.springbootapi.Models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.image.BufferedImage;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class UserServicempl implements UserService {
    public Map<String, Object> data;

    @Autowired
    PicturesServicempl picturesServicempl;
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

    public String postImage(String imageUrl) throws  Exception {
        List<String> listeUrl = picturesServicempl.getPictures();
        CompareFaces.decodeToImage(imageUrl, "1");
        for(String url: listeUrl) {
            TimeUnit.SECONDS.sleep(10);
            CompareFaces.decodeToImage(url, "2");
            CompareFaces.CompareForAuthentication();
        }
        try{
            String filePath = new File("").getAbsolutePath();
            Files.delete(Paths.get("" + filePath.concat("\\src\\main\\resources\\image1.jpeg")));
            Files.delete(Paths.get("" + filePath.concat("\\src\\main\\resources\\image2.jpeg")));
        }catch(Exception e){
            e.printStackTrace();
        }
        return "bien joué!";
    }


//    public Object postImage(User user) throws  Exception {
//        Number Id = user.getId();
//        String ImageUrl = user.getImageUrl();
//        String path = "Users";
//        Firestore db = FirestoreClient.getFirestore();
//        // Update an existing document
//        DocumentReference docRef = db.collection(path).document( Id.toString());
//        // (async) Update one field
//        ApiFuture<WriteResult> future = docRef.update("ImageUrl", ImageUrl);
//        WriteResult result = future.get();
//        System.out.println("Write result: " + result);
//        return user;
//    }


}