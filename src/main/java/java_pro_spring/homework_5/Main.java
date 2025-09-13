package java_pro_spring.homework_5;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import java_pro_spring.homework_5.entities.Album;
import java_pro_spring.homework_5.entities.Band;

import java.util.List;

public class Main {

    public static void main(String[] args) {
        EntityManagerFactory emFactory = Persistence.createEntityManagerFactory("bandsPU");
        EntityManager entityManager = emFactory.createEntityManager();

        entityManager.getTransaction().begin();

        Band band = new Band("Queens of the Stone Age");
        band.addAlbum(new Album("Rated R"));
        band.addAlbum(new Album("Songs for the Deaf"));
        band.addAlbum(new Album("Lullabies to Paralyze"));

        entityManager.persist(band);
        entityManager.getTransaction().commit();

        entityManager.clear();

        Band bandFetchedFromDb = entityManager.find(Band.class, band.getId());

        System.out.println("Loaded Band: " + bandFetchedFromDb.getBandName() + " with ID: " + bandFetchedFromDb.getId());
        System.out.println("Albums saved: " + bandFetchedFromDb.getAlbums().size());

        entityManager.getTransaction().begin();

        List<Album> albumsList = bandFetchedFromDb.getAlbums();

        albumsList.forEach(album -> System.out.println("Album found: " + album.getTitle() + " with ID: " + album.getId()));

        Album albumForRemoval = albumsList.get(0);
        bandFetchedFromDb.removeAlbum(albumForRemoval);

        entityManager.getTransaction().commit();

        entityManager.getTransaction().begin();
        entityManager.remove(bandFetchedFromDb);
        entityManager.getTransaction().commit();

        entityManager.close();
        emFactory.close();
    }
}