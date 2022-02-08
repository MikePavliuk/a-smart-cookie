package com.a_smart_cookie.entity;

import java.io.Serializable;

public abstract class Entity<K> implements Serializable {
	private static final long serialVersionUID = 3747031305735100174L;
	private K id;

	public K getId() {
		return id;
	}

	public void setId(K id) {
		this.id = id;
	}

}