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
//    @Provides
//    @Singleton
//    @Named("carriage piston")
//    public DoubleSolenoid providesDoubleSolenoid() {
//
//        return new DoubleSolenoid(
//                PneumaticsModuleType.CTREPCM,
//                Constants.CarriageConstants.CARRIAGE_PISTON[0],
//                Constants.CarriageConstants.CARRIAGE_PISTON[1]);
//
//    }
}