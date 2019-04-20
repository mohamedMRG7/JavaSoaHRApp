package com.dao;

import com.util.DBUtil;

import java.sql.*;

import java.util.ArrayList;
import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

import javax.xml.bind.annotation.XmlSeeAlso;

import oracle.jdbc.OracleDriver;
import com.model.Department;


@WebService
@XmlSeeAlso({ Department.class, DBUtil.class })
public class DepDao {

    
  //  private static final String INSERT_DEPARTMENT = "CALL insertDepartment (?,?,?,?)";
  //  private static final String UPDATE_DEPARTMENT = "CALL updateDepartment (?,?,?,?)";
  //  private static final String DELETE_DEPARTMENT = "CALL deleteDepartment (?)";
 // private static final String ALL_DEPARTMENT_QUERY = "SELECT * FROM DEPARTMENTS";
 // private static final String DEPARTMENT_BY_ID = "SELECT * FROM DEPARTMENTS WHERE DEPARTMENT_ID = ?";
  private String result = "";

// --------------------------------------------   get all Departments ---------------------------------
    @WebMethod
    public List<Department> getAllDepartments() {
        Connection conn = null;
        List<Department> departments = new ArrayList<>();
        try {
            conn = DBUtil.getConnection();
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM DEPARTMENTS");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                departments.add(new Department(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getInt(4)));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            DBUtil.closeConnection(conn);
        }
        return departments;
    }

    // --------------------------------------------   get  Department by Id ---------------------------------

    @WebMethod
    public Department getDepartmentByID(@WebParam(name = "departmentID") int departmentID) {
        Connection conn = null;
        Department department = null;
        try {
            conn = DBUtil.getConnection();
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM DEPARTMENTS WHERE DEPARTMENT_ID = ?");
            ps.setInt(1, departmentID);
            ResultSet rs = ps.executeQuery();
            rs.next();
            department = new Department(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getInt(4));
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            DBUtil.closeConnection(conn);
        }
        return department;

    }
    // --------------------------------------------   insert into  Departments ---------------------------------

    @WebMethod
    public String insertDepartment(@WebParam(name = "departmentID") int departmentID,
                                   @WebParam(name = "departmentName") String departmentName,
                                   @WebParam(name = "managerID") Integer managerID,
                                   @WebParam(name = "locationID") int locationID) {
        Connection conn = null;
       
        try {
            conn = DBUtil.getConnection();
            PreparedStatement ps = conn.prepareStatement("INSERT INTO DEPARTMENTS(Department_id,department_name,manager_id,location_id) VALUES (?,?,?,?)");
            ps.setInt(1, departmentID);
            ps.setString(2, departmentName);
            ps.setInt(3, managerID);
            ps.setInt(4, locationID);

            if (ps.executeUpdate() != Statement.EXECUTE_FAILED) {
                result = "Department inserted succefully";
            } else
                result = "faild to insert department";
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
                DBUtil.closeConnection(conn);
        }
        return result;
    }

    // --------------------------------------------  delete Department ---------------------------------

    @WebMethod
    public String deleteDepartment(@WebParam(name = "departmentID") int departmentID) {
        Connection conn = null;
        String result = "";
        try {
            conn = DBUtil.getConnection();

            PreparedStatement ps = conn.prepareStatement("DELETE FROM DEPARTMENTS WHERE DEPARTMENT_ID=?");
            ps.setInt(1, departmentID);
            System.out.println(ps.executeUpdate());


            if (ps.executeUpdate() != Statement.EXECUTE_FAILED) {
                result = "Department deleted succefully";
            } else
                result = "faild to delete department";
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            DBUtil.closeConnection(conn);
        }
        return result;
    }

    // --------------------------------------------   update Departments ---------------------------------

    @WebMethod
    public String updateDepartment(@WebParam(name = "departmentID") int departmentID,
                                   @WebParam(name = "departmentName") String departmentName,
                                   @WebParam(name = "managerID") int managerID, 
                                   @WebParam(name = "locationID") int locationID) {
        Connection conn = null;
        String result = "";
        try {
            conn = DBUtil.getConnection();
            PreparedStatement ps = conn.prepareStatement("UPDATE DEPARTMENTS SET DEPARTMENT_NAME =? ,MANAGER_ID= ? ,LOCATION_ID = ?  WHERE DEPARTMENT_ID = ?");
            ps.setString(1, departmentName);
            ps.setInt(2, managerID);
            ps.setInt(3, locationID);
            // by id
            ps.setInt(4, departmentID);

            
            if (ps.executeUpdate() != Statement.EXECUTE_FAILED) {
                result = "Department updated succefully";
            } else
                result = "faild to updated department";
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            DBUtil.closeConnection(conn);
        }
        return result;
    }

}
