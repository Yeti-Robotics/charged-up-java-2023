package frc.robot.commands.arm;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants.ArmConstants;
import frc.robot.subsystems.ArmSubsystem;

import javax.inject.Inject;
import javax.inject.Named;


public class SetPositionCommand extends CommandBase {
    private final ArmSubsystem armSubsystem;
    private final WPI_TalonFX motor1;
    @Inject
    public SetPositionCommand(
            ArmSubsystem armSubsystem,
            @Named("armMotor1") WPI_TalonFX armMotor1) {
        this.armSubsystem = armSubsystem;
        this.motor1 = armMotor1;
        addRequirements(armSubsystem);
    }


    @Override
    public void initialize() {
        double position = armSubsystem.getPosition();
        if (Math.abs(position - ArmConstants.ArmPositions.UP.angle) <= 5) {
            armSubsystem.setPosition(ArmConstants.ArmPositions.DOWN);
        }
        else if (Math.abs(position - ArmConstants.ArmPositions.HANDOFF.angle) <= 5) {
            armSubsystem.setPosition(ArmConstants.ArmPositions.DOWN);
        }
        else if (Math.abs(position - ArmConstants.ArmPositions.DOWN.angle) <= 5) {
            armSubsystem.setPosition(ArmConstants.ArmPositions.HANDOFF);
        }
    }


    @Override
    public void execute() {

    }

    @Override
    public boolean isFinished() {
        return motor1.isMotionProfileFinished();
    }


    @Override
    public void end(boolean interrupted) {
        armSubsystem.engageBrake();
    }
}
