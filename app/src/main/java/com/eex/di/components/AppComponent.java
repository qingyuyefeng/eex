package com.eex.di.components;

import com.eex.EEXApp;
import com.eex.di.modules.AllActivitysModule;
import com.eex.di.modules.AllFragmentsModule;
import com.eex.di.modules.AppModule;
import dagger.Component;
import dagger.android.AndroidInjectionModule;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;
import javax.inject.Singleton;

@Singleton
@Component(modules = {
    AndroidInjectionModule.class,
    AndroidSupportInjectionModule.class,
    AppModule.class,
    AllActivitysModule.class,
    AllFragmentsModule.class
})
public interface AppComponent extends AndroidInjector<EEXApp> {

  @Component.Builder
  abstract class Builder extends AndroidInjector.Builder<EEXApp> {
  }
}
