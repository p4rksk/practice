package shop.mtcoding.blog.board;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class BoardController {
    private final BoardRepository boardRepository;

    @GetMapping({"/", "/board"})
    public String index(HttpServletRequest request) {

        List<Board> boardList = boardRepository.findAll();
        request.setAttribute("boardList", boardList);
        return "index";
    }

    @GetMapping("/board/saveForm")
    public String saveForm() {
        return "board/saveForm";
    }

    @GetMapping("/board/{no}/updateForm")
    public String updateForm(@PathVariable int no, HttpServletRequest request) {
        Board board = boardRepository.findByNo(no);
        if (board.getTitle().length()>20){
            return "error/40x";
        }

        if (board.getContent().length()>20){
            return "error/40x";
        }

        //가방에 담기
        request.setAttribute("board",board);
        return "board/updateForm";
    }

    @PostMapping("/board/save")
    public String save(BoardRequest.SaveDTO requestDTO,HttpServletRequest request){
       //유효성 검사.
        System.out.println(requestDTO);

        if(requestDTO.getTitle().length()>20){
            request.setAttribute("status", 400);
            request.setAttribute("msg","title의 길이가 20자를 초과했습니다.");

            return "error/40x";
        }

        if (requestDTO.getContent().length()>20){
            request.setAttribute("status",400);
            request.setAttribute("msg","content의 길이가 20자를 초과했습니다.");

            return "error/40x";
        }

        //모델(dao)에게 위임하기
        //가방(request)에 담기
        boardRepository.save(requestDTO);


        return "redirect:/";
    }

    @PostMapping("/board/{no}/update")
    public String update(@PathVariable int no, BoardRequest.UpdateDTO requestDTO){
        System.out.println("!11");


        boardRepository.update(requestDTO, no);
        System.out.println("2222222");

        return "redirect:/";
    }



    @PostMapping("/board/{no}/delete")
    public String delete(@PathVariable int no){
        boardRepository.deleteByNo(no);
        return "redirect:/";
    }
}
