package com.a_smart_cookie.entity;

/**
 * Role entity.
 *
 */
public enum Role {
	SUBSCRIBER(1),
	ADMIN(2);

	private final int id;

	Role(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}
}
