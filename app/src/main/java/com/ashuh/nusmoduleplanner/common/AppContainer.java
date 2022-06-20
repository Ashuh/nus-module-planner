package com.ashuh.nusmoduleplanner.common;

import android.content.Context;

import com.ashuh.nusmoduleplanner.common.data.local.database.AppDatabase;
import com.ashuh.nusmoduleplanner.common.data.repository.AppModuleRepository;
import com.ashuh.nusmoduleplanner.common.data.remote.source.ModuleRemoteDataSource;
import com.ashuh.nusmoduleplanner.common.data.remote.api.NusModsApi;
import com.ashuh.nusmoduleplanner.common.data.remote.api.NusModsApiBuilder;
import com.ashuh.nusmoduleplanner.common.data.local.source.ModuleLocalDataSource;
import com.ashuh.nusmoduleplanner.common.data.local.dao.ModuleDao;
import com.ashuh.nusmoduleplanner.common.domain.repository.ModuleRepository;

public class AppContainer {
    public ModuleRepository moduleRepository;

    public AppContainer(Context context) {
        NusModsApi nusModsApi = NusModsApiBuilder.getInstance().getApi();
        ModuleRemoteDataSource moduleRemoteDataSource = new ModuleRemoteDataSource(nusModsApi);
        ModuleDao moduleDao = AppDatabase.getInstance(context).dao();
        ModuleLocalDataSource moduleLocalDataSource = new ModuleLocalDataSource(moduleDao);
        moduleRepository = new AppModuleRepository(moduleRemoteDataSource, moduleLocalDataSource);
    }
}
