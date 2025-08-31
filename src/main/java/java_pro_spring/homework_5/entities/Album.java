package java_pro_spring.homework_5.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "albums")
public class Album {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @ManyToOne(fetch = FetchType.LAZY)
    private Band band;

    public Album() {}
    public Album(String title) {
        this.title = title;
    }

    public void setBand(Band band) {
        this.band = band;
    }

    public Long getId() { return id; }
    public String getTitle() { return title; }
}
