package com.cadre.aggregator;

import com.cadre.dao.SeverityDAO;
import com.cadre.dao.StatsDAO;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.skife.jdbi.v2.DBI;
import com.cadre.entities.Stats;

import javax.print.attribute.standard.Severity;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by purushotham.m on 11/09/16.
 */
public class AggregationJob {

    DBI dbi;

    public AggregationJob(){
        this.dbi = getDbi();
    }

    public List<Stats> getDeviceStat(){
        SeverityDAO severityDAO = dbi.onDemand(SeverityDAO.class);
        Map<String,BigDecimal> dailyDistance = severityDAO.getDailyDistance();
        Map<String,BigDecimal> weeklyDistance = severityDAO.getWeeklyDistance();
        Map<String,BigDecimal> totalDistance = severityDAO.getTotalDistance();

        Map<String,BigDecimal> dailyEarning = severityDAO.getDailyEarnings(1);
        Map<String,BigDecimal> weeklyEarning = severityDAO.getWeeklyEarning(1);
        Map<String,BigDecimal> totalEarning = severityDAO.getTotalEarning(1);

        DateTime lastUpdatedAt = new DateTime(severityDAO.getLastUpdated(), DateTimeZone.forOffsetHoursMinutes(5,30));

        List<Stats> stats = totalDistance.keySet().stream().map( k ->
                new Stats(k, dailyDistance.get(k), weeklyDistance.get(k), totalDistance.get(k),
                        dailyEarning.get(k), weeklyEarning.get(k), totalEarning.get(k),lastUpdatedAt)
        ).collect(Collectors.toList());

        return stats;
    }

    public void updateStats(Iterable<Stats> stats){
        StatsDAO statsDAO = dbi.onDemand(StatsDAO.class);
        statsDAO.insertOrUpdate(stats);
    }

    public static DBI getDbi(){
        System.setProperty("jdbc.drivers", "com.mysql.jdbc.Driver");
        try {
          Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
          System.out.println("Driver not found");
        }
        String url = "jdbc:mysql://127.0.0.1:3306/cadredb";
        String user = "root";
        String password = "";
        DBI jdbi = new DBI(url, user, password);
        return jdbi;
    }

    public static void main(String[] arg){
        AggregationJob job = new AggregationJob();
        List<Stats> stats = job.getDeviceStat();
        job.updateStats(stats);
    }
}
