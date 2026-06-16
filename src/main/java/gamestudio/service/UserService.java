package gamestudio.service;

import gamestudio.entity.User;

public interface UserService {
  void addUser(User user);

  User getUser(String username);

  void updatePassword(User user);

  void reset();
}
