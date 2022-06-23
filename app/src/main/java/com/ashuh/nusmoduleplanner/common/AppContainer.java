package com.ashuh.nusmoduleplanner.common;

import android.content.Context;

import com.ashuh.nusmoduleplanner.common.data.local.dao.ModuleDao;
import com.ashuh.nusmoduleplanner.common.data.local.database.AppDatabase;
import com.ashuh.nusmoduleplanner.common.data.local.source.ModuleLocalDataSource;
import com.ashuh.nusmoduleplanner.common.data.remote.api.DisqusApi;
import com.ashuh.nusmoduleplanner.common.data.remote.api.DisqusApiBuilder;
import com.ashuh.nusmoduleplanner.common.data.remote.api.NusModsApi;
import com.ashuh.nusmoduleplanner.common.data.remote.api.NusModsApiBuilder;
import com.ashuh.nusmoduleplanner.common.data.remote.source.ModuleRemoteDataSource;
import com.ashuh.nusmoduleplanner.common.data.remote.source.PostRemoteDataSource;
import com.ashuh.nusmoduleplanner.common.data.repository.AppModuleRepository;
import com.ashuh.nusmoduleplanner.common.data.repository.AppPostRepository;
import com.ashuh.nusmoduleplanner.common.domain.repository.ModuleRepository;
import com.ashuh.nusmoduleplanner.common.domain.repository.PostRepository;

public class AppContainer {
    public ModuleRepository moduleRepository;
    public PostRepository postRepository;

    public AppContainer(Context context) {
        NusModsApi nusModsApi = NusModsApiBuilder.getInstance().getApi();
        ModuleRemoteDataSource moduleRemoteDataSource = new ModuleRemoteDataSource(nusModsApi);
        ModuleDao moduleDao = AppDatabase.getInstance(context).dao();
        ModuleLocalDataSource moduleLocalDataSource = new ModuleLocalDataSource(moduleDao);
        moduleRepository = new AppModuleRepository(moduleRemoteDataSource, moduleLocalDataSource);

        DisqusApi disqusApi = DisqusApiBuilder.getInstance().getApi();
        PostRemoteDataSource postRemoteDataSource = new PostRemoteDataSource(disqusApi);
        postRepository = new AppPostRepository(postRemoteDataSource);
    }
}
