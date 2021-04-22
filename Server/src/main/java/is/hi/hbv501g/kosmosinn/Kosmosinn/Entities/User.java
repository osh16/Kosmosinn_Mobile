package is.hi.hbv501g.kosmosinn.Kosmosinn.Entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * User Entity, an entity for the many users of Kosmosinn.
 * A User has an id (long), a username (String), a password (String)
 * and includes a ArrayList of Topics generated by the user (Topic Entities)
 * and includes a ArrayList of Comments generated by the user (Comment Entities) 
 */
@Entity
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column(nullable = false, unique = true)
	public String username;

	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	@Column(nullable = false)
	public String password;

	@Column(nullable = false)
	public String role;

	@JsonIgnore
	@Column
	public String token;

	@JsonIgnore // nota /api/users/{id}/topics
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "user", orphanRemoval = true, cascade = CascadeType.ALL)
	private List<Topic> topics = new ArrayList<>();

	//@JsonManagedReference
	@JsonIgnore // nota /api/users/{id}/comments
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "user", orphanRemoval = true, cascade = CascadeType.ALL)
	private List<Comment> comments = new ArrayList<>();

	@JsonIgnore
	@OneToMany(fetch = FetchType.LAZY, orphanRemoval = true, cascade = CascadeType.ALL)
    private List<Message> messagesSent = new ArrayList<>();

	@JsonIgnore
	@OneToMany(fetch = FetchType.LAZY, orphanRemoval = true, cascade = CascadeType.ALL)
	private List<Message> messagesReceived = new ArrayList<>();

	@JsonIgnore
	public long userCreated;
	@JsonIgnore
	public long lastOnline;

	public User(String username, String password, String token)  {
		this.username = username;
		this.password = password;
		this.role = "USER";
		this.token = token;
	}

	public User(String username, String password, String role, String token)  {
		this.username = username;
		this.password = password;
		this.role = role;
		this.token = token;
	}

	public User() {}

	public long getId() {
		return id;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() { return password; }

	public String getToken() { return token; }

	public String getRole() { return role; }

	public List<Topic> getTopics() {
		return topics;
	}

	public List<Comment> getComments() {
		return comments;
	}

	public long getUserCreated() {
		return userCreated;
	}

	public String getUserCreatedDate() {
		LocalDateTime ldt = LocalDateTime.ofInstant(Instant.ofEpochSecond(userCreated), ZoneId.systemDefault());
		return String.format("%d %s, %d at %d:%d", ldt.getDayOfMonth(), ldt.getMonth(), ldt.getYear(), ldt.getHour(), ldt.getMinute());
	}

	public long getLastOnline() {
		return lastOnline;
	}

	public String getLastOnlineDate() {
		LocalDateTime ldt = LocalDateTime.ofInstant(Instant.ofEpochSecond(lastOnline), ZoneId.systemDefault());
		return String.format("%d.%d.%d, %d:%d:%d", ldt.getDayOfMonth(), ldt.getDayOfMonth(), ldt.getYear(), ldt.getHour(), ldt.getMinute(), ldt.getSecond());
	}

	public List<Message> getMessagesSent() {
		return messagesSent;
	}

	public List<Message> getMessagesReceived() {
		return messagesReceived;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public void setRole(String role) { this.role = role; }

	public void setTopics(List<Topic> topics) {
		this.topics = topics;
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}

	public void setUserCreated() {
		this.userCreated = Instant.now().getEpochSecond();
	}

	public void setLastOnline() {
		this.lastOnline = Instant.now().getEpochSecond();
	}

	public void setMessagesSent(List<Message> messagesSent) {
		this.messagesSent = messagesSent;
	}

	public void setMessagesReceived(List<Message> messagesReceived) {
		this.messagesReceived = messagesReceived;
	}
}