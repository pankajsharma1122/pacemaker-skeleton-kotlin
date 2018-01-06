package controllers

import java.util.UUID;
import models.Activity
import models.Location
import models.User

class PacemakerAPI {

  var userIndex = hashMapOf<String, User>()
  var emailIndex = hashMapOf<String, User>()
  var activitiesIndex = hashMapOf<String, Activity>()
  var users = userIndex.values

  fun createUser(firstName: String, lastName: String, email: String, password: String): User {
    var user = User(firstName, lastName, email, password)
    userIndex[user.id] = user
    emailIndex[user.email] = user
    return user
  }

  fun deleteUsers() {
    userIndex.clear();
    emailIndex.clear()
  }

  fun getUser(id: String) = userIndex[id]
	//fun getUsers() = users
  fun getUserByEmail(email: String) = emailIndex[email]
	
	
	//fun getUsers() = users;
	
	fun createActivity(id: String, type: String, location: String, distance: Float): Activity? {
    var activity:Activity? = null
    var user = userIndex.get(id)
    if (user != null) {
      activity = Activity(type, location, distance)
      user.activities[activity.id] = activity
      activitiesIndex[activity.id] = activity;
    }
    return activity;
  }

  fun getActivity(id: String): Activity? {
    return activitiesIndex[id]
  }
	
	fun getActivities(id: String): Collection<Activity>? {
		require(userIndex[id] != null)
		var activities:Collection<Activity>? = null;
    var user = userIndex.get(id)
    if (user != null) {
      activities = user.activities.values
	  }
    return activities
  }
	
	fun listActivities(id: String, sortBy: String): List<Activity>? {
		require(userIndex[id] != null)
		var user = userIndex.get(id)
    var activities:MutableCollection<Activity>?  =  user?.activities?.values
		var sortedList:List<Activity>? = listOf()
		when (sortBy) {
			"type" ->  {sortedList = activities?.sortedWith(compareBy({it.type}))}
			"location" -> {sortedList =activities?.sortedWith(compareBy({it.location}))}
			"distance" -> {sortedList =activities?.sortedWith(compareBy({it.distance}))}
		}
    return sortedList
  }
	
	

  fun deleteActivities(id: String) {
    require(userIndex[id] != null)
    var user = userIndex.get(id)
    if (user != null) {
      for ((u, activity) in user.activities) {
        activitiesIndex.remove(activity.id)
      }
      user.activities.clear();
    }
  }
	
	fun followAFriend(loggedUserEmail: String,friendEmail :String) {
    require(emailIndex[loggedUserEmail] != null)
    var user = emailIndex.get(loggedUserEmail)
		var friendUser = emailIndex.get(friendEmail)
    if (user != null && friendUser != null) {
      user.friendList.add(friendEmail)
		  friendUser.friendList.add(loggedUserEmail)
    }
  }
	
	fun unfollowAFriend(loggedUserEmail: String,friendEmail :String) {
    require(emailIndex[loggedUserEmail] != null)
		var user = emailIndex.get(loggedUserEmail)
		var friendUser = emailIndex.get(friendEmail)
    //var user = userIndex.get(id)
    if (user != null && friendUser != null) {
      user.friendList.remove(friendEmail)
		  friendUser.friendList.remove(loggedUserEmail)
    }
  }
	
	fun messageAFriend(tofriendEmail: String,message :String) : User? {
    require(emailIndex[tofriendEmail] != null)
		var user = emailIndex.get(tofriendEmail)
		//var friendUser = emailIndex.get(friendEmail)
    //var user = userIndex.get(id)
    if (user != null) {
      user.friendMessages.add(message)
    }
		return user
  }
	
	fun messageAllFriend(userId: String,message :String) : User? {
		require(userIndex[userId] != null)
		var user = userIndex.get(userId)
		if (user != null) {
			
			user.friendList.forEach({messageAFriend(it, message)});
		}
		return user
	}
	
	fun listAllMessages(userId: String) : List<String>? {
		require(userIndex[userId] != null)
		var messages:List<String>? = null
		var user = userIndex.get(userId)
		if (user != null) {
			messages = user.friendMessages
		}
		return messages
	}
	
	fun listAllFriends(userId: String) : List<User>? {
		require(userIndex[userId] != null)
		var friends:MutableList<User>? = mutableListOf()
		var user = userIndex.get(userId)
		if (user != null) {
						user.friendList.forEach ( { friends?.add(emailIndex[it]!!)})
		}
		return friends
	}
	
	
}