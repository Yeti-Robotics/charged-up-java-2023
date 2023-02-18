package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.ArmSubsystem;
import frc.robot.subsystems.ElevatorSubsystem;
import frc.robot.subsystems.IntakeSubsystem;

import javax.inject.Inject;


public class ConeHandoffHighCommand extends CommandBase {

    private final IntakeSubsystem intakeSubsystem;
    private final ArmSubsystem armSubsystem;
    private final ElevatorSubsystem elevatorSubsystem;
    @Inject
    public ConeHandoffHighCommand(IntakeSubsystem intakeSubsystem, ArmSubsystem armSubsystem, ElevatorSubsystem elevatorSubsystem) {
        this.intakeSubsystem = intakeSubsystem;
        this.armSubsystem = armSubsystem;
        this.elevatorSubsystem = elevatorSubsystem;
        addRequirements(intakeSubsystem, armSubsystem, elevatorSubsystem);
    }

    @Override
    public void initialize() {

        armSubsystem.setPosition(Constants.ArmConstants.ArmPositions.HANDOFF);
        armSubsystem.toggleBrake();
        elevatorSubsystem.setMotionMagic(Constants.ElevatorConstants.ElevatorPositions.CONE);
        //carriageroll
        intakeSubsystem.intakeUnclamp();


    }

    @Override
    public void execute() {
    }

    @Override
    public boolean isFinished() {
        //carriage has cone
        return true;
    }

    @Override
    public void end(boolean interrupted) {
        //carriagestoproll
        armSubsystem.toggleBrake();
        armSubsystem.setPosition(Constants.ArmConstants.ArmPositions.DOWN);
        elevatorSubsystem.setMotionMagic(Constants.ElevatorConstants.ElevatorPositions.UP);
    }

}
