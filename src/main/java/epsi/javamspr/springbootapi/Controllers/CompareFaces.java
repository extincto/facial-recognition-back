package epsi.javamspr.springbootapi.Controllers;

import java.io.*;
import java.nio.ByteBuffer;
import java.util.List;

import com.amazonaws.services.rekognition.AmazonRekognition;
import com.amazonaws.services.rekognition.AmazonRekognitionClientBuilder;
import com.amazonaws.services.rekognition.model.*;
import com.amazonaws.util.IOUtils;
import software.amazon.awssdk.utils.StringUtils;
import com.amazonaws.services.rekognition.model.SearchFacesByImageResult;


public class CompareFaces {
    public static void main(String[] args) throws Exception{
    }
    public static void decodeToImage(String imageString, String id) throws Exception {
        String filePath = new File("").getAbsolutePath();
        String imageDataBytes = imageString.substring(imageString.indexOf(",")+1);
        byte[] btDataFile = new sun.misc.BASE64Decoder().decodeBuffer(imageDataBytes);
        File of = new File(filePath.concat("\\src\\main\\resources\\")+"image"+id+".jpeg");
        FileOutputStream osf = new FileOutputStream(of);
        osf.write(btDataFile);
        osf.flush();
        osf.close();
    }

    public static Float CompareForAuthentication() throws Exception{
        float confidence = 0;
        String filePath = new File("").getAbsolutePath();
        String sourceImage = filePath.concat("\\src\\main\\resources\\image1.jpeg");
        String targetImage = filePath.concat("\\src\\main\\resources\\image2.jpeg");
        ByteBuffer sourceImageBytes=null;
        ByteBuffer targetImageBytes=null;
        Float similarityThreshold = 70F;


        AmazonRekognition rekognitionClient = AmazonRekognitionClientBuilder.defaultClient();
        //Load source and target images
        try (InputStream inputStream = new FileInputStream(new File(sourceImage))) {
            sourceImageBytes = ByteBuffer.wrap(IOUtils.toByteArray(inputStream));
        }
        catch(Exception e)
        {
            System.out.println("Failed to load source image " + sourceImage);
            System.exit(1);
        }
        try (InputStream inputStream = new FileInputStream(new File(targetImage))) {
            targetImageBytes = ByteBuffer.wrap(IOUtils.toByteArray(inputStream));
        }
        catch(Exception e)
        {
            System.out.println("Failed to load target images: " + targetImage);
            System.exit(1);
        }

        // create request
        Image source=new Image()
                .withBytes(sourceImageBytes);
        Image target=new Image()
                .withBytes(targetImageBytes);

        CompareFacesRequest request = new CompareFacesRequest()
                .withSourceImage(source)
                .withTargetImage(target)
                .withSimilarityThreshold(similarityThreshold);

        // call operation
        CompareFacesResult compareFacesResult=rekognitionClient.compareFaces(request);

        // Display results
        List <CompareFacesMatch> faceDetails = compareFacesResult.getFaceMatches();
        for (CompareFacesMatch match: faceDetails){
            ComparedFace face= match.getFace();
            BoundingBox position = face.getBoundingBox();

            if (StringUtils.isNotBlank(face.getConfidence().toString())){
                confidence = face.getConfidence();
                System.out.println("Face matches with " + face.getConfidence().toString() + "% confidence.");
            }
        }
        return confidence;
    }
}