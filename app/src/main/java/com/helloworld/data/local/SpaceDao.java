package com.helloworld.data.local;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.helloworld.domain.entities.SpacexLaunch;

import java.util.List;

@Dao
public interface SpaceDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(SpacexLaunch item);

    @Query("SELECT * FROM launches")
    List<SpacexLaunch> listLaunches();

    @Query("UPDATE launches SET isFavorite = :status WHERE id =:launchId")
    void updateFavStatus(String launchId, boolean status);
}
