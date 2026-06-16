package gamestudio.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import java.util.List;
import gamestudio.entity.Comment;

@Transactional
public class CommentServiceJPA implements CommentService {
  @Override
  public void addComment(Comment comment) throws CommentException {
    entityManager
        .createNamedQuery("Comment.addComment")
        .setParameter("player", comment.getPlayer())
        .setParameter("game", comment.getGame())
        .setParameter("comment", comment.getComment())
        .setParameter("commentedOn", comment.getCommentedOn())
        .executeUpdate();
  }

  @Override
  public List<Comment> getComments(String game) throws CommentException {
    return entityManager
        .createNamedQuery("Comment.getComments", Comment.class)
        .setParameter("game", game)
        .setMaxResults(10)
        .getResultList();
  }

  @Override
  public void reset() throws CommentException {
    entityManager.createNamedQuery("Comment.resetScores").executeUpdate();
  }

  @PersistenceContext private EntityManager entityManager;
}
