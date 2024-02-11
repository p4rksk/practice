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

    public List<Board> findAll(int page){
        final int count =5;
        int value = page * 5;
        //value는 페이지 번호에 따라 가져올 데이터의 시작 인덱스를 계산하는 방식

        Query query = em.createNativeQuery("select * from board_tb order by no desc limit ?,?", Board.class);
        query.setParameter(1,value);
        query.setParameter(2,count);

        List<Board> boardList = query.getResultList();
        return boardList;
    }

    public int count() {
        Query query = em.createNativeQuery("select count(*) from board_tb");

        Long count = (Long) query.getSingleResult(); //integer를 넣을 경우 터짐 그래서 Long을 사용함

        return count.intValue();//Long 타입을 int 타입으로 형변환 해주는 메서드

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

    @Transactional
    public void deleteByNo(int no){
        Query query = em.createNativeQuery("delete from board_tb where no = ?");
        query.setParameter(1,no);
        query.executeUpdate();
    }
}
