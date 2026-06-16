package gamestudio.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;
import gamestudio.entity.User;

/** UserServiceRestClient */
public class UserServiceRestClient implements UserService {
  @Override
  public void addUser(User user) {
    restTemplate.postForEntity(url, user, User.class);
  }

  // @Override
  // public List<User> getUsers(String game) {
  //   return Arrays.asList(
  //       restTemplate.getForEntity(url + "/" + game + "/" + "users", User[].class).getBody());
  // }

  @Override
  public User getUser(String username) {
    User user = restTemplate.postForEntity(url, username, User.class).getBody();

    return user != null ? user : null;
  }

  @Override
  public void reset() {
    throw new UnsupportedOperationException("Not supported via web service");
  }

  @Override
  public void updatePassword(User user) {
    throw new UnsupportedOperationException("Not supported via web service");
  }

  private final String url = "http://localhost:8080/api/user";

  @Autowired private RestTemplate restTemplate;
}
