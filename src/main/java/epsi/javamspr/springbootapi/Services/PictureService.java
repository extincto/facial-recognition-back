package epsi.javamspr.springbootapi.Services;

import epsi.javamspr.springbootapi.Models.Picture;

import java.util.List;

public interface PictureService {

    List<Picture> getPictures() throws Exception;
}
