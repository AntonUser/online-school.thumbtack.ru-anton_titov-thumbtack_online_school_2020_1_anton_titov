package net.thumbtack.school.database.jdbc;

import net.thumbtack.school.database.model.School;
import net.thumbtack.school.database.model.Subject;
import net.thumbtack.school.database.model.Trainee;
import net.thumbtack.school.database.model.Group;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JdbcService {

    public static void insertTrainee(Trainee trainee) throws SQLException {
        Connection connection = JdbcUtils.getConnection();
        String queryInsertTrainee = "INSERT INTO ttschool.trainee (id, firstname, lastname, rating) VALUES(NULL,?,?,?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(queryInsertTrainee, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, trainee.getFirstName());
            preparedStatement.setString(2, trainee.getLastName());
            preparedStatement.setInt(3, trainee.getRating());
            preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                int id = resultSet.getInt(1);
                trainee.setId(id);
            }
        } catch (SQLException ex) {
            throw new SQLException();
        }
    }

    public static void updateTrainee(Trainee trainee) throws SQLException {
        Connection connection = JdbcUtils.getConnection();
        String queryUpdateTrainee = "UPDATE ttschool.trainee SET `id` = ?,`firstname` = ?, `lastname` = ?, `rating`=? WHERE (`id` = ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(queryUpdateTrainee, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setInt(1, trainee.getId());
            preparedStatement.setString(2, trainee.getFirstName());
            preparedStatement.setString(3, trainee.getLastName());
            preparedStatement.setInt(4, trainee.getRating());
            preparedStatement.setInt(5, trainee.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            throw new SQLException();
        }
    }

    public static Trainee getTraineeByIdUsingColNames(int traineeId) throws SQLException {
        Connection connection = JdbcUtils.getConnection();
        String queryGetTrainee = "SELECT* FROM trainee WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(queryGetTrainee)) {

            preparedStatement.setInt(1, traineeId);
            ResultSet resultSet = preparedStatement.executeQuery();
            Trainee trainee = null;
            if (resultSet.next()) {
                trainee = new Trainee(resultSet.getInt("id"), resultSet.getString("firstname"),
                        resultSet.getString("lastname"), resultSet.getInt("rating"));
            }
            if (trainee == null) {
                return null;
            }
            return trainee;
        } catch (SQLException ex) {
            throw new SQLException();
        }
    }

    public static Trainee getTraineeByIdUsingColNumbers(int traineeId) throws SQLException {
        Connection connection = JdbcUtils.getConnection();
        String queryGetTrainee = "SELECT* FROM trainee WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(queryGetTrainee)) {
            preparedStatement.setInt(1, traineeId);
            ResultSet resultSet = preparedStatement.executeQuery();
            Trainee trainee = null;
            if (resultSet.next()) {
                trainee = new Trainee(resultSet.getInt(1), resultSet.getString(2),
                        resultSet.getString(3), resultSet.getInt(4));
            }
            if (trainee == null) {
                return null;
            }
            return trainee;
        } catch (SQLException ex) {
            throw new SQLException();
        }
    }

    public static List<Trainee> getTraineesUsingColNames() throws SQLException {
        Connection connection = JdbcUtils.getConnection();
        String queryGetTrainees = "SELECT* FROM trainee";
        try (PreparedStatement preparedStatement = connection.prepareStatement(queryGetTrainees)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Trainee> trainees = new ArrayList<>();

            while (resultSet.next()) {
                trainees.add(new Trainee(resultSet.getInt("id"), resultSet.getString("firstname"),
                        resultSet.getString("lastname"), resultSet.getInt("rating")));
            }

            return trainees;

        } catch (SQLException ex) {
            throw new SQLException();
        }
    }

    public static List<Trainee> getTraineesUsingColNumbers() throws SQLException {
        Connection connection = JdbcUtils.getConnection();
        String queryGetTrainees = "SELECT* FROM trainee";
        try (PreparedStatement preparedStatement = connection.prepareStatement(queryGetTrainees)) {
            ResultSet resultSet = preparedStatement.executeQuery();

            List<Trainee> trainees = new ArrayList<>();
            while (resultSet.next()) {
                trainees.add(new Trainee(resultSet.getInt(1), resultSet.getString(2),
                        resultSet.getString(3), resultSet.getInt(4)));
            }

            return trainees;
        } catch (SQLException ex) {
            throw new SQLException();
        }
    }

    public static void deleteTrainee(Trainee trainee) throws SQLException {
        Connection connection = JdbcUtils.getConnection();
        String queryDelete = "DELETE FROM trainee WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(queryDelete)) {
            preparedStatement.setInt(1, trainee.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            throw new SQLException();
        }
    }

    public static void deleteTrainees() throws SQLException {
        Connection connection = JdbcUtils.getConnection();
        String queryDelete = "DELETE FROM trainee";
        try (PreparedStatement preparedStatement = connection.prepareStatement(queryDelete)) {
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            throw new SQLException();
        }
    }

    public static void insertSubject(Subject subject) throws SQLException {
        Connection connection = JdbcUtils.getConnection();
        String queryInsertSubject = "INSERT INTO ttschool.subject (id, name) VALUES(NULL,?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(queryInsertSubject, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, subject.getName());
            preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                int id = resultSet.getInt(1);
                subject.setId(id);
            }
        } catch (SQLException ex) {
            throw new SQLException();
        }
    }

    public static Subject getSubjectByIdUsingColNames(int subjectId) throws SQLException {
        Connection connection = JdbcUtils.getConnection();
        String queryGetSubject = "SELECT* FROM subject WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(queryGetSubject)) {
            preparedStatement.setInt(1, subjectId);
            ResultSet resultSet = preparedStatement.executeQuery();
            Subject subject = null;
            if (resultSet.next()) {
                subject = new Subject(resultSet.getInt("id"), resultSet.getString("name"));
            }
            if (subject == null) {
                return null;
            }
            return subject;
        } catch (SQLException ex) {
            throw new SQLException();
        }
    }

    public static Subject getSubjectByIdUsingColNumbers(int subjectId) throws SQLException {
        Connection connection = JdbcUtils.getConnection();
        String queryGetSubject = "SELECT* FROM subject WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(queryGetSubject)) {
            preparedStatement.setInt(1, subjectId);
            ResultSet resultSet = preparedStatement.executeQuery();
            Subject subject = null;
            while (resultSet.next()) {
                subject = new Subject(resultSet.getInt(1), resultSet.getString(2));
            }
            if (subject == null) {
                return null;
            }
            return subject;
        } catch (SQLException ex) {
            throw new SQLException();
        }
    }

    public static void deleteSubjects() throws SQLException {
        Connection connection = JdbcUtils.getConnection();
        String queryDelete = "DELETE FROM subject";
        try (PreparedStatement preparedStatement = connection.prepareStatement(queryDelete)) {
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            throw new SQLException();
        }
    }

    public static void insertSchool(School school) throws SQLException {
        Connection connection = JdbcUtils.getConnection();
        String queryInsertSchool = "INSERT INTO ttschool.school (id, name, year) VALUES(NULL,?,?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(queryInsertSchool, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, school.getName());
            preparedStatement.setInt(2, school.getYear());
            preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                int id = resultSet.getInt(1);
                school.setId(id);
            }
        } catch (SQLException ex) {
            throw new SQLException();
        }
    }

    public static School getSchoolByIdUsingColNames(int schoolId) throws SQLException {
        Connection connection = JdbcUtils.getConnection();
        String queryGetSchool = "SELECT* FROM school WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(queryGetSchool)) {
            preparedStatement.setInt(1, schoolId);
            ResultSet resultSet = preparedStatement.executeQuery();
            School school = null;
            while (resultSet.next()) {
                school = new School(resultSet.getInt("id"), resultSet.getString("name"), resultSet.getInt("year"));
            }
            if (school == null) {
                return null;
            }
            return school;
        } catch (SQLException ex) {
            throw new SQLException();
        }
    }

    public static School getSchoolByIdUsingColNumbers(int schoolId) throws SQLException {
        Connection connection = JdbcUtils.getConnection();
        String queryGetSchool = "SELECT* FROM school WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(queryGetSchool)) {
            preparedStatement.setInt(1, schoolId);
            ResultSet resultSet = preparedStatement.executeQuery();
            School school = null;
            while (resultSet.next()) {
                school = new School(resultSet.getInt(1), resultSet.getString(2), resultSet.getInt(3));
            }
            if (school == null) {
                return null;
            }
            return school;
        } catch (SQLException ex) {
            throw new SQLException();
        }
    }

    public static void deleteSchools() throws SQLException {
        Connection connection = JdbcUtils.getConnection();
        String queryDelete = "DELETE FROM school";
        try (PreparedStatement preparedStatement = connection.prepareStatement(queryDelete)) {
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            throw new SQLException();
        }
    }

    public static void insertGroup(School school, Group group) throws SQLException {
        JdbcUtils.createConnection();
        Connection connection = JdbcUtils.getConnection();
        String queryInsertSchool = "INSERT INTO ttschool.`group` (id, name, room, idSchool) VALUES(NULL,?,?,?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(queryInsertSchool, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, group.getName());
            preparedStatement.setString(2, group.getRoom());
            preparedStatement.setInt(3, school.getId());
            preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                int id = resultSet.getInt(1);
                group.setId(id);
            }
        } catch (SQLException ex) {
            throw new SQLException();
        }
    }

    public static School getSchoolByIdWithGroups(int id) throws SQLException {
        JdbcUtils.createConnection();
        Connection connection = JdbcUtils.getConnection();
        String queryInsertSchool = "SELECT * FROM `school` JOIN `group` ON `group`.`idSchool` = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(queryInsertSchool)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            School school = null;
            if (resultSet.next()) {
                school = new School(resultSet.getInt(1), resultSet.getString(2), resultSet.getInt(3));
                school.addGroup(new Group(resultSet.getInt(4), resultSet.getString(5), resultSet.getString(6)));
            }
            while (resultSet.next()) {
                school.addGroup(new Group(resultSet.getInt(4), resultSet.getString(5), resultSet.getString(6)));
            }
            return school;
        } catch (SQLException ex) {
            throw new SQLException();
        }

    }

    public static List<School> getSchoolsWithGroups() throws SQLException {
        JdbcUtils.createConnection();
        Connection connection = JdbcUtils.getConnection();
        String queryInsertSchool = "SELECT * FROM `school` JOIN `group` ON `school`.`id` = `group`.`idSchool`";
        try (PreparedStatement preparedStatement = connection.prepareStatement(queryInsertSchool)) {
            List<School> schools = new ArrayList<>();
            ResultSet resultSet = preparedStatement.executeQuery();
            School school = null;
            int prevId = 0;
            if (resultSet.next()) {
                school = new School(resultSet.getInt(1), resultSet.getString(2), resultSet.getInt(3));
                school.addGroup(new Group(resultSet.getInt(4), resultSet.getString(5), resultSet.getString(6)));
                prevId = resultSet.getInt(1);
            }
            while (resultSet.next()) {
                if (resultSet.getInt(1) != prevId) {
                    schools.add(school);
                    prevId = resultSet.getInt(1);
                    school = new School(resultSet.getInt(1), resultSet.getString(2), resultSet.getInt(3));
                }
                school.addGroup(new Group(resultSet.getInt(4), resultSet.getString(5), resultSet.getString(6)));
            }
            schools.add(school);
            return schools;
        } catch (SQLException ex) {
            throw new SQLException();
        }
    }
}
