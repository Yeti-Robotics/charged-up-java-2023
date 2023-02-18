package frc.robot.commands;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.drivetrain.DrivetrainSubsystem;
import frc.robot.utils.Limelight;

import javax.inject.Inject;


public class AprilTagAlignCommand extends CommandBase {

    private final DrivetrainSubsystem drivetrainSubsystem;
    private final Limelight visionSubsystem;
    private final PIDController pidControllerX;
    private final PIDController pidControllerY;
    private final PIDController pidControllerAngle;

    @Inject
    public AprilTagAlignCommand(DrivetrainSubsystem drivetrainSubsystem,
                                Limelight visionSubsystem,
                                PIDController pidControllerX,
                                PIDController pidControllerY,
                                PIDController pidControllerAngle) {
        // each subsystem used by the command must be passed into the
        // addRequirements() method (which takes a vararg of Subsystem)
        this.drivetrainSubsystem = drivetrainSubsystem;
        this.visionSubsystem = visionSubsystem;
        this.pidControllerX = pidControllerX;
        this.pidControllerY = pidControllerY;
        this.pidControllerAngle = pidControllerAngle;
        addRequirements(drivetrainSubsystem);
    }

    /**
     * The initial subroutine of a command.  Called once when the command is initially scheduled.
     */
    @Override
    public void initialize() {


    }

    /**
     * The main body of a command.  Called repeatedly while the command is scheduled.
     * (That is, it is called repeatedly until {@link #isFinished()}) returns true.)
     */
    @Override
    public void execute() {

        if (Limelight.isTarget()) {
            double translationX = MathUtil.clamp(pidControllerX.calculate(Limelight.getTx()), -Constants.VisionConstants.CENTER_LIMIT, Constants.VisionConstants.CENTER_LIMIT);
            double translationY = MathUtil.clamp(pidControllerX.calculate(visionSubsystem.getTy()), -Constants.VisionConstants.CENTER_LIMIT, Constants.VisionConstants.CENTER_LIMIT);
            double translationAngle = pidControllerAngle.calculate(visionSubsystem.getYaw());
            drivetrainSubsystem.drive(new Translation2d(translationX, translationY), translationAngle);
        }
    }

        /**
         * <p>
         * Returns whether this command has finished. Once a command finishes -- indicated by
         * this method returning true -- the scheduler will call its {@link #end(boolean)} method.
         * </p><p>
         * Returning false will result in the command never ending automatically. It may still be
         * cancelled manually or interrupted by another command. Hard coding this command to always
         * return true will result in the command executing once and finishing immediately. It is
         * recommended to use * {@link edu.wpi.first.wpilibj2.command.InstantCommand InstantCommand}
         * for such an operation.
         * </p>
         *
         * @return whether this command has finished.
         */
        @Override
        public boolean isFinished () {
            // TODO: Make this return true when this Command no longer needs to run execute()
            return(pidControllerY.atSetpoint() && pidControllerY.atSetpoint() && pidControllerAngle.atSetpoint());
        }

        /**
         * The action to take when the command ends. Called when either the command
         * finishes normally -- that is it is called when {@link #isFinished()} returns
         * true -- or when  it is interrupted/canceled. This is where you may want to
         * wrap up loose ends, like shutting off a motor that was being used in the command.
         *
         * @param interrupted whether the command was interrupted/canceled
         */
        @Override
        public void end ( boolean interrupted){


        }
    }