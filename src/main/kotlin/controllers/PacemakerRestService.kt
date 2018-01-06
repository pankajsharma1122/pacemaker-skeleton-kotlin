package controllers

import io.javalin.Context
import models.Activity
import models.Location
import models.User

class PacemakerRestService  {
  val pacemaker = PacemakerAPI()

  fun listUsers(ctx: Context) {
    ctx.json(pacemaker.users)
  }

  fun createUser(ctx: Context) {
    val user = ctx.bodyAsClass(User::class.java)
	  println("createUser::>>" + user)
    val newUser = pacemaker.createUser(user.firstname, user.lastname, user.email, user.password)
    ctx.json(newUser)
  }

  fun deleteUsers(ctx: Context) {
	  println("deleteUsers::>>")
    pacemaker.deleteUsers()
    ctx.status(200)
  }
	
	fun getActivity(ctx: Context) {
    // val userId: String? = ctx.param("id")
    val activityId: String? = ctx.param("activityId")
		println("getActivity activityId::>>" + activityId)
    val activity = pacemaker.getActivity(activityId!!)
    if (activity != null) {
      ctx.json(activity)
    } else {
      ctx.status(404)
    }
  }

  fun getActivities(ctx: Context) {
    val id: String? =  ctx.param("id")
	  println("getActivities ::>>" + id) 
    val user = pacemaker.getUser(id!!)
    if (user != null) {
      ctx.json(user.activities.values)
    } else {
      ctx.status(404)
    }
  }
	
	fun listActivities(ctx: Context) {
    val id: String? =  ctx.param("id")
		val sortBy: String? =  ctx.param("sortBy")
	  println("listActivities with sortBy ::>>" + id + " :::>>>" + sortBy)
		val user = pacemaker.getUser(id!!)
    if (user != null) {
      ctx.json(pacemaker.listActivities(id!!,sortBy!!)!!)
    } else {
      ctx.status(404)
    }
  }

   fun createActivity(ctx: Context) {
    val id: String? =  ctx.param("id")
	   println("createActivity ::>>" + id)
    val user = pacemaker.getUser(id!!)
    if (user != null) {
      val activity = ctx.bodyAsClass(Activity::class.java)
      val newActivity = pacemaker.createActivity(user.id, activity.type, activity.location, activity.distance)
      ctx.json(newActivity!!)
    } else {
      ctx.status(404)
    }
  }

  fun deleteActivites(ctx: Context) {
    val id: String? =  ctx.param("id")
	  println("deleteActivites ::>>" + id)
    pacemaker.deleteActivities(id!!);
    ctx.status(204)
  }
	
	fun followAFriend(ctx: Context) {
    val loggedUserEmail: String? =  ctx.param("id")
		val friendEmail: String? =  ctx.param("friendId")
	  println("followAFriend ::>>" + friendEmail + " <<loggedUserEmail>>" + loggedUserEmail)
    pacemaker.followAFriend(loggedUserEmail!!,friendEmail!!);
    ctx.status(204)
  }
	
	fun unfollowAFriend(ctx: Context) {
    val loggedUserEmail: String? =  ctx.param("id")
		val friendEmail: String? =  ctx.param("friendId")
	  println("deleteActivites ::>>" + loggedUserEmail)
    pacemaker.unfollowAFriend(loggedUserEmail!!,friendEmail!!);
    ctx.status(204)
  }
	
	fun messageAFriend(ctx: Context) {
    val tofriendEmail: String? =  ctx.param("friendEmail")
		//val friendEmail: String? =  ctx.param("friendId")
	  println("messageAFriend ::>>" + tofriendEmail)
		val message : String? = ctx.body();
		ctx.json(pacemaker.messageAFriend(tofriendEmail!!, message!!)!!)
  }
	
	fun messageAllFriend(ctx: Context) {
    val userId: String? =  ctx.param("id")
		//val friendEmail: String? =  ctx.param("friendId")
	  println("messageAllFriend ::>>" + userId)
		val message : String? = ctx.body();
		ctx.json(pacemaker.messageAllFriend(userId!!, message!!)!!)
  }
	
	
	fun listAllMessages(ctx: Context) {
    val userId: String? =  ctx.param("id")
		println("listAllMessages ::>>" + userId)
		ctx.json(pacemaker.listAllMessages(userId!!)!!)
  }
	
	fun listAllFriends(ctx: Context) {
    val userId: String? =  ctx.param("id")
		println("listAllFriends ::>>" + userId)
		ctx.json(pacemaker.listAllFriends(userId!!)!!)
  }
	
}