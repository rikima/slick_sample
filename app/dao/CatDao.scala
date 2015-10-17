package dao

import models.Models.Cat

import scala.concurrent.Future

import javax.inject.Inject

import play.api.db.slick.DatabaseConfigProvider
import play.api.db.slick.HasDatabaseConfigProvider

import slick.driver.JdbcProfile

import scala.concurrent.ExecutionContext.Implicits.global

class CatDao @Inject()(protected val dbConfigProvider: DatabaseConfigProvider)
  extends HasDatabaseConfigProvider[JdbcProfile] {

  import driver.api._

  private val cats = TableQuery[CatsTable]

  // ddl
  {
    val schema = cats.schema
    db.run(schema.create)
  }

  def all(): Future[Seq[Cat]] = db.run(cats.result)

  def insert(cat: Cat): Future[Unit] = db.run(cats += cat).map { _ => () }

  private class CatsTable(tag: Tag) extends Table[Cat](tag, "CAT") {
    def name  = column[String]("name", O.PrimaryKey)
    def color = column[String]("color")

    def * = (name, color) <> (Cat.tupled, Cat.unapply _)
  }
}