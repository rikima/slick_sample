package models

/**
 * Created by masabon on 2015/10/17.
 */
object Models {
  case class Cat(name: String, color: String)
  case class FKeyword(keyword: String, filtered: Boolean)
}
