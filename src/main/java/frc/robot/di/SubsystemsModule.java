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

import javax.inject.Named;

@Module
public class SubsystemsModule {
    @Provides
    @Singleton
    public CarriageSubsystem provideCarriageSubsystem(
            @Named("sideNeo") CANSparkMax sideNeo,
            @Named("piston") DoubleSolenoid piston,
            @Named("limitSwitch") DigitalInput limitSwitch,
            @Named("beamBreak") SparkMaxLimitSwitch beamBreak
    ){
        return new CarriageSubsystem(sideNeo, piston, limitSwitch, beamBreak);
    }
}