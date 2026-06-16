package gamestudio.service;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Date;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import gamestudio.entity.Comment;
import gamestudio.server.webservice.CommentServiceRest;

@WebMvcTest(CommentServiceRest.class)
class CommentServiceRestTest {
  @Test
  void shouldReturnCommentsForGame() throws Exception {
    List<Comment> comments =
        List.of(
            new Comment("player1", "game1", "Nice game", new Date()),
            new Comment("player2", "game1", "Cool", new Date()));

    Mockito.when(commentService.getComments("game1")).thenReturn(comments);

    mockMvc
        .perform(get("/api/comment/game1/comments"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.length()").value(2))
        .andExpect(jsonPath("$[0].player").value("player1"))
        .andExpect(jsonPath("$[1].player").value("player2"));
  }

  @Test
  void shouldAddComment() throws Exception {
    Comment comment = new Comment("player1", "game1", "Nice game", new Date());

    mockMvc
        .perform(
            post("/api/comment")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(comment)))
        .andExpect(status().isOk());

    Mockito.verify(commentService).addComment(Mockito.any(Comment.class));
  }

  @Autowired private MockMvc mockMvc;
  @MockBean private CommentService commentService;
  @Autowired private ObjectMapper objectMapper;
}
