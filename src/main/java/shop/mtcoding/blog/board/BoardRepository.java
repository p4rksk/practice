package shop.mtcoding.blog.board;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Repository // 데이터 베이스 연동, ioc컨테이너 등록
public class BoardRepository { //dao
    private final EntityManager em;

    public List<Board> findAll(){
        Query query = em.createNativeQuery("select * from board_tb order by no desc", Board.class);
        return query.getResultList();
    }

    @Transactional
    public void save(BoardRequest.SaveDTO requestDTO){
        Query query = em.createNativeQuery("insert into board_tb(title, content,author,created_at) values(?,?,?,now())");
                query.setParameter(1,requestDTO.getTitle());
                query.setParameter(2,requestDTO.getContent());
                query.setParameter(3,requestDTO.getAuthor());

                query.executeUpdate();
    }

    public Board findByNo(int no){
        Query query = em.createNativeQuery("select * from board_tb where no = ?", Board.class);
        query.setParameter(1,no);

        Board board = (Board) query.getSingleResult();
        return board;

    }
    @Transactional
    public void update(BoardRequest.UpdateDTO requestDTO, int no) {
        Query query = em.createNativeQuery("update board_tb set title=?, content=?, author = ? where no = ?");
        query.setParameter(1, requestDTO.getTitle());
        query.setParameter(2, requestDTO.getContent());
        query.setParameter(3, requestDTO.getAuthor());
        query.setParameter(4, no);

        query.executeUpdate();
    }
}
