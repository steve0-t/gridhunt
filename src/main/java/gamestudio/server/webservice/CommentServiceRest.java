package gamestudio.server.webservice;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import gamestudio.entity.Comment;
import gamestudio.service.CommentService;

@RestController
@RequestMapping("/api/comment")
public class CommentServiceRest {
  @GetMapping("/{game}/comments")
  public List<Comment> getComments(@PathVariable String game) {
    return commentService.getComments(game);
  }

  @PostMapping
  public void addComment(@RequestBody Comment comment) {
    commentService.addComment(comment);
  }

  @Autowired private CommentService commentService;
}
