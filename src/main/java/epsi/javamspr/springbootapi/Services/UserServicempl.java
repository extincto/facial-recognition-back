package epsi.javamspr.springbootapi.Services;
import com.amazonaws.services.rekognition.AmazonRekognition;
import com.amazonaws.services.rekognition.AmazonRekognitionClientBuilder;
import com.amazonaws.services.rekognition.model.CompareFacesMatch;
import com.amazonaws.services.rekognition.model.CompareFacesRequest;
import com.amazonaws.services.rekognition.model.CompareFacesResult;
import com.amazonaws.services.rekognition.model.ComparedFace;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import epsi.javamspr.springbootapi.Controllers.CompareFaces;
import epsi.javamspr.springbootapi.Models.Picture;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.utils.StringUtils;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class UserServicempl implements UserService {
    public Map<String, Object> data;
    public Map<String, Object> userData;
    public  Float conf;


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

    public Object postImage(String imageUrl) throws  Exception {
        List<Picture> listeUrl = picturesServicempl.getPictures();
        CompareFaces.decodeToImage(imageUrl, "1");
        for(Picture url: listeUrl) {
            CompareFaces.decodeToImage(url.getImageUrl(), "2");
           float rslt = CompareFaces.CompareForAuthentication();
            if (rslt >= 90) {
                //FIREBASE GET RECOGNIZED USER DATA
                Firestore db = FirestoreClient.getFirestore();
                CollectionReference users = db.collection("Users");
                // Create a query against the collection.
                Query query = users.whereEqualTo("Id", url.getIdUser());
                // retrieve  query results asynchronously using query.get()
                ApiFuture<QuerySnapshot> querySnapshot = query.get();

                for (DocumentSnapshot document : querySnapshot.get().getDocuments()) {
                    userData = document.getData();
                    userData.put("ImageUrl", url);
                    System.out.println(document.getData());
                }

            }
        }
        try{
            String filePath = new File("").getAbsolutePath();
            Files.delete(Paths.get("" + filePath.concat("\\src\\main\\resources\\image1.jpeg")));
            Files.delete(Paths.get("" + filePath.concat("\\src\\main\\resources\\image2.jpeg")));
        }catch(Exception e){
            e.printStackTrace();
        }
        return userData;
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