package frc.robot.di;

import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.ctre.phoenix.sensors.WPI_CANCoder;
import com.ctre.phoenix.sensors.WPI_Pigeon2;
import com.revrobotics.CANSparkMax;
import com.revrobotics.SparkMaxLimitSwitch;
import com.revrobotics.SparkMaxPIDController;
import dagger.Module;
import dagger.Provides;
import edu.wpi.first.math.estimator.SwerveDrivePoseEstimator;
import edu.wpi.first.math.kinematics.SwerveDriveOdometry;
import edu.wpi.first.math.kinematics.SwerveModulePosition;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import frc.robot.constants.*;
import frc.robot.di.devices.DeviceModule;
import frc.robot.di.devices.MotorsModule;
import frc.robot.subsystems.ArmSubsystem;
import frc.robot.subsystems.CarriageSubsystem;
import frc.robot.subsystems.ElevatorSubsystem;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.drivetrain.DrivetrainSubsystem;
import frc.robot.subsystems.drivetrain.SwerveModule;

import javax.inject.Named;
import javax.inject.Singleton;

@Module
public class SubsystemsModule {
    @Provides
    @Singleton
    public ArmSubsystem providesArmSubsystem (
            @Named(ArmConstants.ARM_MOTOR) WPI_TalonFX armMotor1,
            @Named(ArmConstants.ARM_ENCODER) WPI_CANCoder encoder,
            @Named(ArmConstants.AIR_BRAKE) DoubleSolenoid airBrake) {
       return new ArmSubsystem(
               armMotor1,
               encoder,
               airBrake);
    }

    @Provides
    @Singleton
    public IntakeSubsystem providesIntakeSubsystem(
            @Named(IntakeConstants.LEFT_SPARK) CANSparkMax leftSpark,
            @Named(IntakeConstants.RIGHT_SPARK) CANSparkMax rightSpark,
            @Named(IntakeConstants.INTAKE_PISTON_NAME) DoubleSolenoid intakePiston) {
        return new IntakeSubsystem(
                leftSpark,
                rightSpark,
                intakePiston
        );
    }

    @Provides
    @Singleton
    public CarriageSubsystem provideCarriageSubsystem(
            @Named(CarriageConstants.ROLLER_SPARK) CANSparkMax rollerMotor,
            @Named(CarriageConstants.FLIP_MOTOR_NAME) TalonFX flipMotor)
          {

        return new CarriageSubsystem(rollerMotor, flipMotor);
    }

    @Provides
    @Singleton
    public ElevatorSubsystem provideElevatorSubsystem(
            @Named(ElevatorConstants.ELEVATOR_MOTOR) WPI_TalonFX elevatorMotor,
            @Named(ElevatorConstants.ELEVATOR_MAG_SWITCH) DigitalInput elevatorMagSwitch) {
        return new ElevatorSubsystem(
                elevatorMotor,
                elevatorMagSwitch);
    }

    private static SwerveModule swerveModuleFactory(
            int driveMotorID, int steerMotorID, int CANcoderID, boolean driveInverted, double encoderOffset, boolean encoderReversed
    ) {
        return new SwerveModule(
                MotorsModule.driveMotorFactory(driveMotorID, driveInverted),
                MotorsModule.azimuthMotorFactory(steerMotorID),
                DeviceModule.absoluteEncoderFactory(CANcoderID, encoderOffset, encoderReversed)
        );
    }

    @Provides
    @Singleton
    @Named(DriveConstants.FRONT_LEFT_MODULE_NAME)
    public SwerveModule providesFrontLeftSwerveModule() {
        return swerveModuleFactory(
                DriveConstants.FRONT_LEFT_DRIVE,
                DriveConstants.FRONT_LEFT_AZIMUTH,
                DriveConstants.FRONT_LEFT_ENCODER,
                DriveConstants.FRONT_LEFT_DRIVE_REVERSED,
                DriveConstants.FRONT_LEFT_ENCODER_OFFSET,
                DriveConstants.FRONT_LEFT_ENCODER_REVERSED
        );
    }

    @Provides
    @Singleton
    @Named(DriveConstants.FRONT_RIGHT_MODULE_NAME)
    public SwerveModule providesFrontRightSwerveModule() {
        return swerveModuleFactory(
                DriveConstants.FRONT_RIGHT_DRIVE,
                DriveConstants.FRONT_RIGHT_AZIMUTH,
                DriveConstants.FRONT_RIGHT_ENCODER,
                DriveConstants.FRONT_RIGHT_DRIVE_REVERSED,
                DriveConstants.FRONT_RIGHT_ENCODER_OFFSET,
                DriveConstants.FRONT_RIGHT_ENCODER_REVERSED
        );
    }

    @Provides
    @Singleton
    @Named(DriveConstants.BACK_LEFT_MODULE_NAME)
    public SwerveModule providesBackLeftSwerveModule() {
        return swerveModuleFactory(
                DriveConstants.BACK_LEFT_DRIVE,
                DriveConstants.BACK_LEFT_AZIMUTH,
                DriveConstants.BACK_LEFT_ENCODER,
                DriveConstants.BACK_LEFT_DRIVE_REVERSED,
                DriveConstants.BACK_LEFT_ENCODER_OFFSET,
                DriveConstants.BACK_LEFT_ENCODER_REVERSED
        );
    }


    @Provides
    @Singleton
    @Named(DriveConstants.BACK_RIGHT_MODULE_NAME)
    public SwerveModule providesBackRightSwerveModule() {
        return swerveModuleFactory(
                DriveConstants.BACK_RIGHT_DRIVE,
                DriveConstants.BACK_RIGHT_AZIMUTH,
                DriveConstants.BACK_RIGHT_ENCODER,
                DriveConstants.BACK_RIGHT_DRIVE_REVERSED,
                DriveConstants.BACK_RIGHT_ENCODER_OFFSET,
                DriveConstants.BACK_RIGHT_ENCODER_REVERSED
        );
    }

    @Provides
    @Singleton
    public DrivetrainSubsystem provideDriveTrainSubsystem(
            @Named(DriveConstants.FRONT_LEFT_MODULE_NAME) SwerveModule frontLeftModule,
            @Named(DriveConstants.FRONT_RIGHT_MODULE_NAME) SwerveModule frontRightModule,
            @Named(DriveConstants.BACK_LEFT_MODULE_NAME) SwerveModule backLeftModule,
            @Named(DriveConstants.BACK_RIGHT_MODULE_NAME) SwerveModule backRightModule,
            SwerveModulePosition[] swerveModulePositions,
            SwerveDrivePoseEstimator odometer,
            WPI_Pigeon2 gyro) {
        return new DrivetrainSubsystem(
                frontLeftModule,
                frontRightModule,
                backLeftModule,
                backRightModule,
                swerveModulePositions,
                odometer,
                gyro
        );
    }
}