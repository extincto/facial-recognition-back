package epsi.javamspr.springbootapi.Controllers;

import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import com.amazonaws.services.rekognition.AmazonRekognition;
import com.amazonaws.services.rekognition.AmazonRekognitionClientBuilder;
import com.amazonaws.services.rekognition.model.AmazonRekognitionException;
import com.amazonaws.services.rekognition.model.DetectTextRequest;
import com.amazonaws.services.rekognition.model.DetectTextResult;
import com.amazonaws.services.rekognition.model.Image;
import com.amazonaws.services.rekognition.model.TextDetection;

public class AndroidController {
    public static  List<TextDetection> AndroidGetText(String filePath, String photo) throws Exception {

        List<TextDetection> textDetected =  new ArrayList<>();


        AmazonRekognition rekognitionClient = AmazonRekognitionClientBuilder.defaultClient();

        Path path = Paths.get(filePath + photo);
        ByteBuffer bytes = ByteBuffer.wrap(Files.readAllBytes(path));

//        ByteBuffer imageBytes;
//        try (InputStream inputStream = new FileInputStream(new File(photo))) {
//            imageBytes = ByteBuffer.wrap(IOUtils.toByteArray(inputStream));
//        }
        DetectTextRequest request = new DetectTextRequest()
                .withImage(new Image().withBytes(bytes));


        try {
            DetectTextResult result = rekognitionClient.detectText(request);
            List<TextDetection> textDetections = result.getTextDetections();

            System.out.println("Detected lines and words for " + photo);
            for (TextDetection text : textDetections) {


                //print parent words only. These have parent id == null.
                //non-parent words are part of other sentences already detected.
                if (text.getParentId() == null) {

                    System.out.print(" Word: " + text.getDetectedText());
                    System.out.print(", Confidence: " + text.getConfidence().toString());
                    System.out.print(", Id : " + text.getId());
                    System.out.print(", Parent Id: " + text.getParentId());
                    System.out.print(", Type: " + text.getType());
                    textDetected = textDetections;
                    System.out.println();
                }

            }
        } catch (AmazonRekognitionException e) {
            e.printStackTrace();
        }

        rekognitionClient.shutdown();
        return textDetected;
    }

}
