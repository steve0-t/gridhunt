package gamestudio.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.NamedNativeQuery;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity(name = "Users")
@Table(
    name = "users",
    uniqueConstraints = {
      @UniqueConstraint(
          columnNames = {"username"},
          name = "usernane_unique_constraint")
    })
@NamedNativeQuery(
    name = "Users.addUser",
    query =
        """
        INSERT INTO users (username, password)
        VALUES (:username, :password)
        ON CONFLICT (username)
        DO NOTHING
        """)
@NamedQuery(
    name = "Users.changePassword",
    query = "UPDATE Users u SET u.password = :password WHERE u.username = :username")
@NamedQuery(
    name = "Users.changeUsername",
    query = "UPDATE Users u SET u.username = :username WHERE u.username = :username")
@NamedQuery(name = "Users.reset", query = "DELETE FROM Users")
@NamedQuery(name = "Users.getUser", query = "SELECT u FROM Users u WHERE u.username = :username")
public class User {
  public User(String username, String password) {
    this.username = username;
    this.password = password;
  }

  public User() {}

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public int getIdent() {
    return ident;
  }

  public void setIdent(int ident) {
    this.ident = ident;
  }

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int ident;

  private String username;
  private String password;
}
