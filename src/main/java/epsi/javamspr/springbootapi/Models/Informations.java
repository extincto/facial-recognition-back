package epsi.javamspr.springbootapi.Models;

import com.amazonaws.services.rekognition.model.FaceDetail;
import com.amazonaws.services.rekognition.model.TextDetection;

import java.util.ArrayList;
import java.util.List;

public class Informations {

    private List<FaceDetail> faceId = new ArrayList<FaceDetail>();
    private List<TextDetection> textDetected = new ArrayList<TextDetection>();

    public List<FaceDetail> getFaceId() {
        return faceId;
    }

    public void setFaceId(List<FaceDetail> faceId) {
        this.faceId = faceId;
    }

    public List<TextDetection> getTextDetected() {
        return textDetected;
    }

    public void setTextDetected(List<TextDetection> textDetected) {
        this.textDetected = textDetected;
    }
}
