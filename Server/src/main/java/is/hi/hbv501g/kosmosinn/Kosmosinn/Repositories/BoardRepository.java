package is.hi.hbv501g.kosmosinn.Kosmosinn.Repositories;

import is.hi.hbv501g.kosmosinn.Kosmosinn.Entities.Board;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BoardRepository extends JpaRepository<Board, Long> {
    Board save(Board board);
    void delete(Board board);
    List<Board> findAll();
    Optional<Board> findById(long id);
    Board findByName(String name);
}
