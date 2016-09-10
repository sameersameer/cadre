package com.cadre.db;

import com.cadre.entities.Creative;
import com.cadre.entities.Driver;
import com.codahale.metrics.annotation.Timed;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;

import java.util.List;

@RegisterMapper(Driver.DriverInfoMapper.class)
public interface DriverDao {

    static final String tableName = "driver";

    @Timed
    @SqlQuery("SELECT * FROM " + tableName)
    List<Creative> getAllDrivers();

    @SqlQuery("SELECT * FROM " + tableName + " where id = :id")
    Creative getDriver(@Bind("id") Integer id);


}
