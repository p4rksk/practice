package shop.mtcoding.blog.board;

import jdk.dynalink.beans.StaticClass;
import lombok.Data;

public class BoardRequest { //=DTO
    //게시글 쓰기 POST 요청 DTO 만들기
    @Data
    public static class SaveDTO{
        private String author;
        private String title;
        private String content;
    }

    @Data
    public static class UpdateDTO{
        private String author;
        private String title;
        private String content;
    }

}
