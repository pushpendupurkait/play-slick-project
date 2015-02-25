package controllers

import play.api._
import play.api.db.DB
import play.api.mvc._
import play.api.data._
import play.api.data.Forms._
import play.api.db.slick._
import play.api.Play.current
import play.api.data.format.Formats._
import play.api.db.slick.Config.driver.simple._
import play.api.data.Form
import play.api.db.slick.DBAction
import models.KnolTable
import play.twirl.api.Html
import views._
import models._
import play.mvc.Results.Redirect
import scala.slick.lifted.TableQuery
import play.mvc.Results.Redirect

object Application extends Controller {
  
  val knolTbl = TableQuery[KnolTable]

  def index = Action {
    Redirect(routes.Application.showData())
  }
  def formToAdd = Action {
    Ok(views.html.fform(addEmployeeForm))
  }
  def update(id:Int) = Action{
    Ok(views.html.editKnolder(addEmployeeForm,id))
  }
  def contact = Action{
    Ok(views.html.contact("Contact"))
  }
  /**
   * To add new employee
   */
  def addEmployee: Action[AnyContent] = DBAction { implicit rs =>
    try {
      val knol = addEmployeeForm.bindFromRequest.get
      KnolTable.addEmployee(knol)
      Redirect(routes.Application.showData(0,5,"",50)).flashing("success" ->"1 Record is Added.")
    } catch {
      case ex: Exception =>
        Redirect(routes.Application.showData(0,5,"",50)).flashing("failure" ->"Record is NOT Added.")
    }
  }
  /**
   * To Update an existing Record.
   */
  def updateByID(id:Int): Action[AnyContent] = DBAction { implicit rs =>
      try{
        val knol = addEmployeeForm.bindFromRequest.get
        KnolTable.updateByID(knol,id)
        Redirect(routes.Application.showData(0,5,"",50)).flashing("success"-> s"Record No. ${id} Successfully Updated.")
      }
      catch{
        case ex:Exception=>
          Redirect(routes.Application.showData(0,5,"",50)).flashing("failure"-> "No Record Updated.")
      }
  }
  /**
   * To Show existing data in Table
   */
  def showData(page:Int,pageSize:Int,search:String,total:Int): Action[AnyContent] = DBAction { implicit rs =>
    try{
    	  val totalRow=Query(knolTbl.filter(_.Fname.toLowerCase like "%"+search.toLowerCase+"%").length).first
    		val list = KnolTable.showData(page,pageSize,"%"+search+"%",totalRow)
    		Ok(views.html.showData(list,page,search,totalRow))
    }
    catch{
      case ex:Exception=>
        Redirect(routes.Application.showData(0,5,"",50)).flashing("failure" ->"Something went wrong.")
    }
  }
  /**
   * To delete Employee Record from Table
   */
  def deleteById(ID:Int): Action[AnyContent] = DBAction { implicit rs =>
    try{
      val afftdRow = KnolTable.deleteByID(ID)
    if (afftdRow < 1)
      Redirect(routes.Application.showData(0,5,"",50)).flashing("failure" ->"No record Deleted.")
    else
      Redirect(routes.Application.showData(0,5,"",50)).flashing("success"-> s"Record No. ${ID} Successfully Deleted")
    }
    catch{
      case ex:Exception=>
        Redirect(routes.Application.showData(0,5,"",50)).flashing("failure" ->"Something went wrong.")
    }
    
  }
/**
 * addEmployeeForm is Form to take data from user.
 */

  val addEmployeeForm = Form(
    mapping(
      "Fname" -> text,
      "Lname" -> text,
      "Email" -> text,
      "Mobile" -> text,
      "id" -> number)(Knol.apply)(Knol.unapply))

}
case class Knol(Fname: String, Lname: String, Email: String, Mobile: String, id: Int = 0)
