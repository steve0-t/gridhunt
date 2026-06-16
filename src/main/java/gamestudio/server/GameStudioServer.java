package gamestudio.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import gamestudio.service.CommentService;
import gamestudio.service.CommentServiceJPA;
import gamestudio.service.RatingService;
import gamestudio.service.RatingServiceJPA;
import gamestudio.service.SaveService;
import gamestudio.service.SaveServiceImpl;
import gamestudio.service.ScoreService;
import gamestudio.service.ScoreServiceJPA;
import gamestudio.service.UserService;
import gamestudio.service.UserServiceJPA;

@SpringBootApplication
@Configuration
@EntityScan("sk.tuke.gamestudio.entity")
public class GameStudioServer {
  public static void main(String[] args) {
    SpringApplication.run(GameStudioServer.class, args);
  }

  @Bean
  public ScoreService scoreService() {
    return new ScoreServiceJPA();
  }

  @Bean
  public RatingService ratingService() {
    return new RatingServiceJPA();
  }

  @Bean
  public CommentService commentService() {
    return new CommentServiceJPA();
  }

  @Bean
  public UserService userService() {
    return new UserServiceJPA();
  }

  @Bean
  public SaveService saveService() {
    return new SaveServiceImpl();
  }
}
