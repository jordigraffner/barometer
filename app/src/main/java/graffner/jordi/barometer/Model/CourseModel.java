package graffner.jordi.barometer.Model;

/**
 * Created by wesley tjin on 29-12-15.
 */
public class CourseModel {
    public String period;
    public String name;
    public String ects;
    public String grade;

    public CourseModel (String courseName, String ects, String grade, String period){
        this.name = courseName;
        this.ects = ects;
        this.grade = grade;
        this.period = period;
    }

}
