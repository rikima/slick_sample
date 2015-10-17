package dao

import scala.concurrent.Future

import javax.inject.Inject

import play.api.db.slick.DatabaseConfigProvider
import play.api.db.slick.HasDatabaseConfigProvider

import slick.driver.JdbcProfile

import models.Models.FKeyword

import scala.concurrent.ExecutionContext.Implicits.global


class FKeywordDao @Inject()(protected val dbConfigProvider: DatabaseConfigProvider)
  extends HasDatabaseConfigProvider[JdbcProfile] {

  import driver.api._

  private val kws = TableQuery[FKeywordsTable]

  // ddl
  {
    val schema = kws.schema
    db.run(schema.create)
  }

  def all(): Future[Seq[FKeyword]] = db.run(kws.result)

  def insert(kw: FKeyword): Future[Unit] = db.run(kws += kw).map { _ => () }

  private class FKeywordsTable(tag: Tag) extends Table[FKeyword](tag, "FKEYWORD") {

    def keyword = column[String]("keyword", O.PrimaryKey)
    def filtered = column[Boolean]("filtered")

    def * = (keyword, filtered) <> (FKeyword.tupled, FKeyword.unapply _)
  }

}
