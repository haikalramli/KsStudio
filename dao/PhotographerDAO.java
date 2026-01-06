package dao;

import model.Photographer;

public interface PhotographerDAO {
    boolean registerPhotographer(Photographer photographer);
    Photographer loginPhotographer(String email, String password);
    boolean photographerExists(String email);
    Photographer getPhotographerById(int photographerId);
    boolean updatePhotographer(Photographer photographer);
    boolean deletePhotographer(int photographerId);
}