package frc.robot.di.devices;

import dagger.Provides;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import frc.robot.Constants;

import javax.inject.Named;
import javax.inject.Singleton;

public class SolenoidsModule {

    /*@Provides
    @Singleton
    @Named("carriage piston")
    public DoubleSolenoid providesDoubleSolenoid() {

        return new DoubleSolenoid(
                PneumaticsModuleType.CTREPCM,
                Constants.CarriageConstants.CARRIAGE_PISTON[0],
                Constants.CarriageConstants.CARRIAGE_PISTON[1]);

    }*/
}
