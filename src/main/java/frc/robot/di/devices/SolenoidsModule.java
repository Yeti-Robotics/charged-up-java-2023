package frc.robot.di.devices;


import dagger.Module;
import dagger.Provides;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import frc.robot.constants.ArmConstants;
import frc.robot.constants.DriveConstants;
import frc.robot.constants.IntakeConstants;

import javax.inject.Named;
import javax.inject.Singleton;

@Module
public class SolenoidsModule {

    @Provides
    @Singleton
    @Named(ArmConstants.AIR_BRAKE)
    public DoubleSolenoid providesAirBrake() {
        return new DoubleSolenoid(
                PneumaticsModuleType.CTREPCM,
                ArmConstants.AIR_BRAKE_PORTS[0],
                ArmConstants.AIR_BRAKE_PORTS[1]);
    }
}
