package gamestudio.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import gamestudio.entity.Rating;

@Transactional
public class RatingServiceJPA implements RatingService {
  @Override
  public void setRating(Rating rating) throws RatingException {
    entityManager
        .createNamedQuery("Rating.setRating")
        .setParameter("player", rating.getPlayer())
        .setParameter("game", rating.getGame())
        .setParameter("rating", rating.getPoints())
        .setParameter("ratedOn", rating.getRatedOn())
        .executeUpdate();
  }

  @Override
  public int getAverageRating(String game) throws RatingException {
    Double avg =
        (Double)
            entityManager
                .createNamedQuery("Rating.getAverageRating")
                .setParameter("game", game)
                .getSingleResult();

    return avg != null ? avg.intValue() : 0;
  }

  @Override
  public int getRating(String game, String player) throws RatingException {
    Integer ret =
        (Integer)
            entityManager
                .createNamedQuery("Rating.getRating")
                .setParameter("game", game)
                .setParameter("player", player)
                .getSingleResult();

    return ret != null ? ret.intValue() : 0;
  }

  @Override
  public void reset() throws RatingException {
    entityManager.createNamedQuery("Rating.resetRating").executeUpdate();
  }

  @PersistenceContext private EntityManager entityManager;
}
