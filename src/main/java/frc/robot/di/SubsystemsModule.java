package frc.robot.di;

import javax.inject.Singleton;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import dagger.Module;
import dagger.Provides;
import frc.robot.subsystems.ArmSubsystem;
import frc.robot.subsystems.ExampleSubsystem;
import com.revrobotics.CANSparkMax;
import com.revrobotics.SparkMaxLimitSwitch;
import dagger.Module;
import dagger.Provides;
import edu.wpi.first.networktables.NetworkTableInstance;
import frc.robot.subsystems.CarriageSubsystem;
import frc.robot.subsystems.VisionSubsystem;

import javax.inject.Named;
import javax.inject.Singleton;

@Module
public class SubsystemsModule {
    @Provides
    @Singleton


    public ArmSubsystem providesArmSubsystem (
            @Named("armMotor1") WPI_TalonFX motor1, @Named("armMotor2")WPI_TalonFX motor2
    ){
       return new ArmSubsystem(motor1, motor2);
    }
    @Provides
    @Singleton
    public ExampleSubsystem provideExampleSubsystem() {
        return new ExampleSubsystem();

    public CarriageSubsystem provideCarriageSubsystem(
            @Named("rollerMotor") CANSparkMax rollerMotor,
            @Named("flipMotor") CANSparkMax flipMotor,
            @Named("beamBreak") SparkMaxLimitSwitch beamBreak
    ) {
        return new CarriageSubsystem(rollerMotor, flipMotor, beamBreak);
    }

    @Provides
    @Singleton
    public VisionSubsystem providesVisionSubsystem(@Named("table") NetworkTableInstance table) {
        return new VisionSubsystem(NetworkTableInstance.getDefault());

    }
}
