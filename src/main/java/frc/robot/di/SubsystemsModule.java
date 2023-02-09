package frc.robot.di;

import javax.inject.Inject;
import javax.inject.Singleton;

import com.revrobotics.CANSparkMax;
import com.revrobotics.SparkMaxLimitSwitch;
import dagger.Module;
import dagger.Provides;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import frc.robot.subsystems.CarriageSubsystem;
import edu.wpi.first.networktables.NetworkTableInstance;
import frc.robot.subsystems.ExampleSubsystem;
import frc.robot.subsystems.VisionSubsystem;
import org.photonvision.PhotonCamera;


import javax.inject.Named;

@Module
public class SubsystemsModule {
    @Provides
    @Singleton
    public CarriageSubsystem provideCarriageSubsystem(
            @Named("carriageNeo") CANSparkMax carriageNeo,
            @Named("piston") DoubleSolenoid piston,
            @Named("limitSwitch") DigitalInput limitSwitch,
            @Named("beamBreak") SparkMaxLimitSwitch beamBreak
    ){
        return new CarriageSubsystem(carriageNeo, piston, limitSwitch, beamBreak);
    }

    @Provides
    @Singleton
    public VisionSubsystem providesVisionSubsystem(@Named("table") NetworkTableInstance table) {return new VisionSubsystem(NetworkTableInstance.getDefault());}
}