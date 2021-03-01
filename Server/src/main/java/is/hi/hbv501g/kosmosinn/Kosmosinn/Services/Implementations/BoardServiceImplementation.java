package is.hi.hbv501g.kosmosinn.Kosmosinn.Services.Implementations;

import is.hi.hbv501g.kosmosinn.Kosmosinn.Entities.Board;
import is.hi.hbv501g.kosmosinn.Kosmosinn.Entities.Topic;
import is.hi.hbv501g.kosmosinn.Kosmosinn.Repositories.BoardRepository;
import is.hi.hbv501g.kosmosinn.Kosmosinn.Services.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BoardServiceImplementation implements BoardService {
    BoardRepository repository;

    @Autowired
    BoardServiceImplementation(BoardRepository boardRepository) {
       this.repository = boardRepository;
    }

    @Override
    public Board save(Board board) {
        return repository.save(board);
    }

    @Override
    public void delete(Board board) {
        repository.delete(board);
    }

    @Override
    public List<Board> findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<Board> findById(long id) {
        return repository.findById(id);
    }

    @Override
    public Board findByName(String name) {
        return repository.findByName(name);
    }
}
