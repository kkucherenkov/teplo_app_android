package com.kkucherenkov.teploapp.dagger;

import com.kkucherenkov.teploapp.homescreen.HomescreenFragment;

import dagger.Component;

/**
 * Created by Kirill Kucherenkov on 04/09/16.
 */
@Component(
        modules = {
                ApplicationModule.class
        })
@PerApp
public interface ApplicationComponent {
    HomescreenFragment inject(HomescreenFragment fragment);
}
