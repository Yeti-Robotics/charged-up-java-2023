package frc.robot.di.devices;


import dagger.Module;
import dagger.Provides;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import frc.robot.Constants;

import javax.inject.Named;
import javax.inject.Singleton;

@Module
public class SolenoidsModule {

    @Provides
    @Singleton
    @Named(Constants.ArmConstants.AIR_BRAKE)
    public DoubleSolenoid providesAirBrake() {
        return new DoubleSolenoid(
                PneumaticsModuleType.CTREPCM,
                Constants.ArmConstants.AIR_BRAKE_PORTS[0],
                Constants.ArmConstants.AIR_BRAKE_PORTS[1]);
    }

    /****** IntakeSolenoid ******/
    @Provides
    @Singleton
    @Named(Constants.IntakeConstants.INTAKE_PISTON_NAME)
    public DoubleSolenoid providesIntakePiston() {
        return new DoubleSolenoid(
                PneumaticsModuleType.CTREPCM,
                Constants.IntakeConstants.INTAKE_PISTON[0],
                Constants.IntakeConstants.INTAKE_PISTON[1]);
    }
}
