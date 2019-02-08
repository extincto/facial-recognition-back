package epsi.javamspr.springbootapi.Controllers;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.amazonaws.services.rekognition.AmazonRekognition;
import com.amazonaws.services.rekognition.AmazonRekognitionClientBuilder;
import com.amazonaws.services.rekognition.model.*;
import com.amazonaws.services.rekognition.model.Image;
import com.amazonaws.util.IOUtils;
import epsi.javamspr.springbootapi.Models.Informations;
import jdk.nashorn.internal.runtime.regexp.joni.Regex;

import javax.imageio.ImageIO;

public class AndroidController {
    public static Informations AndroidGetText(String filePath, String photo) throws Exception {
        Informations results = new Informations();

        List<FaceDetail> faceId = new ArrayList<>();
        List<TextDetection> textDetected = new ArrayList<>();

        BufferedImage image = null;
        ByteBuffer imageBytes = null;

        AmazonRekognition rekognitionClient = AmazonRekognitionClientBuilder.defaultClient();


        try (InputStream inputStream = new FileInputStream(new File(filePath + photo))) {
            imageBytes = ByteBuffer.wrap(IOUtils.toByteArray(inputStream));

        }
        DetectTextRequest request = new DetectTextRequest()
                .withImage(new Image().withBytes(imageBytes));


        try {
            DetectTextResult result = rekognitionClient.detectText(request);
            List<TextDetection> textDetections = result.getTextDetections();

            System.out.println("Detected lines and words for " + photo);
            for (TextDetection text : textDetections) {


                //print parent words only. These have parent id == null.
                //non-parent words are part of other sentences already detected.
                if (text.getParentId() == null) {

                    TextRegularExp();
                    System.out.print(" Word: " + text.getDetectedText());
                    System.out.print(", Confidence: " + text.getConfidence().toString());
                    System.out.print(", Id : " + text.getId());
                    System.out.print(", Parent Id: " + text.getParentId());
                    System.out.print(", Type: " + text.getType());
                    results.setTextDetected(textDetections);
                    System.out.println();
                }

            }
        } catch (AmazonRekognitionException e) {
            e.printStackTrace();
        }
        InputStream imageBytesStream;
        imageBytesStream = new ByteArrayInputStream(imageBytes.array());

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image = ImageIO.read(imageBytesStream);

        int height = image.getHeight();
        int width = image.getWidth();


        System.out.println("Image Information:");
        System.out.println(photo);
        System.out.println("Image Height: " + (height));
        System.out.println("Image Width: " + (width));

        try {
            DetectFacesRequest request2 = new DetectFacesRequest()
                    .withImage(new Image()
                            .withBytes((imageBytes)))
                    .withAttributes(Attribute.ALL);


            DetectFacesResult result = rekognitionClient.detectFaces(request2);
            List<FaceDetail> faceDetails = result.getFaceDetails();

            if (faceDetails == null || faceDetails.isEmpty()) {
                System.out.println("No faces detected");
            }


            System.out.println("*********   Faces found : " + faceDetails.size());

            for (FaceDetail face : faceDetails) {
                addBoundingBoxPositions(image, height,
                        width,
                        face.getBoundingBox(),
                        result.getOrientationCorrection());
                CropFace(image, face.getBoundingBox());


                String Path = new File("").getAbsolutePath();
                FileOutputStream osf = new FileOutputStream(Path.concat("\\src\\main\\resources\\") + photo);
                ImageIO.write(image, "jpg", new File(Path.concat("\\src\\main\\resources\\") + photo));
                osf.flush();
                osf.close();


                Gender gender = face.getGender();
                Smile smile = face.getSmile();
                List<Emotion> emotions = face.getEmotions();
                Eyeglasses glasses = face.getEyeglasses();

                AgeRange ageRange = face.getAgeRange();

                System.out.println("The detected face is estimated to be between "
                        + ageRange.getLow().toString() + " and " + ageRange.getHigh().toString()
                        + " years old. The gender is " + gender.getValue() + ". The person is " + (smile.isValue() ? "smiling" : "not smiling") + "."
                        + " The person is " + (glasses.isValue() ? "wearing " : "not wearing ") + "eyeglasses.");
                System.out.print("Emotions shown ");
                emotions.stream().filter(e -> (e.getConfidence() > 95.0F)).forEach(e -> System.out.print(e.getType() + ", "));
                System.out.println();
            }
            results.setFaceId(faceDetails);

        } catch (AmazonRekognitionException e) {
            e.printStackTrace();
        }
        rekognitionClient.shutdown();
        return results;
    }

