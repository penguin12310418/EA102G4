package com.chatbox.model;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

public class State {
	private String type;
	// the user changing the state
	private String user;
	// total users
	private Set<String> users;
	// total users
	private Set<String> friends;
	//friendsListMessage
	private Collection<String> friendsValue;

	public State(String type, String user, Set<String> users, Set<String> friends, Collection<String> friendsValue) {
		super();
		this.type = type;
		this.user = user;
		this.users = users;
		this.friends = friends;
		this.friendsValue = friendsValue;
	}
	


	public Set<String> getFriends() {
		return friends;
	}



	public void setFriends(Set<String> friends) {
		this.friends = friends;
	}



	public Collection<String> getFriendsValue() {
		return friendsValue;
	}



	public void setFriendsValue(Collection<String> friendsValue) {
		this.friendsValue = friendsValue;
	}



	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public Set<String> getUsers() {
		return users;
	}

	public void setUsers(Set<String> users) {
		this.users = users;
	}

}
