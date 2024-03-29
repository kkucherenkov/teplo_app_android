package com.kkucherenkov.teploapp.dagger;

import com.kkucherenkov.teploapp.endofvisit.EndOfVisitFragment;
import com.kkucherenkov.teploapp.homescreen.HomescreenFragment;
import com.kkucherenkov.teploapp.newvisitor.NewVisitorFragment;

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

    NewVisitorFragment inject(NewVisitorFragment fragment);

    EndOfVisitFragment inject(EndOfVisitFragment fragment);
}
