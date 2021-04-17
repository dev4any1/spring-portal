package net.dev4any1.model;

import net.dev4any1.pojo.User;

public class UserModel extends User implements DbObject {

	private static final long serialVersionUID = UserModel.class.getName().hashCode();
	private Long id;

	public static User toUser(UserModel u) {
		User user = new User();
		user.setLogin(u.getLogin());
		user.setRole(u.getRole());
		return user;
	}

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

}
