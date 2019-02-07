package epsi.javamspr.springbootapi.Services;


import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import epsi.javamspr.springbootapi.Controllers.AndroidController;
import epsi.javamspr.springbootapi.Controllers.CompareFaces;
import epsi.javamspr.springbootapi.Models.Loan;
import epsi.javamspr.springbootapi.Models.Picture;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class UserServicempl implements UserService {
    public Map<String, Object> data;
    public Map<String, Object> userData;
    public int idUser;


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
                idUser = url.getIdUser();



                for (DocumentSnapshot document : querySnapshot.get().getDocuments()) {
                    userData = document.getData();
                    userData.put("ImageUrl", url);
                    System.out.println(document.getData());
                }

                // search loan of user and tool loaned
                ApiFuture<QuerySnapshot> future =
                        db.collection("Loans").whereEqualTo("UserId",  url.getIdUser()).get();
                List<QueryDocumentSnapshot> documents = future.get().getDocuments();
                List<Loan> Loan = new ArrayList<>();
                for (DocumentSnapshot document : documents) {
                    System.out.println(document.getId() + " => " + document.toObject(Loan.class));
                    Loan.add(document.toObject(Loan.class));
                }
                userData.put("ToolId",Loan.get(0).getToolId());

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
    public Object AndroidGetText(String Path, String photo) throws Exception {
        Object data = AndroidController.AndroidGetText(Path, photo);
        return data;

    }
    public int getId() throws  Exception {
        return idUser;
    }
}