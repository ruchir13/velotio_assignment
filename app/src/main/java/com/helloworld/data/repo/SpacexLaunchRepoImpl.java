package com.helloworld.data.repo;

import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.helloworld.data.Resource;
import com.helloworld.data.local.SpaceDao;
import com.helloworld.data.remote.DispatchGroup;
import com.helloworld.data.remote.RetrofitServiceInterface;
import com.helloworld.domain.entities.Rocket;
import com.helloworld.domain.entities.SpacexLaunch;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SpacexLaunchRepoImpl {

    private RetrofitServiceInterface serviceInterface;
    private SpaceDao spaceDao;

    public SpacexLaunchRepoImpl(RetrofitServiceInterface serviceInterface, SpaceDao spaceDao) {
        this.serviceInterface = serviceInterface;
        this.spaceDao = spaceDao;
    }

    public void updateFavStatus(String launchId, boolean status) {
        AsyncTask.execute(() -> spaceDao.updateFavStatus(launchId, status));
    }

    private void getSpacexLaunchList(MutableLiveData<Resource<List<SpacexLaunch>>> liveData) {
        serviceInterface.listLaunches().enqueue(new Callback<List<SpacexLaunch>>() {
            @Override
            public void onResponse(@NonNull Call<List<SpacexLaunch>> call, @NonNull Response<List<SpacexLaunch>> response) {
                if (response.isSuccessful()) {
                    List<SpacexLaunch> items = response.body();
                    DispatchGroup dispatchGroup = new DispatchGroup(() -> {
                        DispatchGroup dbDispatcher = new DispatchGroup(() -> liveData.postValue(Resource.success(spaceDao.listLaunches())));
                        if (items != null) {
                            dbDispatcher.enter(items.size());
                            items.forEach(item -> AsyncTask.execute(() -> {
                                spaceDao.insert(item);
                                dbDispatcher.leave();
                            }));
                        }
                    });
                    dispatchGroup.enter(items != null ? items.size() : 0);
                    items.forEach(item -> serviceInterface.getLaunchDetail(item.getRocket()).enqueue(new Callback<Rocket>() {
                        @Override
                        public void onResponse(@NonNull Call<Rocket> call, @NonNull Response<Rocket> response) {
                            item.setRocketModel(response.body());
                            dispatchGroup.leave();
                        }

                        @Override
                        public void onFailure(@NonNull Call<Rocket> call, @NonNull Throwable t) {
                            dispatchGroup.leave();
                        }
                    }));
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<SpacexLaunch>> call, @NonNull Throwable t) {
                liveData.postValue(Resource.error(t.getLocalizedMessage()));
            }
        });
    }

    public void spacexLaunchList(MutableLiveData<Resource<List<SpacexLaunch>>> liveData, boolean hardRefresh) {
        if (hardRefresh) {
            getSpacexLaunchList(liveData);
        } else {
            DispatchGroup dbDispatcher = new DispatchGroup(() -> {
            });
            dbDispatcher.enter();
            AsyncTask.execute(() -> {
                List<SpacexLaunch> items = spaceDao.listLaunches();
                if (items != null && !items.isEmpty()) {
                    liveData.postValue(Resource.success(items));
                } else {
                    getSpacexLaunchList(liveData);
                }
                dbDispatcher.leave();
            });
        }
    }
}
