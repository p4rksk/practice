package shop.mtcoding.blog.board;


import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Table(name = "board_tb")
@Data
@Entity
public class Board {
    //id가 없으니 게시물 번호에다가 pk걸기
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)//auto_increment
    private int no;

    @Column(length = 20)
    private String title;

    @Column(length = 20)
    private String content;

    private String author;
}
