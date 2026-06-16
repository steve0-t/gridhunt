package gamestudio.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import java.util.List;
import gamestudio.entity.Score;

@Transactional
public class ScoreServiceJPA implements ScoreService {
  @Override
  public void addScore(Score score) throws ScoreException {
    entityManager
        .createNamedQuery("Score.addScore")
        .setParameter("player", score.getPlayer())
        .setParameter("game", score.getGame())
        .setParameter("points", score.getPoints())
        .setParameter("playedOn", score.getPlayedOn())
        .executeUpdate();
  }

  @Override
  public List<Score> getTopScores(String game) throws ScoreException {
    return entityManager
        .createNamedQuery("Score.getTopScores", Score.class)
        .setParameter("game", game)
        .setMaxResults(10)
        .getResultList();
  }

  @Override
  public void reset() {
    entityManager.createNamedQuery("Score.resetScores").executeUpdate();
    // alebo:
    // entityManager.createNativeQuery("delete from score").executeUpdate();
  }

  @PersistenceContext private EntityManager entityManager;
}
