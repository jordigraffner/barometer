package graffner.jordi.barometer.database;

/**
 * Created by jordigraffner on 29/12/15.
 */
public class DatabaseInfo {

    public class BarometerTables {
        public static final String USER = "user";
        public static final String COURSE = "course";
    }

    public class UserColumn {
        public static final String NAME = "name";
    }
    public class CourseColumn {
        public static final String NAME ="name";
        public static final String ECTS ="ects";
        public static final String CODE ="code";
        public static final String GRADE ="grade";
    }
}
