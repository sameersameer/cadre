package com.cadre.db;

import com.cadre.entities.Creative;
import com.codahale.metrics.annotation.Timed;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;

import java.util.List;

@RegisterMapper(Creative.CreativeInfoMapper.class)
public interface CreativeDao {

    static final String tableName = "creative";

    @Timed
    @SqlQuery("SELECT * FROM " + tableName)
    List<Creative> getAllCreatives();

    @SqlQuery("SELECT * FROM " + tableName + " where id = :id")
    Creative getCreative(@Bind("id") Integer id);


}
