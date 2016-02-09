package models

import anorm.SqlParser._
import anorm._
import play.api.Play.current
import play.api.db._

/**
  * Created by sakamoto on 2016/02/09.
  */
case class Task(id: Long, label: String)

object Task {

  val task = {
    get[Long]("id") ~
            get[String]("label") map {
      case id ~ label => Task(id, label)
    }
  }

  def all(): List[Task] = DB.withConnection { implicit c =>
    SQL("select * from task").as(task *)
  }

  def create(label: String) {
    DB.withConnection { implicit c =>
      SQL("insert into task (label) values ({label})").on(
        'label -> label
      ).executeUpdate()
    }
  }

  def delete(id: Long) {
    DB.withConnection { implicit c =>
      SQL("delete from task where id = {id}").on(
        'id -> id
      ).executeUpdate()
    }
  }

}