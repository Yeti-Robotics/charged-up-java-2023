package frc.robot.commands.rest;

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
            elevatorSubsystem.elevatorUp();
        });

        isFinished(() -> {return hasElapsed(5);});
        end(() -> {
        });
    }
    @REST
    public void elevatorDownTest() {
        init(() -> {
            elevatorSubsystem.zeroEncoder();
        });
        execute(() -> {
            elevatorSubsystem.elevatorDown();
        });

        isFinished(() -> {return hasElapsed(5);});

        end(() -> {
            assertEquals(5000, elevatorSubsystem.getElevatorEncoder(), 50);
        });
    }



}

