package gamestudio;

import gamestudio.game.gridhunt.consoleui.ConsoleUI;
import gamestudio.game.gridhunt.core.World;
import gamestudio.service.CommentService;
import gamestudio.service.CommentServiceRestClient;
import gamestudio.service.RatingService;
import gamestudio.service.RatingServiceRestClient;
import gamestudio.service.SaveService;
import gamestudio.service.SaveServiceImpl;
import gamestudio.service.ScoreService;
import gamestudio.service.ScoreServiceRestClient;
import gamestudio.service.UserService;
import gamestudio.service.UserServiceRestClient;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@Configuration
@ComponentScan(
    excludeFilters =
        @ComponentScan.Filter(type = FilterType.REGEX, pattern = "gamestudio.server.*"))
public class SpringClient {
  public static void main(String[] args) {
    new SpringApplicationBuilder(SpringClient.class).web(WebApplicationType.NONE).run(args);
  }

  @Bean
  public CommandLineRunner runner(ConsoleUI ui) {
    return args -> ui.play();
  }

  @Bean
  public ConsoleUI consoleUI(World world) {
    return new ConsoleUI(world);
  }

  @Bean
  public World field() {
    return new World();
  }

  @Bean
  public RestTemplate restTemplate() {
    return new RestTemplate();
  }

  @Bean
  public ScoreService scoreService() {
    return new ScoreServiceRestClient();
  }

  @Bean
  public CommentService commentService() {
    return new CommentServiceRestClient();
  }

  @Bean
  public RatingService ratingService() {
    return new RatingServiceRestClient();
  }

  @Bean
  public UserService userService() {
    return new UserServiceRestClient();
  }

  @Bean
  public SaveService saveService() {
    return new SaveServiceImpl();
  }
}
