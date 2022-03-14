package sourse.outils;

import java.time.LocalDate;
import java.time.Period;

public class dateUtils
{
    public static int getYearsBetweenDat(LocalDate debut,LocalDate fin)//c'est une fonction a 2 arguments avec 2 variable de meme type LocalDate
    {
        return Period.between(debut,fin).getYears();
    }
    public static int getMonthsBetweenDate(LocalDate debut,LocalDate fin)
    {
        return Period.between(debut,fin).getMonths();
    }
    public static int getDaysBetweenDate(LocalDate debut,LocalDate fin)
    {
        return Period.between(debut,fin).getDays();
    }
}
