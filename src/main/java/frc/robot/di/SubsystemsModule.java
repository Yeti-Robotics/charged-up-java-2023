package frc.robot.di;

import javax.inject.Singleton;
import dagger.Module;
import dagger.Provides;
import frc.robot.subsystems.ExampleSubsystem;

import javax.inject.Named;

@Module
public class SubsystemsModule {
    @Provides
    @Singleton
    public ExampleSubsystem provideExampleSubsystem() {
        return new ExampleSubsystem();
    }
}