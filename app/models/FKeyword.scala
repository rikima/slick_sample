package models

import play.api.db.slick.Config.driver.simple._

case class FKeyword(keyword: String, filtered: Boolean)

/* Table mapping
 */
class FKeywordTable(tag: Tag) extends Table[FKeyword](tag, "FKEYWORD") {

  def keyword = column[String]("keyword", O.PrimaryKey)
  def filtered = column[Boolean]("filtered", O.NotNull)

  def * = (keyword, filtered) <> (FKeyword.tupled, FKeyword.unapply _)
}
