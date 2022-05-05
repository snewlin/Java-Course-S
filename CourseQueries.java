/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author sophi
 */
public class CourseQueries {
    private static Connection connection;
    private static ArrayList<CourseEntry> courses = new ArrayList<CourseEntry>();
    private static ArrayList<CourseEntry> courseCodes = new ArrayList<CourseEntry>();
    private static PreparedStatement addCourse;
    private static PreparedStatement getAllCourses;
    private static PreparedStatement getAllCourseCodes;
    private static PreparedStatement getCourseSeats;
    private static ResultSet resultSet;
    private static int courseSeats;
    
    static public ArrayList<CourseEntry> getAllCourses(String semester){
        connection = DBConnection.getConnection();
        ArrayList<CourseEntry> courses = new ArrayList<CourseEntry>();
        try
        {
            getAllCourses = connection.prepareStatement("select coursecode,description,seats from app.course where semester = ?");
            getAllCourses.setString(1, semester);
            
            resultSet = getAllCourses.executeQuery();
            
            while(resultSet.next())
            {
                courses.add(new CourseEntry(semester,resultSet.getString(1),resultSet.getString(2),resultSet.getInt(3)));

            }
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        return courses;
    }
    
    public static void addCourse(CourseEntry course){
        connection = DBConnection.getConnection();
        try
        {
            addCourse = connection.prepareStatement("insert into app.course (semester,coursecode,description,seats) values (?,?,?,?)");
            addCourse.setString(1, course.getSemester());
            addCourse.setString(2, course.getCourseCode());
            addCourse.setString(3, course.getCourseDescription());
            addCourse.setInt(4, course.getSeats());
            addCourse.executeUpdate();
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
    }
    public static ArrayList<String> getAllCourseCodes(String semester){
        connection = DBConnection.getConnection();
        System.out.println(semester);
        ArrayList<String> courseCodes = new ArrayList<String>();
        try
        {
            getAllCourseCodes = connection.prepareStatement("select courseCode from app.course where semester = ? order by courseCode");
            getAllCourseCodes.setString(1, semester);
            resultSet = getAllCourseCodes.executeQuery();
            
            while(resultSet.next())
            {
                courseCodes.add(resultSet.getString(1));
            }
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        return courseCodes;
    }
    public static int getCourseSeats(String semester, String courseCode){
        connection = DBConnection.getConnection();
        int courseSeats = 0;
        try
        {
            getCourseSeats = connection.prepareStatement("select seats from app.course where coursecode = (?) and semester = (?)");
            getCourseSeats.setString(1, courseCode);
            getCourseSeats.setString(2,semester);
            resultSet = getCourseSeats.executeQuery();
            
            while(resultSet.next())
            {
                courseSeats = resultSet.getInt(1);
            }
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        return courseSeats;
    } 
    
    
    
}
