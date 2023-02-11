package frc.robot.di;

import javax.inject.Singleton;
import dagger.Module;
import dagger.Provides;
import frc.robot.subsystems.ExampleSubsystem;
import frc.robot.subsystems.VisionSubsystem;
import edu.wpi.first.networktables.NetworkTableInstance;
import org.photonvision.PhotonCamera;

import javax.inject.Named;

@Module
public class SubsystemsModule {
    @Provides
    @Singleton
    public ExampleSubsystem provideExampleSubsystem() {
        return new ExampleSubsystem();
    }

    @Provides
    @Singleton
    public VisionSubsystem providesVisionSubsystem(@Named("table") NetworkTableInstance table) {return new VisionSubsystem(NetworkTableInstance.getDefault());}
}