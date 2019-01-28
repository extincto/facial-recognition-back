package epsi.javamspr.springbootapi.Services;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import epsi.javamspr.springbootapi.Models.Loan;
import epsi.javamspr.springbootapi.Models.Tool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ToolServicempl implements ToolService {
    /**
     * Return all documents in the cities collection.
     *
     * @return list of documents
     */
    @Autowired
    UserService UserService;

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
    public ArrayList<Integer> postTools(ArrayList<Integer> toolist) throws Exception {
        String path = "Tools";
        List<Tool> ToolList = new ArrayList<>();
        //get tool
        for(int id: toolist) {
            Firestore db = FirestoreClient.getFirestore();

            DocumentReference docRef = db.collection(path).document(String.valueOf(id));
            ApiFuture<DocumentSnapshot> future = docRef.get();
            DocumentSnapshot document = future.get();
            ToolList.add(document.toObject(Tool.class).withId(String.valueOf(id)));
        }

        // get loan id
        Firestore db = FirestoreClient.getFirestore();
        ApiFuture<QuerySnapshot> future =
                db.collection("Loans").whereEqualTo("UserId", UserService.getId()).get();
        List<QueryDocumentSnapshot> documents = future.get().getDocuments();
        List<Loan> Loan = new ArrayList<>();
        for (DocumentSnapshot document : documents) {
            Loan.add(document.toObject(Loan.class));
        }
        // update loan with current tool
        System.out.println("Write result1: " + Loan.get(0));
        DocumentReference docRef = db.collection("Loans").document(String.valueOf(Loan.get(0).getId()));
        ApiFuture<WriteResult> futureloan = docRef.update("ToolId", toolist);
        WriteResult result = futureloan.get();
        System.out.println("Write result2: " + result);

            for (Tool tool: ToolList) {
                DocumentReference docRefDecrement = db.collection(path).document(String.valueOf(tool.getId()));
                int decrementQuantity = tool.getQuantity() - 1;
                ApiFuture<WriteResult> futureDecrement = docRefDecrement.update("Quantity", decrementQuantity);
                WriteResult resultDecrement = futureDecrement.get();
                System.out.println("Write result: " + resultDecrement);
            }

        return toolist;
    }

    public ArrayList<Integer> retourTools(ArrayList<Integer> toolist) throws Exception {
        List<Tool> ToolList = new ArrayList<>();
        for(int id: toolist) {
            Firestore db = FirestoreClient.getFirestore();
            DocumentReference docRef = db.collection("Tools").document(String.valueOf(id));
            ApiFuture<DocumentSnapshot> future = docRef.get();
            DocumentSnapshot document = future.get();
            ToolList.add(document.toObject(Tool.class).withId(String.valueOf(id)));
        }

        // get loan id
        Firestore db = FirestoreClient.getFirestore();
        ApiFuture<QuerySnapshot> future =
                db.collection("Loans").whereEqualTo("UserId", UserService.getId()).get();
        List<QueryDocumentSnapshot> documents = future.get().getDocuments();
        List<Loan> Return = new ArrayList<>();
        for (DocumentSnapshot document : documents) {
            Return.add(document.toObject(Loan.class));
        }
        // update loan with current tool
        System.out.println("Write result1: " + Return.get(0));
        DocumentReference docRef = db.collection("Loans").document(String.valueOf(Return.get(0).getId()));
        ApiFuture<WriteResult> futureReturn = docRef.update("ToolId", toolist);
        WriteResult result = futureReturn.get();
        System.out.println("Write result2: " + result);

        for (Tool tool: ToolList) {
            DocumentReference docRefIncrement = db.collection("Tools").document(String.valueOf(tool.getId()));
            int IncrementQuantity = tool.getQuantity() + 1;
            ApiFuture<WriteResult> futureIncrement = docRefIncrement.update("Quantity", IncrementQuantity);
            WriteResult resultIncrement = futureIncrement.get();
            System.out.println("Write result: " + resultIncrement);
        }
        List<Tool> clear = new ArrayList<>(0);
//            toolist.update("ToolId", clear);
// Update and delete the "capital" field in the document
            ApiFuture<WriteResult> ClearArrayResult = docRef.update("ToolId", clear);
            System.out.println("CLEARED ARRAY FIREBASE : " + ClearArrayResult);
            System.out.println("ToolLIST RETURN ARRAY: " + toolist);


        return toolist;
    }
}