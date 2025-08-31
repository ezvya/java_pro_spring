package java_pro_spring.homework_5.entities;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "bands")
public class Band {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String bandName;

    @OneToMany(mappedBy = "band",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY)
    private List<Album> albums = new ArrayList<>();

    public Band() {}
    public Band(String bandName) {
        this.bandName = bandName;
    }

    public void addAlbum(Album album) {
        albums.add(album);
        album.setBand(this);
    }

    public void removeAlbum(Album album) {
        albums.remove(album);
        album.setBand(null);
    }


    // getters & toString
    public Long getId() { return id; }
    public String getBandName() { return bandName; }
    public List<Album> getAlbums() { return albums; }
}
