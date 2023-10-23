package com.helloworld.presentation.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.helloworld.data.Resource;
import com.helloworld.data.repo.SpacexLaunchRepoImpl;
import com.helloworld.domain.entities.SpacexLaunch;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class SpacexLaunchViewModel extends ViewModel {
    private MutableLiveData<Resource<List<SpacexLaunch>>> spaceLaunchesLiveData;
    private MutableLiveData selectedSpaceLaunch = new MutableLiveData<SpacexLaunch>();
    private SpacexLaunchRepoImpl repository;

    @Inject
    public SpacexLaunchViewModel(SpacexLaunchRepoImpl repository) {
        this.repository = repository;
        spaceLaunchesLiveData = new MutableLiveData<>();
    }

    public MutableLiveData<SpacexLaunch> getSelectedSpaceLaunch() {
        return selectedSpaceLaunch;
    }

    public LiveData<Resource<List<SpacexLaunch>>> listLaunches(boolean hardRefresh) {
        spaceLaunchesLiveData.postValue(Resource.loading());
        repository.spacexLaunchList(spaceLaunchesLiveData, hardRefresh);
        return spaceLaunchesLiveData;
    }

    public void setSpaceLaunch(SpacexLaunch launch) {
        selectedSpaceLaunch.postValue(launch);
    }

    public void setFabStatus(SpacexLaunch launch) {
        repository.updateFavStatus(launch.getId(), launch.isFavorite());
    }
}
