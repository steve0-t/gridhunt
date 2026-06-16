package gamestudio.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import java.util.List;
import gamestudio.entity.User;

/** UserServiceJPA */
@Transactional
public class UserServiceJPA implements UserService {
  @Override
  public void addUser(User user) {
    entityManager
        .createNamedQuery("Users.addUser")
        .setParameter("username", user.getUsername())
        .setParameter("password", user.getPassword())
        .executeUpdate();
  }

  // @Override
  // public List<User> getUsers(String game) {
  //   return entityManager
  //       .createNamedQuery("Users.getUsers", User.class)
  //       .setParameter("game", game)
  //       .setMaxResults(10)
  //       .getResultList();
  // }

  @Override
  public User getUser(String username) {
    List<User> results =
        entityManager
            .createNamedQuery("Users.getUser")
            .setParameter("username", username)
            .getResultList();

    return !results.isEmpty() ? results.get(0) : null;
  }

  @Override
  public void reset() {
    entityManager.createNamedQuery("Users.resetUsers").executeUpdate();
  }

  @Override
  public void updatePassword(User user) {
    User existing = entityManager.find(User.class, user.getIdent());
    if (existing != null) {
      existing.setPassword(user.getPassword());
      entityManager
          .createNamedQuery("Users.changePassword")
          .setParameter("password", existing.getPassword())
          .setParameter("username", existing.getUsername())
          .executeUpdate();
    }
  }

  @PersistenceContext private EntityManager entityManager;
}
