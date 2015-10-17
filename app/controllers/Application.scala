package controllers

import javax.inject.Inject

import play.api.libs.concurrent.Execution.Implicits.defaultContext

import dao.CatDao
import models.Models.Cat

import play.api.data._
import play.api.data.Forms._
import play.api.mvc._

class Application @Inject() (cats: CatDao) extends Controller {
  val catForm = Form(
    mapping(

  "name" -> text(),
      "color" -> text()
    )(Cat.apply)(Cat.unapply)
  )
  def index = Action.async {
    cats.all.map { case cs => Ok(views.html.index(cs)) }
  }

  def insert = Action.async { implicit rs =>
    val cat: Cat = catForm.bindFromRequest.get
    cats.insert(cat).map(_ => Redirect(routes.Application.index))
  }
}
