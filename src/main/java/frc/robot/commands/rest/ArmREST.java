package frc.robot.commands.rest;

import frc.robot.constants.ArmConstants;
import frc.robot.constants.ElevatorConstants;
import frc.robot.subsystems.ArmSubsystem;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.utils.rests.restAnnotations.REST;
import frc.robot.utils.rests.restAnnotations.Requirement;
import frc.robot.utils.rests.restUtils.RESTContainer;

import javax.inject.Inject;
import static frc.robot.utils.rests.restUtils.RESTAssertions.assertEquals;


public class ArmREST extends RESTContainer {
    @Requirement
    private final ArmSubsystem armSubsystem;

    @Inject
    public ArmREST (ArmSubsystem armSubsystem){
        this.armSubsystem = armSubsystem;
    }
    @REST
    public void armUpTest(){
        init(()->{
        });
        execute(()->{
            armSubsystem.moveUp(0.1);
        });
        double current = armSubsystem.getSuppliedCurrent();
        isFinished(()->{return hasElapsed(5);});
        end(()->{
            assertEquals(100, armSubsystem.getAngle(), 5);
            assertEquals(20, current, 5);
        });
    }
    @REST
    public void armDownTest(){
        init(()->{
        });
        execute(()->{
            armSubsystem.setPosition(ArmConstants.ArmPositions.DOWN);
        });
        double current = armSubsystem.getSuppliedCurrent();
        isFinished(()->{return hasElapsed(5);});
        end(()->{
            assertEquals(0, armSubsystem.getAngle(), 5);
            assertEquals(20, armSubsystem.getSuppliedCurrent(), 5);
        });
    }
}
