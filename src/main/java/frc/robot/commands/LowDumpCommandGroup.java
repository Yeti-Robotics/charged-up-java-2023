package frc.robot.commands;


import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.arm.SetArmPositionCommand;
import frc.robot.commands.wrist.SetWristPositionCommand;
import frc.robot.constants.ArmConstants;
import frc.robot.constants.WristConstants;
import frc.robot.subsystems.ArmSubsystem;
import frc.robot.subsystems.ElevatorSubsystem;
import frc.robot.subsystems.LEDSubsystem;
import frc.robot.subsystems.WristSubsystem;

public class LowDumpCommandGroup extends SequentialCommandGroup {
    public LowDumpCommandGroup(ArmSubsystem armSubsystem, ElevatorSubsystem elevatorSubsystem, LEDSubsystem ledSubsystem, WristSubsystem wristSubsystem) {
        super(
                new SetArmPositionCommand(armSubsystem, elevatorSubsystem, ArmConstants.ArmPositions.GROUND),
                ledSubsystem.getPieceTarget() == LEDSubsystem.PieceTarget.CONE ?
                        new SetWristPositionCommand(wristSubsystem, WristConstants.WristPositions.SCORE_CONE_LOW) :
                        new SetWristPositionCommand(wristSubsystem, WristConstants.WristPositions.SCORE_CUBE_LOW));
    }
}