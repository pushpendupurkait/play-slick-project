package models

import play.api.db.DB
import play.api.db.slick.Config.driver.simple._
import controllers._
import play.api.Play.current
import scala.slick.lifted.Tag
import java.sql.Timestamp


/**
 * case class to use in Form
 */
class KnolTable(tag: Tag) extends Table[Knol](tag, "knol") {
  def id: Column[Int] = column[Int]("id", O.PrimaryKey, O.AutoInc)
  def Fname: Column[String] = column[String]("Fname")
  def Lname: Column[String] = column[String]("Lname")
  def Email: Column[String] = column[String]("Email")
  def mobile: Column[String] = column[String]("Mobile")
  def * = (Fname, Lname,Email,mobile, id) <> (Knol.tupled, Knol.unapply)
}
object KnolTable {
  
  val knolTbl = TableQuery[KnolTable]
/**
 * addEmployee is for adding a new employee in Table.
 */
  def addEmployee(knol: Knol)(implicit s: Session):Int= {
      //knolTbl.ddl.create;
      val afftdRow = knolTbl.insert(knol)
      afftdRow
  }
/**
 * showData is used for showing data in the page
 */
  def showData(page:Int,pageSize:Int,search:String,total:Int)(implicit s:Session):List[Knol]={
      val offset = pageSize * page
      val dataTableWithFilter = knolTbl.filter(_.Fname.toLowerCase like search.toLowerCase).drop(offset).take(pageSize).list
        dataTableWithFilter
  }
/**
 * deleteByID is used for deleting Employee form table.
 */
  def deleteByID(Id:Int)(implicit s:Session):Int={
      val afftdRow = knolTbl.filter(_.id===Id).delete
      afftdRow
  }
/**
 * updateByID is used for updating existing employee data.
 */
  def updateByID(knol:Knol,ID:Int)(implicit s:Session):Int={
      val afftdRow = knolTbl.filter(_.id===ID).update(knol)
      afftdRow
  }
}