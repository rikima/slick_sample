package controllers

import models._

import play.api.data.Forms._
import play.api.data._
import play.api.db.slick.Config.driver.simple._
import play.api.db.slick._
import play.api.libs.json.Json
import play.api.libs.json.Json._
import play.api.mvc._


object FKeywordController extends Controller{

  //create an instance of the table
  val fkws = TableQuery[FKeywordTable] //see a way to architect your app in the computers-database-slick sample

  //JSON read/write macro
  implicit val fkwsFormat = Json.format[FKeyword]

  def index = DBAction { implicit rs =>
    Ok(views.html.fk_index(fkws.list))
  }

  val fkwsForm = Form(
    mapping(
      "keyword" -> text(),
      "filtered" -> boolean
    )(FKeyword.apply)(FKeyword.unapply)
  )

  def insert = DBAction { implicit rs =>
    val afkws = fkwsForm.bindFromRequest.get
    fkws.insert(afkws)

    Redirect(routes.Application.index)
  }

  def jsonFindAll = DBAction { implicit rs =>
    Ok(toJson(fkws.list))
  }

  def jsonInsert = DBAction(parse.json) { implicit rs =>
    rs.request.body.validate[FKeyword].map { fkw =>
      fkws.insert(fkw)
      Ok(toJson(fkw))
    }.getOrElse(BadRequest("invalid json"))    
  }
  
}
