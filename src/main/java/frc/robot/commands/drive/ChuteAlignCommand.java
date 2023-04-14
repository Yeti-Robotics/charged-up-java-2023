package frc.robot.commands.drive;

import edu.wpi.first.hal.DriverStationJNI;
import edu.wpi.first.hal.simulation.DriverStationDataJNI;
import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.constants.*;
import frc.robot.constants.AutoConstants.ALIGNMENT_POSITION;
import frc.robot.subsystems.CarriageSubsystem;
import frc.robot.subsystems.ElevatorSubsystem;
import frc.robot.subsystems.LEDSubsystem;
import frc.robot.subsystems.drivetrain.DrivetrainSubsystem;

import java.util.function.DoubleSupplier;

public class ChuteAlignCommand extends CommandBase {
    private final DrivetrainSubsystem drivetrainSubsystem;
    private final DoubleSupplier ySupplier;
    private final PIDController xController;
    private final PIDController thetaController;
    private final Timer timer;

    private double targetX;
    private Rotation2d targetTheta;

    private final ALIGNMENT_POSITION position;
    private final CarriageSubsystem carriageSubsystem;

    private final LEDSubsystem ledSubsystem;

    public ChuteAlignCommand(DrivetrainSubsystem drivetrainSubsystem, LEDSubsystem ledSubsystem, CarriageSubsystem carriageSubsystem, DoubleSupplier ySpeed, ALIGNMENT_POSITION position) {
        this.drivetrainSubsystem = drivetrainSubsystem;
        this.carriageSubsystem = carriageSubsystem;
        this.ledSubsystem = ledSubsystem;
        this.ySupplier = ySpeed;
        this.xController = new PIDController(AutoConstants.TRANSLATION_P, AutoConstants.TRANSLATION_I, AutoConstants.TRANSLATION_D);
        this.thetaController = new PIDController(AutoConstants.THETA_CONTROLLER_P, AutoConstants.THETA_CONTROLLER_I, AutoConstants.THETA_CONTROLLER_D);
        this.position = position;
        this.timer = new Timer();
        this.timer.start();

        xController.setTolerance(0.05);
        thetaController.setTolerance(0.008);
        thetaController.enableContinuousInput(-Math.PI, Math.PI);

        addRequirements(drivetrainSubsystem);
    }

    @Override
    public void initialize() {
        timer.reset();
        xController.reset();
        thetaController.reset();

        targetX = FieldConstants.humanStationAprilTag.getX() + position.offset.getX();

        targetTheta = position.offset.getRotation();

        if(DriverStation.getAlliance() == DriverStation.Alliance.Red) {
            targetTheta = position.offset.getRotation().plus(Rotation2d.fromDegrees(180));
        }

        if(ledSubsystem.getPieceTarget() == LEDSubsystem.PieceTarget.CUBE) {
            targetTheta = targetTheta.plus(Rotation2d.fromDegrees(0.0));
            carriageSubsystem.setSetpoint(CarriageConstants.CarriagePositions.CHUTE);
        } else {
            carriageSubsystem.setSetpoint(CarriageConstants.CarriagePositions.CHUTE);
        }

        xController.setSetpoint(targetX);
        thetaController.setSetpoint(targetTheta.getRadians());
        }



    @Override
    public void execute() {
        Pose2d robotPose = drivetrainSubsystem.getPose();
        double xSpeed = 0.0;
        double ySpeed = DrivetrainSubsystem.modifyAxis(ySupplier.getAsDouble()) * AutoConstants.ALIGNMENT_CONSTRAINTS.maxVelocity;
        double thetaSpeed = MathUtil.clamp(
                thetaController.calculate(robotPose.getRotation().getRadians()),
                -3.0,
                3.0);

        if (!xController.atSetpoint()) {
            xSpeed = MathUtil.clamp(
                    xController.calculate(robotPose.getX()),
                    -1.0,
                    1.0);
        }

        drivetrainSubsystem.drive(
                DriveConstants.DRIVE_KINEMATICS.toSwerveModuleStates(
                        ChassisSpeeds.fromFieldRelativeSpeeds(
                                xSpeed,
                                ySpeed,
                                thetaSpeed,
                                robotPose.getRotation()
                        )
                )
        );
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public void end(boolean interrupted) {
        drivetrainSubsystem.stop();
    }
}

