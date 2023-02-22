package frc.robot.di;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.ctre.phoenix.sensors.WPI_CANCoder;
import com.ctre.phoenix.sensors.WPI_Pigeon2;
import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkMaxLimitSwitch;
import com.revrobotics.SparkMaxPIDController;
import dagger.Module;
import dagger.Provides;
import edu.wpi.first.math.kinematics.SwerveDriveOdometry;
import edu.wpi.first.math.kinematics.SwerveModulePosition;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import frc.robot.Constants;
import frc.robot.Constants.DriveConstants;
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
            @Named(Constants.ArmConstants.ARM_MOTOR_1) WPI_TalonFX armMotor1,
            @Named(Constants.ArmConstants.ARM_MOTOR_2) WPI_TalonFX armMotor2,
            @Named(Constants.ArmConstants.ARM_ENCODER) WPI_CANCoder encoder,
            @Named(Constants.ArmConstants.AIR_BRAKE) DoubleSolenoid airBrake) {
       return new ArmSubsystem(
               armMotor1,
               armMotor2,
               encoder,
               airBrake);
    }

    @Provides
    @Singleton
    public IntakeSubsystem providesIntakeSubsystem(
            @Named(Constants.IntakeConstants.LEFT_SPARK) CANSparkMax leftSpark,
            @Named(Constants.IntakeConstants.RIGHT_SPARK) CANSparkMax rightSpark,
            @Named(Constants.IntakeConstants.INTAKE_PISTON_NAME) DoubleSolenoid intakePiston,
            @Named(Constants.IntakeConstants.INTAKE_PID) SparkMaxPIDController pidController,
            @Named(Constants.IntakeConstants.INTAKE_ENCODER) RelativeEncoder encoder,
            @Named(Constants.IntakeConstants.INTAKE_BEAM_BREAK) SparkMaxLimitSwitch beamBreak,
            @Named(Constants.IntakeConstants.INTAKE_REED_SWITCH) SparkMaxLimitSwitch reedSwitch) {
        return new IntakeSubsystem(
                leftSpark,
                rightSpark,
                intakePiston,
                pidController,
                encoder,
                beamBreak,
                reedSwitch
        );
    }

    @Provides
    @Singleton
    public CarriageSubsystem provideCarriageSubsystem(
            @Named(Constants.CarriageConstants.ROLLER_MOTOR_NAME) CANSparkMax rollerMotor,
            @Named(Constants.CarriageConstants.FLIP_MOTOR_NAME) CANSparkMax flipMotor,
            @Named(Constants.CarriageConstants.FLIP_MOTOR_PID_NAME) SparkMaxPIDController flipPIDController) {
        return new CarriageSubsystem(rollerMotor, flipMotor, flipPIDController);
    }

    @Provides
    @Singleton
    public ElevatorSubsystem provideElevatorSubsystem(
            @Named(Constants.ElevatorConstants.ELEVATOR_MOTOR) WPI_TalonFX elevatorMotor,
            @Named(Constants.ElevatorConstants.ELEVATOR_MAG_SWITCH) DigitalInput elevatorMagSwitch) {
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
            SwerveDriveOdometry odometer,
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