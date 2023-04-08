package frc.robot.commands;


import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.arm.SetArmPositionCommand;
import frc.robot.commands.elevator.SetElevatorPositionCommand;
import frc.robot.commands.wrist.SetWristPositionCommand;
import frc.robot.constants.ArmConstants;
import frc.robot.constants.ElevatorConstants;
import frc.robot.constants.WristConstants;
import frc.robot.subsystems.ArmSubsystem;
import frc.robot.subsystems.ElevatorSubsystem;
import frc.robot.subsystems.LEDSubsystem;
import frc.robot.subsystems.WristSubsystem;

public class MidScoreCommandGroup extends SequentialCommandGroup {


    public MidScoreCommandGroup(ArmSubsystem armSubsystem, ElevatorSubsystem elevatorSubsystem, LEDSubsystem ledSubsystem, WristSubsystem wristSubsystem) {
        // TODO: Add your sequential commands in the super() call, e.g.
        //           super(new OpenClawCommand(), new MoveArmCommand());
        super(
                new SetArmPositionCommand(armSubsystem, elevatorSubsystem, ArmConstants.ArmPositions.HANDOFF),
                new SetElevatorPositionCommand(elevatorSubsystem, armSubsystem, ElevatorConstants.ElevatorPositions.LEVEL_TWO),
                ledSubsystem.getPieceTarget() == LEDSubsystem.PieceTarget.CONE ?
                        new SetWristPositionCommand(wristSubsystem, WristConstants.WristPositions.SCORE_CONE_MID) :
                        new SetWristPositionCommand(wristSubsystem, WristConstants.WristPositions.SCORE_CUBE_MID));
    }
}