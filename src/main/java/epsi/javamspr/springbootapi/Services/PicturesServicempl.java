package epsi.javamspr.springbootapi.Services;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.firebase.cloud.FirestoreClient;
import epsi.javamspr.springbootapi.Models.Picture;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PicturesServicempl implements PictureService {

    @Override
    public List<Picture>  getPictures() throws Exception {
        Firestore db = FirestoreClient.getFirestore();
        ApiFuture<QuerySnapshot> future = db.collection("Pictures").get();
        List<Picture> PictureList = new ArrayList<>();
        List<QueryDocumentSnapshot> documents = future.get().getDocuments();
        for (QueryDocumentSnapshot document : documents) {
            PictureList.add((document.toObject(Picture.class)));
        }
        return PictureList;
    }
}
