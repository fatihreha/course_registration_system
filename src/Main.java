import java.util.*;
class Course {
    String code;
    String name;
    String calendar;
    int studentCount;
 public Course(String code, String name, String calendar) {
        this.code = code;
        this.name = name;
        this.calendar = calendar;
        this.studentCount = 0;
    }
    public boolean canAddStudent() {
        return studentCount < 5;
    }
    public void addStudent() {
        studentCount++;
    }
    public void dropStudent() {
        if (studentCount > 0) {
            studentCount--;
        }
    }
    public String toString() {
        return name + " (" + code + ") - Calendar: " + calendar + " - Students: " + studentCount + "/5";
    }
    public boolean hasConflict(String otherCalendar) {
        return this.calendar.equals(otherCalendar);
    }
}
class Student {
    long studentNumber;
    String name;
    List<Course> courses;
    public Student(long studentNumber, String name) {
        this.studentNumber = studentNumber;
        this.name = name;
        this.courses = new ArrayList<>();
    }
    public boolean canAddCourse() {
        return courses.size() < 5;
    }
    public boolean hasCalendarConflict(String calendar) {
        for (Course registeredCourse : courses) {
            if (registeredCourse.hasConflict(calendar)) {
                return true;
            }
        }
        return false;
    }
    public void addCourse(Course course) {
        if (canAddCourse()) {
            for (Course registeredCourse : courses) {
                if (registeredCourse.hasConflict(course.calendar)) {
                    System.out.println("Cannot add " + course.name + " for " + name + " due to calendar conflict with " + registeredCourse.name);
                    return;
                }
            }

            courses.add(course);
            course.addStudent();
            System.out.println(name + " has successfully added " + course.name);
        } else {
            System.out.println("Cannot add " + course.name + " for " + name + ". Student already registered for 5 courses.");
        }
    }

    public void dropCourse(Course course) {
        if (courses.contains(course)) {
            courses.remove(course);
            course.dropStudent();
            System.out.println(name + " has successfully dropped " + course.name);
        } else {
            System.out.println(name + " didn't select " + course.name + " earlier");
        }
    }
    public String toString() {
        return name + " (" + studentNumber + ") - Courses: " + courses.size() + "/5";
    }
}
public class Main {
    public static void main(String[] args) {
        Map<String, Course> courses = initializeCourses();
        Map<Long, Student> students = initializeStudents();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n1. indicate Courses");
            System.out.println("2. indicate Students");
            System.out.println("3. Add Course to Student");
            System.out.println("4. Drop Course from Student");
            System.out.println("5. Exit");
            System.out.print("Select an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    showCourses(courses);
                    break;
                case 2:
                    showStudents(students);
                    break;
                case 3:
                    addCourseToStudent(scanner, students, courses);
                    break;
                case 4:
                    dropCourseFromStudent(scanner, students, courses);
                    break;
                case 5:
                    System.out.println("Exiting the program");
                    System.exit(0);
                default:
                    System.out.println("Invalid! Enter a valid option.");
            }
        }
    }
    private static Map<String, Course> initializeCourses() {
        Map<String, Course> courses = new HashMap<>();
        courses.put("HTR301.5", new Course("HTR301.5", "HistoryOfTurkishRevolution", "Monday, 14:00-16:00"));
        courses.put("ACM212.2", new Course("ACM212.2", "AdvancedDatabaseApplications", "Tuesday, 09:00-12:00"));
        courses.put("ACM365.4", new Course("ACM365.4", "AdvancedWebDesign", "Tuesday, 13:00-16:00"));
        courses.put("ACM437.2", new Course("ACM437.2", "InternetOfThings", "Wednesday, 10:00-13:00"));
        courses.put("ACM321.4", new Course("ACM321.4", "ObjectOrientedProgramming", "Thursday, 09:00-12:00"));
        courses.put("STAT411.6", new Course("STAT411.6", "AdvancedStatisticApplications", "Thursday, 13:00-16:00"));
        courses.put("ACM213.3", new Course("ACM213.3", "InformationAnalysisAndSystemDesign", "Friday, 13:00-16:00"));
        courses.put("ACM222.2", new Course("ACM222.2", "StructuralProgramming", "Wednesday, 09:00-12:00"));
        courses.put("ACM361.1", new Course("ACM361.1", "Networking", "Wednesday, 16:00-19:00"));
        courses.put("TKL202.10", new Course("TKL202.10", "TurkishLanguage", "Monday, 14:00-16:00"));
        return courses;
    }
    private static Map<Long, Student> initializeStudents() {
        Map<Long, Student> students = new HashMap<>();
        students.put(20211308012L, new Student(20211308012L, "Fatih"));
        students.put(20211308013L, new Student(20211308013L, "Fatih1"));
        students.put(20211308014L, new Student(20211308014L, "Fatih2"));
        students.put(20211308015L, new Student(20211308015L, "Fatih3"));
        students.put(20211308016L, new Student(20211308016L, "Fatih4"));
        students.put(20211308017L, new Student(20211308017L, "Fatih5"));
        students.put(20211308018L, new Student(20211308018L, "Fatih6"));
        students.put(20211308019L, new Student(20211308019L, "Fatih7"));
        students.put(20211308010L, new Student(20211308010L, "Fatih8"));
        students.put(20211308011L, new Student(20211308011L, "Fatih9"));
        return students;
    }
    private static void showCourses(Map<String, Course> courses) {
        for (Course course : courses.values()) {
            System.out.println(course);
        }
    }
    private static void showStudents(Map<Long, Student> students) {
        for (Student student : students.values()) {
            System.out.println(student);
        }
    }
    private static void addCourseToStudent(Scanner scanner, Map<Long, Student> students, Map<String, Course> courses) {
        System.out.print("Enter student number: ");
        long studentNumber = scanner.nextLong();
        scanner.nextLine();

        if (students.containsKey(studentNumber)) {
            Student student = students.get(studentNumber);

            System.out.print("Enter course code: ");
            String courseCode = scanner.nextLine();

            Course course = courses.get(courseCode);

            if (course != null) {
                student.addCourse(course);
            } else {
                System.out.println("Invalid course code");
            }
        } else {
            System.out.println("Invalid student number");
        }
    }
    private static void dropCourseFromStudent(Scanner scanner, Map<Long, Student> students, Map<String, Course> courses) {
        System.out.print("Enter student number: ");
        long studentNumber = scanner.nextLong();
        scanner.nextLine();

        if (students.containsKey(studentNumber)) {
            Student student = students.get(studentNumber);

            if (!student.courses.isEmpty()) {
                System.out.println("Courses registered by " + student.name + ":");
                for (int i = 0; i < student.courses.size(); i++) {
                    System.out.println((i + 1) + ". " + student.courses.get(i).name);
                }

                System.out.print("Enter the number of the course to drop: ");
                int courseNumber;
                try {
                    courseNumber = scanner.nextInt();
                    scanner.nextLine();
                } catch (InputMismatchException e) {
                    System.out.println("Invalid. enter a valid number");
                    scanner.nextLine();  // clear the buffer
                    return;
                }

                if (courseNumber >= 1 && courseNumber <= student.courses.size()) {
                    Course courseToDrop = student.courses.get(courseNumber - 1);
                    student.dropCourse(courseToDrop);
                    System.out.println(student.name + " has successfully dropped " + courseToDrop.name);
                } else {
                    System.out.println("Invalid course number");
                }
            } else {
                System.out.println(student.name + " isn't registered in any courses");
            }
        } else {
            System.out.println("Invalid student number");
        }
    }
}