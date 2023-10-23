package com.helloworld.data.local;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.helloworld.domain.entities.SpacexLaunch;

@Database(version = 1, entities = {SpacexLaunch.class}, exportSchema = false)
@TypeConverters(Converter.class)
public abstract class SpaceRoomDatabase extends RoomDatabase {
    public abstract SpaceDao spaceDao();
}
