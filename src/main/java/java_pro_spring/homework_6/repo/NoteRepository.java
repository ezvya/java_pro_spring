package java_pro_spring.homework_6.repo;

import java_pro_spring.homework_6.domain.Note;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoteRepository extends JpaRepository<Note, Long> {
}