    public static void addBoundingBoxPositions(BufferedImage image, int imageHeight, int imageWidth, BoundingBox box, String rotation) {

        float left = 0;
        float top = 0;

        if (rotation == null) {
            //System.out.println("No estimated estimated orientation. Check Exif data.");
            return;
        }
        //Calculate face position based on image orientation.
        switch (rotation) {
            case "ROTATE_0":
                left = imageWidth * box.getLeft();
                top = imageHeight * box.getTop();
                break;
            case "ROTATE_90":
                left = imageHeight * (1 - (box.getTop() + box.getHeight()));
                top = imageWidth * box.getLeft();
                break;
            case "ROTATE_180":
                left = imageWidth - (imageWidth * (box.getLeft() + box.getWidth()));
                top = imageHeight * (1 - (box.getTop() + box.getHeight()));
                break;
            case "ROTATE_270":
                left = imageHeight * box.getTop();
                top = imageWidth * (1 - box.getLeft() - box.getWidth());
                break;
            default:
                System.out.println("No estimated orientation information. Check Exif data.");
                return;
        }

        Graphics2D graph = image.createGraphics();
        Graphics2D g2d = image.createGraphics();
        g2d.setColor(Color.YELLOW);
        g2d.setStroke(new BasicStroke(3));
        g2d.drawRect(Math.round(left), Math.round(top), Math.round(imageHeight * box.getHeight()), Math.round(imageWidth * box.getWidth()));
        g2d.dispose();


        //Display face location information.
        System.out.println("Left: " + (int) left);
        System.out.println("Top: " + (int) top);
        System.out.println("Face Width: " + (int) (imageWidth * box.getWidth()));
        System.out.println("Face Height: " + (int) (imageHeight * box.getHeight()));
    }


    public static BufferedImage CropFace(BufferedImage image, BoundingBox box) throws Exception {
        int boxLeft = (int) (box.getLeft() * image.getWidth());
        int boxTop = (int) (box.getTop() * image.getHeight());
        int boxWidth = (int) (box.getWidth() * image.getWidth());
        int boxHeight = (int) (box.getHeight() * image.getHeight());

        BufferedImage dest = image.getSubimage(boxLeft, boxTop, boxWidth, boxHeight);
        String FileName = "cropedImage.jpg";
        String cropedImage = new File("").getAbsolutePath();
        FileOutputStream osf = new FileOutputStream(cropedImage.concat("\\src\\main\\resources\\") + FileName);
        ImageIO.write(dest, "jpg", new File(cropedImage.concat("\\src\\main\\resources\\") + FileName));
        return dest;
    }

    public static void TextRegularExp() {

        String Nom = "CARTE NATIONALE D'IDENTITE No: 880692310285 Nationalite Francaise";
        String NomPattern = "(CARTE NATIONALE D'IDENTITER.+:.+(\\d+))";

//        String Prenom;
//        String PrenomPattern = "";
//
//        String CarteId = "";
//        String CarteIdPattern = "";

        // Create a Pattern object
        Pattern r = Pattern.compile(NomPattern);

        Matcher m = r.matcher(Nom);
        if (m.find( )) {
            System.out.println("Found value: " + m.start());

        } else {
            System.out.println("NO MATCH");
        }
    }
}
