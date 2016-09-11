package com.cadre.aggregator;

import com.cadre.dao.SeverityDAO;
import com.cadre.dao.StatsDAO;
import com.cadre.entities.Stats;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.skife.jdbi.v2.DBI;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by purushotham.m on 11/09/16.
 */
public class AggregationJob {

    DBI dbi;
    double pricePerMeter = 0.01;

    public AggregationJob(){
        this.dbi = getDbi();
    }

    public List<Stats> getDeviceStat(){
        SeverityDAO severityDAO = dbi.onDemand(SeverityDAO.class);
        Map<String,BigDecimal> dailyDistance = getMapFromMapEntries(severityDAO.getDailyDistance());
        Map<String,BigDecimal> weeklyDistance = getMapFromMapEntries(severityDAO.getWeeklyDistance());
        Map<String,BigDecimal> totalDistance = getMapFromMapEntries(severityDAO.getTotalDistance());

        Map<String,BigDecimal> dailyEarning = getMapFromMapEntries(severityDAO.getDailyEarnings(pricePerMeter));
        Map<String,BigDecimal> weeklyEarning = getMapFromMapEntries(severityDAO.getWeeklyEarning(pricePerMeter));
        Map<String,BigDecimal> totalEarning = getMapFromMapEntries(severityDAO.getTotalEarning(pricePerMeter));

        DateTime lastUpdatedAt = new DateTime(severityDAO.getLastUpdated(), DateTimeZone.forOffsetHoursMinutes(5,30));

        List<Stats> stats = totalDistance.keySet().stream().map( k ->
                new Stats(k, dailyDistance.containsKey(k) ? dailyDistance.get(k):BigDecimal.ZERO,
                        weeklyDistance.containsKey(k) ? weeklyDistance.get(k):BigDecimal.ZERO,
                        totalDistance.containsKey(k) ? totalDistance.get(k):BigDecimal.ZERO,
                        dailyEarning.containsKey(k) ? dailyEarning.get(k):BigDecimal.ZERO,
                        weeklyEarning.containsKey(k) ? weeklyEarning.get(k):BigDecimal.ZERO,
                        totalEarning.containsKey(k) ? totalEarning.get(k):BigDecimal.ZERO,
                        lastUpdatedAt)
        ).collect(Collectors.toList());

        return stats;
    }

    private Map<String,BigDecimal> getMapFromMapEntries(List<Map.Entry<String,BigDecimal>> entries){
        HashMap<String,BigDecimal> map = new HashMap<>();
        entries.forEach(e -> map.put(e.getKey(), e.getValue()==null? BigDecimal.ZERO :e.getValue()));
        return map;
    }

    public void updateStats(List<Stats> stats){
        StatsDAO statsDAO = dbi.onDemand(StatsDAO.class);
        stats.forEach(s->{
            statsDAO.insertOrUpdate(s);
            }
        );

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
