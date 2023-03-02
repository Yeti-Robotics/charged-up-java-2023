package frc.robot.commands.rest;

import frc.robot.constants.ElevatorConstants;
import frc.robot.subsystems.ElevatorSubsystem;
import frc.robot.utils.rests.restAnnotations.REST;
import frc.robot.utils.rests.restAnnotations.Requirement;
import frc.robot.utils.rests.restUtils.RESTContainer;

import javax.inject.Inject;
import static frc.robot.utils.rests.restUtils.RESTAssertions.assertEquals;
public class ElevatorREST extends RESTContainer {
    @Requirement
    private final ElevatorSubsystem elevatorSubsystem;
    @Inject
    public ElevatorREST (ElevatorSubsystem elevatorSubsystem) {
        this.elevatorSubsystem = elevatorSubsystem;
    }
    @REST
    public void elevatorUpTest() {
        init(() -> {
            elevatorSubsystem.zeroEncoder();
        });
        execute(() -> {
            elevatorSubsystem.setPosition(ElevatorConstants.ElevatorPositions.LEVEL_TWO);
        });

        isFinished(() -> {return hasElapsed(5);});
        end(() -> {
            assertEquals(15.5, elevatorSubsystem.getDistance(), 5);
            assertEquals(12,elevatorSubsystem.getSuppliedCurrent(),5);
        });
    }
    @REST
    public void elevatorDownTest() {
        init(() -> {
            elevatorSubsystem.zeroEncoder();
        });
        execute(() -> {
            elevatorSubsystem.setPosition(ElevatorConstants.ElevatorPositions.DOWN);
        });

        isFinished(() -> {return hasElapsed(5);});
        end(() -> {
            assertEquals(0, elevatorSubsystem.getDistance(), 5);
            assertEquals(12,elevatorSubsystem.getSuppliedCurrent(),5);
        });


}

