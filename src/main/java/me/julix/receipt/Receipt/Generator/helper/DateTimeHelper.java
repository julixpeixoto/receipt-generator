package me.julix.receipt.Receipt.Generator.helper;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateTimeHelper {
    public static String dateToDDMMYYYY(LocalDate date){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return date.format(formatter);
    }
}
