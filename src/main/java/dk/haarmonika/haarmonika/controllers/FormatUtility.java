package dk.haarmonika.haarmonika.controllers;

import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.TableColumn;
import java.util.function.Function;


public class FormatUtility {
    private FormatUtility() {
        // Privat constructor to prevent instantiating
    }

    public static String checkExists(String value) {
        return (value != null && !value.isBlank()) ? "✅" : "❌";
    }

    public static <T> void setTextCell(TableColumn<T, String> column, Function<T, String> valueExtractor) {
        column.setCellValueFactory(cellData -> new SimpleStringProperty(valueExtractor.apply(cellData.getValue())));
    }

    public static <T> void setCheckmarkCell(TableColumn<T, String> column, Function<T, String> valueExtractor) {
        column.setCellValueFactory(cellData -> new SimpleStringProperty(checkExists(valueExtractor.apply(cellData.getValue()))));
    }

}
