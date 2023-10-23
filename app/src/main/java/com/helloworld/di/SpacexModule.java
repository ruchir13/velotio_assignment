package com.helloworld.di;

import android.app.Application;
import android.content.Context;

import androidx.room.Room;

import com.helloworld.data.local.SpaceDao;
import com.helloworld.data.local.SpaceRoomDatabase;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.qualifiers.ApplicationContext;
import dagger.hilt.components.SingletonComponent;

@Module
@InstallIn(SingletonComponent.class)
public class SpacexModule {
    @Provides
    @Singleton
    public Context provideContext(Application application) {
        return application.getApplicationContext();
    }

    @Provides
    @Singleton
    public SpaceRoomDatabase provideSpaceDatabase(@ApplicationContext Context context) {
        return Room.databaseBuilder(context, SpaceRoomDatabase.class, "space.db").build();
    }

    @Provides
    @Singleton
    public SpaceDao provideSpaceDao(SpaceRoomDatabase database) {
        return database.spaceDao();
    }
}
