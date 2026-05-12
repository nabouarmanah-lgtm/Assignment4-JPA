package controllers;

import dao.CourseDAO;
import dao.EnrollmentDAO;
import dao.StudentDAO;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import models.Enrollment;
import models.Student;

public class EnrollmentController implements Initializable {

    @FXML private ComboBox<Integer> studentsCombobox;
    @FXML private ComboBox<Integer> coursesCombobox;
    @FXML private DatePicker enrollDate;
    @FXML private TableView<Enrollment> table;
    @FXML private TableColumn<Enrollment, Integer> enrollIdTC;
    @FXML private TableColumn<Enrollment, Integer> studentIdTC;
    @FXML private TableColumn<Enrollment, Integer> courseIdTC;
    @FXML private TableColumn<Enrollment, String> enrollDateTC;

    EnrollmentDAO dao = new EnrollmentDAO();
    StudentDAO studentDAO = new StudentDAO();
    CourseDAO courseDAO = new CourseDAO();

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        studentsCombobox.getItems().addAll(studentDAO.getAllStudentsids());
        coursesCombobox.getItems().addAll(courseDAO.getAllCoursesIds());

        enrollIdTC.setCellValueFactory(data ->
                new javafx.beans.property.SimpleIntegerProperty(
                        data.getValue().getEnrollId()
                ).asObject());

        studentIdTC.setCellValueFactory(data ->
                new javafx.beans.property.SimpleIntegerProperty(
                        data.getValue().getStudent().getStudentId()
                ).asObject());

        courseIdTC.setCellValueFactory(data ->
                new javafx.beans.property.SimpleIntegerProperty(
                        data.getValue().getCourseId()
                ).asObject());

        enrollDateTC.setCellValueFactory(data ->
                new javafx.beans.property.SimpleStringProperty(
                        data.getValue().getEnrollDate().toString()
                ));
    }

    @FXML
    public void viewHandle() {
        table.getItems().setAll(dao.findAll());
    }

    @FXML
    public void addHandle() {
        if (studentsCombobox.getValue() == null
                || coursesCombobox.getValue() == null
                || enrollDate.getValue() == null) {
            showWarning("Missing Data");
            return;
        }

        Student s = studentDAO.findById(studentsCombobox.getValue());

        Enrollment e = new Enrollment(
                s,
                coursesCombobox.getValue(),
                enrollDate.getValue()
        );

        if (dao.insert(e)) {
            showInfo("Added Successfully");
            viewHandle();
        } else {
            showWarning("Student already enrolled in this course!");
        }
    }

    @FXML
    public void updateHandle() {
        Enrollment selected = table.getSelectionModel().getSelectedItem();

        if (selected == null || enrollDate.getValue() == null) {
            showWarning("Select record + date");
            return;
        }

        selected.setEnrollDate(enrollDate.getValue());

        if (dao.update(selected)) {
            showInfo("Updated");
            viewHandle();
        }
    }

    @FXML
    public void deleteHandle() {
        Enrollment selected = table.getSelectionModel().getSelectedItem();

        if (selected == null) {
            showWarning("Select record");
            return;
        }

        if (dao.delete(selected)) {
            showInfo("Deleted");
            viewHandle();
        }
    }

    private void showWarning(String msg) {
        new Alert(Alert.AlertType.WARNING, msg).showAndWait();
    }

    private void showInfo(String msg) {
        new Alert(Alert.AlertType.INFORMATION, msg).showAndWait();
    }
}