package frc.robot.commands.rest;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import frc.robot.constants.DriveConstants;
import frc.robot.subsystems.drivetrain.DrivetrainSubsystem;
import frc.robot.utils.rests.restAnnotations.REST;
import frc.robot.utils.rests.restAnnotations.Requirement;
import frc.robot.utils.rests.restUtils.RESTContainer;

import javax.inject.Inject;
public class DrivetrainREST extends RESTContainer {
    @Requirement
    private final DrivetrainSubsystem drivetrainSubsystem;
    @Inject
    public DrivetrainREST (DrivetrainSubsystem drivetrainSubsystem) {
        this.drivetrainSubsystem = drivetrainSubsystem;
    }
    @REST
    public void driveForwardTest() {
        init(() -> {
            drivetrainSubsystem.resetOdometer(new Pose2d(0,0,new Rotation2d(0)));
        });
        execute(() -> {
            drivetrainSubsystem.drive(
                    DriveConstants.DRIVE_KINEMATICS.toSwerveModuleStates(
                            ChassisSpeeds.fromFieldRelativeSpeeds(
                                    1,0,0, new Rotation2d(0)
                            )
            )
        );
       });

        isFinished(() -> {return hasElapsed(5);});
        end(() -> {
        });
    }

    @REST
    public void driveBackwardTest() {
        init(() -> {
            drivetrainSubsystem.resetOdometer(new Pose2d(0,0,new Rotation2d(0)));
        });
        execute(() -> {
            drivetrainSubsystem.drive(
                    DriveConstants.DRIVE_KINEMATICS.toSwerveModuleStates(
                            ChassisSpeeds.fromFieldRelativeSpeeds(
                                    -1,0,0, new Rotation2d(0)
                            )
                    )
            );
        });

        isFinished(() -> {return hasElapsed(5);});
        end(() -> {
        });
    }

    @REST
    public void driveRightSidewaysTest() {
        init(() -> {
            drivetrainSubsystem.resetOdometer(new Pose2d(0,0,new Rotation2d(0)));
        });
        execute(() -> {
            drivetrainSubsystem.drive(
                    DriveConstants.DRIVE_KINEMATICS.toSwerveModuleStates(
                            ChassisSpeeds.fromFieldRelativeSpeeds(
                                    0,1,0, new Rotation2d(0)
                            )
                    )
            );
        });

        isFinished(() -> {return hasElapsed(5);});
        end(() -> {
        });
    }

    @REST
    public void driveLeftSidewaysTest() {
        init(() -> {
            drivetrainSubsystem.resetOdometer(new Pose2d(0,0,new Rotation2d(0)));
        });
        execute(() -> {
            drivetrainSubsystem.drive(
                    DriveConstants.DRIVE_KINEMATICS.toSwerveModuleStates(
                            ChassisSpeeds.fromFieldRelativeSpeeds(
                                    0,-1,0, new Rotation2d(0)
                            )
                    )
            );
        });

        isFinished(() -> {return hasElapsed(5);});
        end(() -> {
        });
    }

}


