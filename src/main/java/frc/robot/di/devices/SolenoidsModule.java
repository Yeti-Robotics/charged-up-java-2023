package frc.robot.di.devices;

import com.revrobotics.CANSparkMax;
import dagger.Module;
import dagger.Provides;
import dagger.internal.InjectedFieldSignature;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import frc.robot.Constants;

import javax.inject.Named;
import javax.inject.Singleton;

@Module
public class SolenoidsModule {
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