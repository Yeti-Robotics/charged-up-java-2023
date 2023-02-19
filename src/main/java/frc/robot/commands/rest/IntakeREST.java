package frc.robot.commands.rest;

import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.utils.rests.restAnnotations.REST;
import frc.robot.utils.rests.restAnnotations.Requirement;
import frc.robot.utils.rests.restUtils.RESTContainer;

import javax.inject.Inject;

import static frc.robot.utils.rests.restUtils.RESTAssertions.assertEquals;

public class IntakeREST extends RESTContainer {
    @Requirement
    private final IntakeSubsystem intakeSubsystem;
    @Inject
    public IntakeREST (IntakeSubsystem intakeSubsystem) {
        this.intakeSubsystem=intakeSubsystem;
    }
    @REST
    public void intakeCloseTest() {
        init(() -> {
            intakeSubsystem.enableReedSwitch();
        });
        execute(() -> {
            intakeSubsystem.intakeClose();
        });

        isFinished(() -> {
            return intakeSubsystem.isClosed();
        });
        end(() -> {
            intakeSubsystem.intakeOpen();
        });
    }
    @REST
    public void intakeOpenTest() {
        init(() -> {
            intakeSubsystem.enableReedSwitch();
        });
        execute(() -> {
            intakeSubsystem.intakeOpen();
        });

        isFinished(() -> {
            return !intakeSubsystem.isClosed();
        });
        end(() -> {
            intakeSubsystem.intakeClose();
        });
    }
}
