package com.a_smart_cookie.dao;

public final class EntityColumn {

	public enum Genre {
		ID("id"),
		NAME("name");

		private final String name;

		Genre(String name) {
			this.name = name;
		}

		public String getName() {
			return name;
		}
	}

	private EntityColumn() {}

}
