package frc.robot.di;

import dagger.Module;
import dagger.Provides;
import dagger.multibindings.IntoSet;
import frc.robot.commands.rest.ExampleREST;
import frc.robot.utils.rests.restUtils.RESTContainer;
import frc.robot.utils.rests.restUtils.RESTHandler;

import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.Set;

@Module
public class RESTModule {
    @Provides
    @Singleton
    public RESTHandler providesRESTHandler(Set<RESTContainer> rests) {
        ArrayList<RESTContainer> restList = new ArrayList<>(rests);
        return new RESTHandler(restList);
    }

    @Provides @IntoSet
    public RESTContainer providesExampleREST() {
        return new ExampleREST();
    }
}
