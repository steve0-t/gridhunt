package gamestudio.server.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.ModelAttribute;

/** GlobalModelAttributes */
@ControllerAdvice
public class GlobalModelAttributes {
  @ModelAttribute
  public void addThemeToModel(
      @CookieValue(value = "theme", defaultValue = "default") String theme, Model model) {
    model.addAttribute("theme", theme);
  }
}
