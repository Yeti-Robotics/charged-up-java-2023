package frc.robot.di;

import javax.inject.Singleton;

import com.revrobotics.CANSparkMax;
import dagger.Module;
import dagger.Provides;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.VisionSubsystem;

import javax.inject.Named;

@Module
public class SubsystemsModule {
    @Provides
    @Singleton
    public IntakeSubsystem providesIntakeSubsystem(
            @Named("intake spark 1") CANSparkMax intakeSpark1,
            @Named ("intake spark 2") CANSparkMax intakeSpark2,
            @Named ("intake piston") DoubleSolenoid intakePiston) {

        return new IntakeSubsystem(
                intakeSpark1,
                intakeSpark2,
                intakePiston
        );


    }
//    @Provides
//    @Singleton
//    public CarriageSubsystem provideCarriageSubsystem(
////            @Named("rollerMotor") CANSparkMax rollerMotor,
////            @Named("flipMotor") CANSparkMax flipMotor,
////            @Named("beamBreak") SparkMaxLimitSwitch beamBreak
//    ) {
//        return new CarriageSubsystem(rollerMotor, flipMotor, beamBreak);
//    }

    @Provides
    @Singleton
    public VisionSubsystem providesVisionSubsystem(@Named("table") NetworkTableInstance table) {
        return new VisionSubsystem(NetworkTableInstance.getDefault());
    }
}