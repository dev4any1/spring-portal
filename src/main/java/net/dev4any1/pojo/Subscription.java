package net.dev4any1.pojo;
import java.util.Date;

public class Subscription {
	private Long id;
	private Date createdAt;
	private User user;
	private Category category;

	@Override
	public String toString() {
		return "Subscription [id=" + id + ", createdAt=" + createdAt + ", user=" + user + ", category=" + category
				+ "]";
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date date) {
		this.createdAt = date;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}
}
